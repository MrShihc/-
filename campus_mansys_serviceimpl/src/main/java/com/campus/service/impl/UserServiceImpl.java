package com.campus.service.impl;

import com.campus.entity.CityBean;
import com.campus.entity.Grade;
import com.campus.entity.Student;
import com.campus.entity.UserBean;
import com.campus.mapper.UserMapper;
import com.campus.service.UserService;
import com.campus.common.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 查询所有的用户信息
     * @return
     */
    public List<UserBean> getUserList() {
        return userMapper.getUserList();
    }

    /**
     * 查询所有的学生信息以及班级信息
     * @return
     */
    public List<Student> getStuList() {
        return userMapper.getStuList();
    }

    /**
     * 查询出所有的班级信息
     * @return
     */
    public List<Grade> getGradelist() {
        return userMapper.getGradelist();
    }

    /**
     * 三级联动
     * @param id
     * @return
     */
    public List<CityBean> getProlistById(Long id) {
        return userMapper.getProlistById(id);
    }

    /**
     * 保存学生信息
     * @param student
     * @return
     */
    public Result saveStuInfo(Student student) {
        if(student.getSid()==null){     //添加
            try{
                //保存学生信息
                userMapper.saveStuInfo(student);
                //保存学生和班级的中间表信息
                userMapper.saveStuGradeInfo(student.getSid(),student.getGrade().getGid());
                return new Result(true,"保存成功！");
            }catch(Exception e){
                e.printStackTrace();
                return new Result(false,"保存失败!");
            }
        }else{  //修改
           try{
               userMapper.saveStuInfoBySid(student);
               userMapper.saveStuGradeInfoById(student.getGrade().getGid(),student.getSid());
               return new Result(true,"保存成功!");
           }catch(Exception e){
               e.printStackTrace();
               return new Result(false,"保存失败!");
           }
        }
    }

    /**
     * 批量删除学生信息
     * @param sids
     * @return
     */
    public Result deleteStuBatchBySid(Long[] sids) {
        if(sids!=null && sids.length>=1){
            for (Long sid:sids) {
                try{
                    //删除学生表信息
                    userMapper.deleteStuBatchBySid(sid);
                    userMapper.deleteStuGradeBySid(sid);
                }catch(Exception e){
                    return new Result(false,"删除失败!");
                }
            }
            return new Result(true,"删除成功");
        }
        return new Result(false,"传入的数据不能为空哦!");
    }

    /**
     * 根据学生id查询学生信息
     * @param sid
     * @return
     */

    public Student saveStuById(Long sid) {
        return userMapper.saveStuById(sid);
    }
}
