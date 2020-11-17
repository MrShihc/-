package com.campus.service.impl;

import com.campus.entity.Student;
import com.campus.mapper.StudentMapper;
import com.campus.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;

    /**
     * 学生登录界面
     * @param student
     * @return
     */
    public Student stuLogin(Student student) {
        /**
         * 判断账户学生账户是否为空
         */
        if(student.getSaccount() != null || student.getSaccount() !=""){    //不为空继续操作
            List<Student> stuList = studentMapper.getLogin(student.getSaccount());
            if(stuList.size()==1){  //等于1的话相当于数据库中只有一条该记录，证明不重复
                //取出集合中下标为0的学生信息
                Student stu = stuList.get(0);
                /**
                 * 判断前台输入的密码是否和查询出来的密码一致，
                 */
                if(stu.getSpwd()!=null && student.getSpwd()==stu.getSpwd()){    //一致继续执行
                    return stu;
                }
            }
        }
        return null;
    }
}
