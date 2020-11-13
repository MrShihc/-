package com.campus.service.impl;

import com.campus.entity.Exam;
import com.campus.entity.ExamOption;
import com.campus.entity.Grade;
import com.campus.entity.TestBean;
import com.campus.mapper.ExamMapper;
import com.campus.mapper.ExamOptionMapper;
import com.campus.mapper.TeacherMapper;
import com.campus.mapper.TestMapper;
import com.campus.service.TeacherService;
import com.campus.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
     * @param exam
     * @param testBean
     * @param gids
     * @return
     */
    public Result saveMakeTest(List<Exam> exam, TestBean testBean, Long[] gids) {
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
        Long tid = 1L;
        String tname = "李增强";

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
                testMapper.saveTestGradeInfo(gids,testid);
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
}
