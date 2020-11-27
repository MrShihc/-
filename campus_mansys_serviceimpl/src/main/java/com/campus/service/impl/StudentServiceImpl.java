package com.campus.service.impl;

import com.campus.common.Result;
import com.campus.entity.*;
import com.campus.mapper.StudentMapper;
import com.campus.service.StudentService;
import com.campus.utils.ExamReadUtils;
import com.campus.utils.RedisUtil;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 学生登录界面
     * @param student
     * @return
     */
    public Student stuLogin(Student student) {
        /**
         * 判断前台传入的student中是否有数据
         */
        if(student!=null){   //有数据,继续下面的操作
            /**
             * 判断账户学生账户是否为空
             */
            if(student.getSaccount() != null && !student.getSaccount().equals("")){    //不为空继续操作
                //登录时获取集合，目的就是为了防止有两个同样的信息
                List<Student> stuList = studentMapper.getLogin(student.getSaccount());
                if(stuList.size()==1){  //等于1的话相当于数据库中只有一条该记录，证明不重复
                    //取出集合中下标为0的学生信息
                    Student stu = stuList.get(0);
                    /**
                     * 判断前台输入的密码是否和查询出来的密码一致，
                     */
                    if(stu!=null && student.getSpwd().equals(stu.getSpwd())){    //一致继续执行
                        return stu;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 根据学生对应的角色id查询出菜单信息
     * @param request
     * @return
     */
    public List<MenuBean> getStuMenuInfo(HttpServletRequest request) {
        Student student = (Student)request.getSession().getAttribute("student");
        if(student!=null){
            if(student.getRid()!=null){
               List<MenuBean> menuList = studentMapper.getStuMenuInfo(student.getRid());
               return menuList;
            }
        }
        return null;
    }

    /**
     * 根据学生id查询学生考试信息
     * @param request
     * @return
     */
    public List<TestBean> getStuTestInfo(HttpServletRequest request) {
        //获取session中存的学生信息
        Student student = (Student)request.getSession().getAttribute("student");
        if(student!=null){  //判断学生信息不为空
            List<TestBean> testList = studentMapper.getStuTestInfo(student.getSid());
            if(testList!=null && testList.size()>=1){
                for (TestBean testBean : testList) {
                    long starttime = testBean.getStarttime().getTime();
                    long time = new Date().getTime();
                    if(starttime-time >0){
                        testBean.setTestStatus("考试尚未开放");
                    }else{
                        testBean.setTestStatus("开始考试");
                    }
                }
                return testList;
            }

        }
        return null;
    }

    /**
     * 获取学生的历史考试信息
     * @param request
     * @return
     */
    public List<TestBean> findStuHistoryTest(HttpServletRequest request) {
        Student student = (Student)request.getSession().getAttribute("student");
        if(student!=null){
            List<TestBean> testList = studentMapper.findStuHistoryTest(student.getSid());
            return testList;
        }
        return null;
    }

    /**
     * 根据试卷id获取试题信息
     * @param testid
     * @return
     */
    public Result getTestInfoByTestid(Long testid,HttpServletRequest request) {
        //创建Result对象
        Result result = new Result();

        //设置session过期时间为试卷指定的时长+1分钟
        //去查询试卷信息
        TestBean test = studentMapper.getTestInfo(testid);
        if(test!=null){
            //获取当前试题的考试时长的秒数
            Integer testtime = test.getTesttime()*60;
            //防止答题时间等于当前时长所以让他多一分钟
            Integer testtimes = testtime+60;
            //设置session过期时间
            request.getSession().setMaxInactiveInterval(testtimes);
            //存入result对象中，传入前台
            result.setData2(testtime);
        }

        //当学生点击去考试时，存入session中一个当前时间戳的毫秒数
        request.getSession().setAttribute("time",new Date().getTime());

        //获取到试题信息
        List<Exam> examList = studentMapper.getTestInfoByTestid(testid);
        if(examList!=null && examList.size()>=1){   //如果试题信息不为空，继续操作
            for (Exam exam : examList) {
                //获取选项信息
                List<ExamOption> options = exam.getOptions();
                //判断选项信息不为空
                if(options!=null&&options.size()>=1){
                    //打乱选项顺序
                    Collections.shuffle(options);
                }
            }
            //打乱试题信息
            Collections.shuffle(examList);
            //设置到result对象中
            result.setData1(examList);
        }
        return result;
    }

    /**
     * 保存学生考试信息
     * @param sid
     * @param testid
     * @param exam
     */
    public Result savestuToSubmit(Long sid, Long testid, List<Exam> exam,Long time) {
        //创建Result对象
        Result result = new Result();
        //获取客观题总分
        Double cscore = ExamReadUtils.getExamScore(exam);

        //生成时间戳格式的客观rediskey
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String crediskey = sdf.format(new Date());

        /**
         * 把学生id，试卷id，客观分数，考试时长，结束时间，客观rediskey存起来
         */
        Long stesttime = (Long)((new Date().getTime()-time)/1000/60)+1;
        try{
            Date testendtime = new Date();
            studentMapper.stuToSubmits(sid,testid,cscore,stesttime,testendtime,crediskey);
            boolean flag = false;
            for (Exam exam1 : exam) {
                if("问答题".equals(exam1.getEtype()) || "填空题".equals(exam1.getEtype())){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                //更改主观分数
                studentMapper.updateStudentQscore(sid,testid);
            }
            result.setFlag(true);
            result.setMessage("提交成功，请等待管理员审阅剩余的主观题！");
        }catch (Exception e){
            e.printStackTrace();
            result.setFlag(false);
            result.setMessage("提交失败，请及时联系管理员！");
        }

        /**
         * 用时间戳来保存crediskey
         */
        redisUtil.putObject(crediskey,exam);
        return result;
    }

    /**
     * 获取学生历史试卷详情信息
     * @param crediskey
     * @return
     */
    public List<Exam> getStudentHistoryTest(String crediskey) {
        return (List<Exam>)redisUtil.getObject(crediskey);
    }
}
