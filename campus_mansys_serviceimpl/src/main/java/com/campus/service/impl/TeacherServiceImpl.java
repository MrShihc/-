package com.campus.service.impl;

import com.campus.common.TestVo;
import com.campus.entity.*;
import com.campus.mapper.ExamMapper;
import com.campus.mapper.ExamOptionMapper;
import com.campus.mapper.TeacherMapper;
import com.campus.mapper.TestMapper;
import com.campus.service.TeacherService;
import com.campus.common.Result;
import com.campus.utils.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl implements TeacherService {

    //老师的数据访问接口
    @Resource
    private TeacherMapper teacherMapper;

    //考卷的数据访问接口
    @Resource
    private TestMapper testMapper;

    //试卷的数据访问接口
    @Resource
    private ExamMapper examMapper;

    //选项的数据访问接口
    @Resource
    private ExamOptionMapper examOptionMapper;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 根据登录的用户id查询该用户可以看到的考试信息
     * @param tid
     * @return
     */
    public List<TestBean> getDuliTestList(Long tid) {

        return teacherMapper.getDuliTestList(tid);
    }

    /**
     * 根据当前登录人id，把这个老师发布的班级查询出来，直接给页面
     * @param tid
     * @return
     */
    public List<Grade> getCurLoginPeoGrade(Long tid) {
        return teacherMapper.getCurLoginPeoGrade(tid);
    }

    /**
     * 判断考试名称的唯一性
     * @param testname
     * @return
     */
    public Result uniTestname(String testname) {
        //1.创建Result对象
        Result result = new Result();
        TestBean testBean = teacherMapper.uniTestname(testname);
        if(testBean==null || testBean.toString()==""){
            result.setSuccess("success");
            result.setMessage("√");
        }else{
            result.setSuccess("fail");
            result.setMessage("考卷名称重复了哦，请重新填写！");
        }
        return result;
    }

    /**
     * 发布试题
     * @param tid
     * @param exam
     * @param testBean
     * @param grade
     * @return
     */
    public Result saveMakeTest(Long tid, List<Exam> exam, TestBean testBean, List<Grade> grade,HttpServletRequest request){
        //1.创建Result对象
        Result result = new Result();

        //当前时间
        Date date = new Date();
        //开始时间
        Date starttime = testBean.getStarttime();
        //结束时间
        Date endtime = testBean.getEndtime();

        //比较开始时间是否大于当前时间
        int i = starttime.compareTo(date);

        //比较结束时间是否大于开始时间
        int end = endtime.compareTo(starttime);

        //小于的话直接返回结果
        if(i<0){
            result.setSuccess("fail");
            result.setMessage("开始时间不能小于当前时间哦！");
            return result;
        }else if(end<0){
            result.setSuccess("fail");
            result.setMessage("结束时间不能小于开始时间哦！");
            return result;
        }
        /**
         * 先去获取到老师的名称及老师的id编号
         */
        Teacher teacher1 = (Teacher)request.getSession().getAttribute("teacher");
        String tname = teacher1.getTname();

        /**
         * 先去保存考试表信息，
         *  之后把testid通过insert标签中的属性：useGenerated及keyProperty拿出来
         */
        try{
            testBean.setTestauthor(tname);
            testBean.setAuthorid(tid);
            teacherMapper.saveTestInfo(testBean);
            //获取自增主键id
            Long testid = testBean.getTestid();
            try{
                /**
                 * 保存考试和班级关联的信息
                 */
                if(grade!=null && grade.size()>=1){
                    for (Grade g : grade) {
                        testMapper.saveTestGradeInfo(g.getGid(),testid,g.getTid());
                    }
                }
                try{
                    /**
                     * 保存试卷信息
                     */
                    for (Exam ex:exam) {
                        ex.setTestid(testid);
                        examMapper.saveExamInfo(ex);
                        Long examid = ex.getExamid();
                        /**
                         * 保存试卷选项信息
                         */
                        List<ExamOption> options = ex.getOptions();
                        //判断选项的个数大于0
                        if(options.size()>0 || !options.isEmpty()){
                            for (ExamOption option:options){
                                //判断试卷选项如果不为空，则进行添加
                                if(option!=null || option.toString()!=""){
                                    option.setExamid(examid);
                                    examOptionMapper.saveExamOptionInfo(option);
                                }else{
                                    continue;
                                }
                            }
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    result.setSuccess("fail");
                    result.setMessage("试卷信息保存失败，请稍后重试！");
                    return result;
                }
            }catch(Exception e){
                e.printStackTrace();
                result.setSuccess("fail");
                result.setMessage("保存考试与班级关联信息失败，请稍后重试！");
                return result;
            }
            result.setSuccess("success");
            result.setMessage("保存成功！");
        }catch(Exception e){
            e.printStackTrace();
            result.setSuccess("fail");
            result.setMessage("保存考卷失败，请稍后重试！");
            return result;
        }
        return result;
    }

    /**
     * 查询出所有的老师信息
     * @return
     */
    public List<Teacher> getTeacher() {
        return teacherMapper.getTeacher();
    }

    /**
     * 根据老师id查询出该老师可以阅卷的信息
     * @param tid
     * @return
     */
    public List<TestVo> getReadInfo(Long tid) {

        List<TestVo> readInfo = teacherMapper.getReadInfo(tid);
        /**
         * 在这里开始组合testVo中的两个数
         */
        for (TestVo testVo : readInfo) {
            Long gid = testVo.getGid();
            Long testid = testVo.getTestid();

            /**
             * 拿着这两个字段去数据库进行统计查询
             * 一次能查出来两个数据最好，要是查不出来，
             * 分两次查询
             * 这里最后进行排序处理，倒序
             */
            Map map = teacherMapper.getCounts(testVo.getGid(),testVo.getTestid());
            //获取考试总人数
            Integer totalcount = ((Long)map.get("totalcount")).intValue();

            //获取待阅卷人数
            Integer nocount = ((Long)map.get("nocount")).intValue();

            testVo.setTotalcount(totalcount);
            testVo.setNocount(nocount);
        }
        Collections.sort(readInfo);
        return readInfo;
    }

    /**
     * 去阅卷，查询出学生信息
     * @param gid
     * @param testid
     * @return
     */
    public List<TestBean> getReadTestStudent(Long gid, Long testid) {
        return teacherMapper.readTestStudent(gid,testid);
    }

    /**
     * 获取学生的主观题信息
     * @param crediskey
     * @return
     */
    public List<Exam> getPanfen(String crediskey) {

        //试题信息
        List<Exam> examList = (List<Exam>)redisUtil.getObject(crediskey);

        if(examList.size()>=0 && examList != null){
            for (int i = 0; i < examList.size(); i++) {
                if(examList.get(i).getEtype().equals("单选题") ||
                        examList.get(i).getEtype().equals("多选题") ||
                        examList.get(i).getEtype().equals("判断题")){
                    examList.remove(i);
                    i--;
                }
            }
        }
        return examList;
    }

    /**
     * 保存学生的主观题信息
     * @param crediskey
     * @param examids
     * @param fenzhis
     */
    public void saveTestStudentQuestion(String crediskey, Long[] examids, Double[] fenzhis) {
        /**
         * 先拿着crediskey去mysql数据库中把该学生的考试信息查出来
         * 客观题分数、总分等等字段
         */
        TestBean test = teacherMapper.getStudentTestByCrediskey(crediskey);

        /**
         * 可以测试事务是否回滚
         * int i = 3/0; 运行时异常，属于算术异常
         */

        /**
         * 考试计算老师给他主观题打了多少分，主观题计算出来，
         *      更新数据库学生和考试中间表的主观分数以及客观分数
         */
        Double qscore = 0.0;
        for (Double fenzhi : fenzhis) {
            qscore+=fenzhi;
        }


        /**
         * 查询出来没有id，就用crediskey更新
         */
        teacherMapper.updateStuTestQscoreAndScore(crediskey,qscore);

        /**
         * 把老师给学生主观题的分数，更新进redis里面去，供学生查看历史试卷
         */
        List<Exam> examList = (List<Exam>)redisUtil.getObject(crediskey);

        /**
         * 遍历redis的学生作答list
         */
        for (Exam exam : examList) {

            /**
             * 遍历老师给学生的主观题得分情况，先遍历主观题的id,examid的集合
             */
            for(int i=0;i<examids.length;i++){

                //如果老师给判分的这个主观题在redis中找到了
                if(exam.getExamid().equals(examids[i])){
                    //把老师给学生这个主观题得的分数，更新进redis里面
                    exam.setPanfen(fenzhis[i]);
                    break;
                }

            }
        }

        /**
         * 把最后的list,更新到redis里面去
         */
        redisUtil.putObject(crediskey,examList);

    }

    /**
     * 根据老师登录进行登录
     * @param teacher
     * @return
     */
    public Teacher getLogin(Teacher teacher, HttpServletRequest request) {

        //
        /**
         * 判断老师账户是否为空
         */
        if(teacher.getTaccount()!=null&&!"".equals(teacher.getTaccount())){
            //获取集合的原因是防止账户名相同的大于1个
            List<Teacher> teachers = teacherMapper.getLogin(teacher.getTaccount());
            //判断只有一个账户
            if(teachers.size()==1){
                //获取这个账户
                Teacher tea = teachers.get(0);
                //判断前台传入的密码和这个账户的密码是否一致，
                if(tea!=null &&  tea.getPwd().equals(teacher.getPwd())){
                    request.getSession().setAttribute("teacher",tea);
                    return tea;
                }
            }
        }

        return null;
    }
}
