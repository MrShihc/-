package com.campus.controller;

import com.campus.entity.*;
import com.campus.service.UserService;
import com.campus.utils.Result;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 用户控制层
 */
@Controller
@RequestMapping("/user")
public class UserController {

    //注入用户业务逻辑层接口
    @Resource
    private UserService userService;

    /**
     * 根据sid实现修改时回显数据的功能
     */
    @RequestMapping("/saveStuById")
    @ResponseBody
    public Student saveStuById(Long sid){
        Student student = userService.saveStuById(sid);
        return student;
    }

    /**
     * 批量删除学生信息
     */
    @RequestMapping("/deleteStuBatchBySid")
    @ResponseBody
    public Result deleteStuBatchBySid(@RequestBody Long[] sids){
        return userService.deleteStuBatchBySid(sids);
    }

    /**
     * 保存学生信息
     */
    @RequestMapping("/saveStuInfo")
    @ResponseBody
    public Result saveStuInfo(@RequestBody Student student){
        return userService.saveStuInfo(student);
    }

    /**
     * 跳转至学生信息展示界面
     */
    @RequestMapping("/goStuList")
    public String goStuList(){
        return "stuList";
    }

    /**
     * 展示所有的用户信息
     * @return
     */
    @RequestMapping("/getUserList")
    @ResponseBody
    public List<UserBean> getUserList(){

        return userService.getUserList();
    }

    /**
     * 展示所有的学生以及班级信息
     */
    @RequestMapping("/getStuList")
    @ResponseBody
    public List<Student> getStuList(){
        return userService.getStuList();
    }

    /**
     * 展示所有的班级信息
     */
    @RequestMapping("/getGradelist")
    @ResponseBody
    public List<Grade> getGradelist(){
        return userService.getGradelist();
    }

    /**
     * 三级联动
     */
    @RequestMapping("/getProlistById")
    @ResponseBody
    public List<CityBean> getProlistById(Long id){
        return userService.getProlistById(id);
    }

    /**
     * 上传试卷，并解析Excel表格信息
     */
    @RequestMapping("/fileUpload")
    public void fileupload(@RequestBody MultipartFile filename){

        try {
            /**
             * 从springMvc上传上来的文件中获取到输入流
             */
            InputStream inputStream = filename.getInputStream();
            //获取文件类型
            String originalFilename = filename.getOriginalFilename();

            /**
             * 创建一个excel,把流给他
             */
            /**
             * Excel 2007的
             */
            // SXSSFWorkbook xx = null;

            //Excel 2003的
            // HSSFWorkbook yy = null;

            if(originalFilename.endsWith(".xlsx")){
                /**
                 * 创建2007版的Excel对象
                 */
                XSSFWorkbook  workbook = new XSSFWorkbook (inputStream);

                /**
                 * 获取工作簿里面的工作表，sheet,可以用名字来获取，也可以用角标来获取
                 * 习惯性用角标来获取
                 */
                XSSFSheet sheet = workbook.getSheetAt(0);
                /**
                 * 开始遍历工作表里面的行，需要知道有多少行
                 */
                int lastRowNum = sheet.getLastRowNum();
                List<Exam> list = new ArrayList<Exam>();
                for (int i=2;i<=lastRowNum;i++){
                    /**
                     * 使用角标获取Excel工作表中的行，0和1不是试题
                     */
                    XSSFRow row = sheet.getRow(i);
                    XSSFCell cell1cc = row.getCell(0);
                    if(cell1cc==null || cell1cc.equals("") || cell1cc.toString()==""){
                        continue;
                    }else{
                        Exam exam = new Exam();
                        /**
                         * 获取行中第一列，题型
                         */
                        XSSFCell cell = row.getCell(0);
                        String value = cell.getStringCellValue();
                        exam.setEtype(value);

                        /**
                         * 题干
                         */
                        String tigan = row.getCell(1).getStringCellValue();
                        exam.setEname(tigan);

                        /**
                         * 答案，答案和选项需要装进另外一个list里面去
                         * 答案拿到是一个
                         */
                        XSSFCell daanCell = row.getCell(2);
                        String daan = "";
                        if (daanCell != null){
                            daan = daanCell.getStringCellValue();
                        }else{
                            daan = "";
                        }

                        /**
                         * 分支，装进去
                         */
                        double fenzhi = row.getCell(3).getNumericCellValue();
                        exam.setEfenzhi(fenzhi);

                        /**
                         * 选项，开始判断，单选和多选有四个选项
                         * 判断题有两个选项，填空题和问答题，没有选项
                         */

                        if("单选题".equals(value)){
                            /**
                             * 获取A/B/C/D四个选项的值
                             */
                            String xxA = row.getCell(4).getStringCellValue();
                            String xxB = row.getCell(5).getStringCellValue();
                            String xxC = row.getCell(6).getStringCellValue();
                            String xxD = row.getCell(7).getStringCellValue();

                            ExamOption oA = new ExamOption();
                            oA.setOname(xxA);

                            ExamOption oB = new ExamOption();
                            oB.setOname(xxB);

                            ExamOption oC = new ExamOption();
                            oC.setOname(xxC);

                            ExamOption oD = new ExamOption();
                            oD.setOname(xxD);

                            /**
                             * 判断答案
                             */
                            if("A".equalsIgnoreCase(daan)){
                                oA.setIstrue(1);
                            }else if("B".equalsIgnoreCase(daan)){
                                oB.setIstrue(1);
                            }else if("C".equalsIgnoreCase(daan)){
                                oC.setIstrue(1);
                            }else if("D".equalsIgnoreCase(daan)){
                                oD.setIstrue(1);
                            }else{
                                System.out.println("题目有误");
                            }

                            exam.getOptions().add(oA);
                            exam.getOptions().add(oB);
                            exam.getOptions().add(oC);
                            exam.getOptions().add(oD);

                        }else if("多选题".equals(value)){
                            /**
                             * 获取A/B/C/D四个选项的值
                             */
                            String xxA = row.getCell(4).getStringCellValue();
                            String xxB = row.getCell(5).getStringCellValue();
                            String xxC = row.getCell(6).getStringCellValue();
                            String xxD = row.getCell(7).getStringCellValue();

                            ExamOption oA = new ExamOption();
                            oA.setOname(xxA);

                            ExamOption oB = new ExamOption();
                            oA.setOname(xxB);

                            ExamOption oC = new ExamOption();
                            oA.setOname(xxC);

                            ExamOption oD = new ExamOption();
                            oA.setOname(xxD);

                            /**
                             * 多选需要先把答案拿出来，进行分割
                             */
                            String[] split = daan.split("\\|");

                            List<String> daans = Arrays.asList(split);


                            if(daans.contains("A")){
                                oA.setIstrue(1);
                            }

                            if(daans.contains("B")){
                                oB.setIstrue(1);
                            }

                            if(daans.contains("C")){
                                oC.setIstrue(1);
                            }

                            if(daans.contains("D")){
                                oD.setIstrue(1);
                            }

                            exam.getOptions().add(oA);
                            exam.getOptions().add(oB);
                            exam.getOptions().add(oC);
                            exam.getOptions().add(oD);

                        }else if("判断题".equals(value)){
                            /**
                             * 获取A/B/C/D四个选项的值
                             */
                            String xxA = row.getCell(4).getStringCellValue();
                            String xxB = row.getCell(5).getStringCellValue();

                            ExamOption oA = new ExamOption();
                            oA.setOname(xxA);

                            ExamOption oB = new ExamOption();
                            oA.setOname(xxB);

                            if("A".equalsIgnoreCase(daan)){
                                oA.setIstrue(1);
                            }else if("B".equalsIgnoreCase(daan)){
                                oB.setIstrue(1);
                            }else{
                                System.out.println("题目有误");
                            }
                            exam.getOptions().add(oA);
                            exam.getOptions().add(oB);
                        }
                        list.add(exam);
                    }

                }
                System.out.println(1);
                System.out.println(list);
                System.out.println(list.size());
            }else if(originalFilename.endsWith(".xls")){
                /**
                 * 创建2003版的Excel对象
                 */
                HSSFWorkbook  workbook = new HSSFWorkbook (inputStream);

                /**
                 * 获取工作簿里面的工作表，sheet,可以用名字来获取，也可以用角标来获取
                 * 习惯性用角标来获取
                 */
                HSSFSheet sheet = workbook.getSheetAt(0);
                /**
                 * 开始遍历工作表里面的行，需要知道有多少行
                 */
                int lastRowNum = sheet.getLastRowNum();
                List<Exam> list = new ArrayList<Exam>();
                for (int i=2;i<=lastRowNum;i++){
                    /**
                     * 使用角标获取Excel工作表中的行，0和1不是试题
                     */
                    HSSFRow row = sheet.getRow(i);
                    Exam exam = new Exam();
                    /**
                     * 获取行中第一列，题型
                     */
                    HSSFCell cell = row.getCell(0);
                    String value = cell.getStringCellValue();
                    exam.setEtype(value);

                    /**
                     * 题干
                     */
                    String tigan = row.getCell(1).getStringCellValue();
                    exam.setEname(tigan);

                    /**
                     * 答案，答案和选项需要装进另外一个list里面去
                     * 答案拿到是一个
                     */
                    String daan = row.getCell(2).getStringCellValue();

                    /**
                     * 分支，装进去
                     */
                    double fenzhi = row.getCell(3).getNumericCellValue();
                    exam.setEfenzhi(fenzhi);

                    /**
                     * 选项，开始判断，单选和多选有四个选项
                     * 判断题有两个选项，填空题和问答题，没有选项
                     */

                    if("单选题".equals(value)){
                        /**
                         * 获取A/B/C/D四个选项的值
                         */
                        String xxA = row.getCell(4).getStringCellValue();
                        String xxB = row.getCell(5).getStringCellValue();
                        String xxC = row.getCell(6).getStringCellValue();
                        String xxD = row.getCell(7).getStringCellValue();

                        ExamOption oA = new ExamOption();
                        oA.setOname(xxA);

                        ExamOption oB = new ExamOption();
                        oB.setOname(xxB);

                        ExamOption oC = new ExamOption();
                        oC.setOname(xxC);

                        ExamOption oD = new ExamOption();
                        oD.setOname(xxD);

                        /**
                         * 判断答案
                         */
                        if("A".equalsIgnoreCase(daan)){
                            oA.setIstrue(1);
                        }else if("B".equalsIgnoreCase(daan)){
                            oB.setIstrue(1);
                        }else if("C".equalsIgnoreCase(daan)){
                            oC.setIstrue(1);
                        }else if("D".equalsIgnoreCase(daan)){
                            oD.setIstrue(1);
                        }else{
                            System.out.println("题目有误");
                        }

                        exam.getOptions().add(oA);
                        exam.getOptions().add(oB);
                        exam.getOptions().add(oC);
                        exam.getOptions().add(oD);

                    }else if("多选题".equals(value)){
                        /**
                         * 获取A/B/C/D四个选项的值
                         */
                        String xxA = row.getCell(4).getStringCellValue();
                        String xxB = row.getCell(5).getStringCellValue();
                        String xxC = row.getCell(6).getStringCellValue();
                        String xxD = row.getCell(7).getStringCellValue();

                        ExamOption oA = new ExamOption();
                        oA.setOname(xxA);

                        ExamOption oB = new ExamOption();
                        oA.setOname(xxB);

                        ExamOption oC = new ExamOption();
                        oA.setOname(xxC);

                        ExamOption oD = new ExamOption();
                        oA.setOname(xxD);

                        /**
                         * 多选需要先把答案拿出来，进行分割
                         */
                        String[] split = daan.split("\\|");

                        List<String> daans = Arrays.asList(split);


                        if(daans.contains("A")){
                            oA.setIstrue(1);
                        }

                        if(daans.contains("B")){
                            oB.setIstrue(1);
                        }

                        if(daans.contains("C")){
                            oC.setIstrue(1);
                        }

                        if(daans.contains("D")){
                            oD.setIstrue(1);
                        }

                        exam.getOptions().add(oA);
                        exam.getOptions().add(oB);
                        exam.getOptions().add(oC);
                        exam.getOptions().add(oD);

                    }else if("判断题".equals(value)){
                        /**
                         * 获取A/B/C/D四个选项的值
                         */
                        String xxA = row.getCell(4).getStringCellValue();
                        String xxB = row.getCell(5).getStringCellValue();

                        ExamOption oA = new ExamOption();
                        oA.setOname(xxA);

                        ExamOption oB = new ExamOption();
                        oA.setOname(xxB);

                        if("A".equalsIgnoreCase(daan)){
                            oA.setIstrue(1);
                        }else if("B".equalsIgnoreCase(daan)){
                            oB.setIstrue(1);
                        }else{
                            System.out.println("题目有误");
                        }
                        exam.getOptions().add(oA);
                        exam.getOptions().add(oB);
                    }
                    list.add(exam);
                }
                System.out.println(1);
                System.out.println(list);
                System.out.println(list.size());
            }else{
                System.out.println("表格类型错误");
            }


        }catch(Exception e){
           e.printStackTrace();
        }
    }

}
