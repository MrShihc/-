package com.campus.utils;

import com.campus.entity.Exam;
import com.campus.entity.ExamOption;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * POI导入Excel表格
 */
public class PoiImportExcel {

    //单选题
    public static final String SINGLECHOICE = "单选题";
    //多选题
    public static final String MULTIPLECHOICE = "多选题";
    //判断题
    public static final String TUREORFALSEQUESTIONS = "判断题";
    //问答题
    public static final String ESSAYQUESTION = "问答题";
    //填空题
    public static final String GAPFILLING = "填空题";
    //选项A
    public static final String A = "A";
    //选项B
    public static final String B = "B";
    //选项C
    public static final String C = "C";
    //选项D
    public static final String D = "D";

    /**
     * 支持2007版的Excel表格上传
     */
    public static void support07Excel(String value, XSSFRow row, String daan, Exam exam, Result result) {

        if (SINGLECHOICE.equals(value)) {
            /**
             * 获取A/B/C/D四个选项的值
             */

            //选项A的判断
            XSSFCell cell = row.getCell(4);
            String xxA = "";
            if(cell==null || cell.equals("") || cell.toString()==""){
                result.setMessage("选项A不能为空哦！");
            }else{
                xxA = cell.getStringCellValue();
            }

            //选项B的判断
            XSSFCell cell1 = row.getCell(5);
            String xxB = "";
            if(cell1==null || cell1.equals("") || cell1.toString()==""){
                result.setMessage("选项B不能为空哦！");
                result.setSuccess("fail");
            }else{
                xxB = cell1.getStringCellValue();
            }

            //选项C的判断
            XSSFCell cell2 = row.getCell(6);
            String xxC = "";
            if(cell2==null || cell2.equals("") || cell2.toString()==""){
                result.setMessage("选项C不能为空哦！");
                result.setSuccess("fail");
            }else{
                xxC = cell2.getStringCellValue();
            }

            //选项D的判断
            XSSFCell cell3 = row.getCell(7);
            String xxD = "";
            if(cell3==null || cell3.equals("") || cell3.toString()==""){
                result.setMessage("选项D不能为空哦！");
                result.setSuccess("fail");
            }else{
                xxD = cell3.getStringCellValue();
            }

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
            if (A.equalsIgnoreCase(daan)) {
                oA.setIstrue(1);
            } else if (B.equalsIgnoreCase(daan)) {
                oB.setIstrue(1);
            } else if (C.equalsIgnoreCase(daan)) {
                oC.setIstrue(1);
            } else if (D.equalsIgnoreCase(daan)) {
                oD.setIstrue(1);
            } else {
                result.setMessage("题目有误！");
                result.setSuccess("fail");
            }
            exam.getOptions().add(oA);
            exam.getOptions().add(oB);
            exam.getOptions().add(oC);
            exam.getOptions().add(oD);
        }else if(MULTIPLECHOICE.equals(value)){     //多选题
            /**
             * 获取A/B/C/D四个选项的值
             */
            //选项A的判断
            XSSFCell cell = row.getCell(4);
            String xxA = "";
            if(cell==null || cell.equals("") || cell.toString()==""){
                result.setMessage("选项A不能为空哦！");
            }else{
                xxA = cell.getStringCellValue();
            }

            //选项B的判断
            XSSFCell cell1 = row.getCell(5);
            String xxB = "";
            if(cell1==null || cell1.equals("") || cell1.toString()==""){
                result.setMessage("选项B不能为空哦！");
                result.setSuccess("fail");
            }else{
                xxB = cell1.getStringCellValue();
            }

            //选项C的判断
            XSSFCell cell2 = row.getCell(6);
            String xxC = "";
            if(cell2==null || cell2.equals("") || cell2.toString()==""){
                result.setMessage("选项C不能为空哦！");
                result.setSuccess("fail");
            }else{
                xxC = cell2.getStringCellValue();
            }

            //选项D的判断
            XSSFCell cell3 = row.getCell(7);
            String xxD = "";
            if(cell3==null || cell3.equals("") || cell3.toString()==""){
                result.setMessage("选项D不能为空哦！");
                result.setSuccess("fail");
            }else{
                xxD = cell3.getStringCellValue();
            }

            ExamOption oA = new ExamOption();
            oA.setOname(xxA);

            ExamOption oB = new ExamOption();
            oB.setOname(xxB);

            ExamOption oC = new ExamOption();
            oC.setOname(xxC);

            ExamOption oD = new ExamOption();
            oD.setOname(xxD);

            /**
             * 多选需要先把答案拿出来，进行分割
             */
            String[] split = daan.split("\\|");

            List<String> daans = Arrays.asList(split);


            if(daans.contains(A)){
                oA.setIstrue(1);
            }

            if(daans.contains(B)){
                oB.setIstrue(1);
            }

            if(daans.contains(C)){
                oC.setIstrue(1);
            }

            if(daans.contains(D)){
                oD.setIstrue(1);
            }

            exam.getOptions().add(oA);
            exam.getOptions().add(oB);
            exam.getOptions().add(oC);
            exam.getOptions().add(oD);

        }else if(TUREORFALSEQUESTIONS.equals(value)){       //判断题
            /**
             * 获取A/B/C/D四个选项的值
             */
            //选项A的判断
            XSSFCell cell = row.getCell(4);
            String xxA = "";
            if(cell==null || cell.equals("") || cell.toString()==""){
                result.setMessage("选项A不能为空哦！");
            }else{
                xxA = cell.getStringCellValue();
            }

            //选项B的判断
            XSSFCell cell1 = row.getCell(5);
            String xxB = "";
            if(cell1==null || cell1.equals("") || cell1.toString()==""){
                result.setMessage("选项B不能为空哦！");
                result.setSuccess("fail");
            }else{
                xxB = cell1.getStringCellValue();
            }

            ExamOption oA = new ExamOption();
            oA.setOname(xxA);

            ExamOption oB = new ExamOption();
            oB.setOname(xxB);

            if(A.equalsIgnoreCase(daan)){
                oA.setIstrue(1);
            }else if(B.equalsIgnoreCase(daan)){
                oB.setIstrue(1);
            }else{
                result.setSuccess("fail");
                result.setMessage("该判断题题目有误");
            }
            exam.getOptions().add(oA);
            exam.getOptions().add(oB);
        }
    }

    /**
     * 支持2003版的Excel表格上传
     */
    public static void support03Excel(String value,HSSFRow row,String daan,Exam exam,Result result) {
        if (SINGLECHOICE.equals(value)) {       //单选题
            /**
             * 获取A/B/C/D四个选项的值
             */

            //选项A的判断
            HSSFCell cell = row.getCell(4);
            String xxA = "";
            if(cell==null || cell.equals("") || cell.toString()==""){
                result.setMessage("选项A不能为空哦！");
            }else{
                xxA = cell.getStringCellValue();
            }

            //选项B的判断
            HSSFCell cell1 = row.getCell(5);
            String xxB = "";
            if(cell1==null || cell1.equals("") || cell1.toString()==""){
                result.setMessage("选项B不能为空哦！");
                result.setSuccess("fail");
            }else{
                xxB = cell1.getStringCellValue();
            }

            //选项C的判断
            HSSFCell cell2 = row.getCell(6);
            String xxC = "";
            if(cell2==null || cell2.equals("") || cell2.toString()==""){
                result.setMessage("选项C不能为空哦！");
                result.setSuccess("fail");
            }else{
                xxC = cell2.getStringCellValue();
            }

            //选项D的判断
            HSSFCell cell3 = row.getCell(7);
            String xxD = "";
            if(cell3==null || cell3.equals("") || cell3.toString()==""){
                result.setMessage("选项D不能为空哦！");
                result.setSuccess("fail");
            }else{
                xxD = cell3.getStringCellValue();
            }

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
            if (A.equalsIgnoreCase(daan)) {
                oA.setIstrue(1);
            } else if (B.equalsIgnoreCase(daan)) {
                oB.setIstrue(1);
            } else if (C.equalsIgnoreCase(daan)) {
                oC.setIstrue(1);
            } else if (D.equalsIgnoreCase(daan)) {
                oD.setIstrue(1);
            } else {
                result.setMessage("题目有误！");
                result.setSuccess("fail");
            }

            exam.getOptions().add(oA);
            exam.getOptions().add(oB);
            exam.getOptions().add(oC);
            exam.getOptions().add(oD);
        }else if(MULTIPLECHOICE.equals(value)){     //多选题
            /**
             * 获取A/B/C/D四个选项的值
             */
            //选项A的判断
            HSSFCell cell = row.getCell(4);
            String xxA = "";
            if(cell==null || cell.equals("") || cell.toString()==""){
                result.setMessage("选项A不能为空哦！");
            }else{
                xxA = cell.getStringCellValue();
            }

            //选项B的判断
            HSSFCell cell1 = row.getCell(5);
            String xxB = "";
            if(cell1==null || cell1.equals("") || cell1.toString()==""){
                result.setMessage("选项B不能为空哦！");
                result.setSuccess("fail");
            }else{
                xxB = cell1.getStringCellValue();
            }

            //选项C的判断
            HSSFCell cell2 = row.getCell(6);
            String xxC = "";
            if(cell2==null || cell2.equals("") || cell2.toString()==""){
                result.setMessage("选项C不能为空哦！");
                result.setSuccess("fail");
            }else{
                xxC = cell2.getStringCellValue();
            }

            //选项D的判断
            HSSFCell cell3 = row.getCell(7);
            String xxD = "";
            if(cell3==null || cell3.equals("") || cell3.toString()==""){
                result.setMessage("选项D不能为空哦！");
                result.setSuccess("fail");
            }else{
                xxD = cell3.getStringCellValue();
            }

            ExamOption oA = new ExamOption();
            oA.setOname(xxA);

            ExamOption oB = new ExamOption();
            oB.setOname(xxB);

            ExamOption oC = new ExamOption();
            oC.setOname(xxC);

            ExamOption oD = new ExamOption();
            oD.setOname(xxD);

            /**
             * 多选需要先把答案拿出来，进行分割
             */
            String[] split = daan.split("\\|");

            List<String> daans = Arrays.asList(split);


            if(daans.contains(A)){
                oA.setIstrue(1);
            }

            if(daans.contains(B)){
                oB.setIstrue(1);
            }

            if(daans.contains(C)){
                oC.setIstrue(1);
            }

            if(daans.contains(D)){
                oD.setIstrue(1);
            }

            exam.getOptions().add(oA);
            exam.getOptions().add(oB);
            exam.getOptions().add(oC);
            exam.getOptions().add(oD);

        }else if(TUREORFALSEQUESTIONS.equals(value)){       //判断题
            /**
             * 获取A/B/C/D四个选项的值
             */
            //选项A的判断
            HSSFCell cell = row.getCell(4);
            String xxA = "";
            if(cell==null || cell.equals("") || cell.toString()==""){
                result.setMessage("选项A不能为空哦！");
            }else{
                xxA = cell.getStringCellValue();
            }

            //选项B的判断
            HSSFCell cell1 = row.getCell(5);
            String xxB = "";
            if(cell1==null || cell1.equals("") || cell1.toString()==""){
                result.setMessage("选项B不能为空哦！");
                result.setSuccess("fail");
            }else{
                xxB = cell1.getStringCellValue();
            }

            ExamOption oA = new ExamOption();
            oA.setOname(xxA);

            ExamOption oB = new ExamOption();
            oB.setOname(xxB);

            if(A.equalsIgnoreCase(daan)){
                oA.setIstrue(1);
            }else if(B.equalsIgnoreCase(daan)){
                oB.setIstrue(1);
            }else{
                result.setSuccess("fail");
                result.setMessage("该判断题题目有误");
            }
            exam.getOptions().add(oA);
            exam.getOptions().add(oB);
        }
    }











    /**
     * 上传试卷，并解析Excel表格信息
     */
    public static Result fileupload(MultipartFile filename){
        //创建Result对象
        Result result = new Result();

        try {
            /**
             * 从springMvc上传上来的文件中获取到输入流
             */
            InputStream inputStream = filename.getInputStream();
            //获取文件类型
            String originalFilename = filename.getOriginalFilename();
            if(originalFilename==null || originalFilename=="") {
                result.setMessage("文件上传失败！");
            }

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

                //判断行数大于2
                if(lastRowNum <2){
                    result.setMessage("您当前上传的表格数据为空哦！");
                    result.setSuccess("fail");
                    return result;
                }
                List<Exam> list = new ArrayList<Exam>();
                for (int i=2;i<=lastRowNum;i++){
                    /**
                     * 使用角标获取Excel工作表中的行，0和1不是试题
                     */
                    XSSFRow row = sheet.getRow(i);
                    Exam exam = new Exam();
                    /**
                     * 获取行中第一列，题型
                     */
                    XSSFCell cell = row.getCell(0);
                    if(cell==null || cell.equals("") || cell.toString()==""){
                        continue;
                    }
                    String value = cell.getStringCellValue();
                    exam.setEtype(value);

                    /**
                     * 题干
                     */
                    XSSFCell cell1 = row.getCell(1);
                    if(cell1==null || cell1.equals("") || cell1.toString()==""){
                        result.setMessage("第"+i+"行这道题干不能为空哦！");
                        result.setSuccess("fail");
                        return result;
                    }
                    String tigan = cell1.getStringCellValue();
                    exam.setEname(tigan);

                    /**
                     * 答案，答案和选项需要装进另外一个list里面去
                     * 答案拿到是一个
                     */
                    XSSFCell cell2 = row.getCell(2);
                    String daan = "";
                    if(value.equals("多选题") || value.equals("判断题") || value.equals("单选题")){
                       if(cell2==null || cell2.equals("") || cell2.toString()==""){
                           result.setMessage("第"+i+"行的"+tigan+"这道题答案不能为空哦！");
                           result.setSuccess("fail");
                           return result;
                       }
                    daan = cell2.getStringCellValue();
                   }

                    if(value.equals("多选题") || value.equals("判断题") || value.equals("单选题")){

                        if(!daan.contains("A") && !daan.contains("B") && !daan.contains("C") && !daan.contains("D")) {

                            result.setMessage("第"+i+"行的"+tigan+"这道题答案中不包含ABCD这四个答案中的任意一个哦！");
                            result.setSuccess("fail");
                            return result;
                        }
                    }

                    /**
                     * 分支，装进去
                     */
                    XSSFCell cell3 = row.getCell(3);
                    if(cell3==null || cell3.equals("") || cell3.toString()==""){
                        result.setMessage("第"+i+"行这道题的分支不能为空哦！");
                        result.setMessage("fail");
                        return result;
                    }
                    double fenzhi = cell3.getNumericCellValue();
                    exam.setEfenzhi(fenzhi);

                    /**
                     * 选项，开始判断，单选和多选有四个选项
                     * 判断题有两个选项，填空题和问答题，没有选项
                     */

                    support07Excel(value,row,daan,exam,result);

                    list.add(exam);
                    result.setData1(list);
                }

                return result;
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

                //判断行数大于2
                if(lastRowNum <2){
                    result.setMessage("您当前上传的表格数据为空哦！");
                    result.setSuccess("fail");
                    return result;
                }
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
                    if(cell==null || cell.equals("") || cell.toString()==""){
                       continue;
                    }
                    String value = cell.getStringCellValue();
                    exam.setEtype(value);

                    /**
                     * 题干
                     */
                    HSSFCell cell1 = row.getCell(1);
                    if(cell1==null || cell1.equals("") || cell1.toString()==""){
                        result.setMessage("第"+(i-1)+"道题干不能为空哦！");
                        result.setSuccess("fail");
                        return result;
                    }
                    String tigan = cell1.getStringCellValue();
                    exam.setEname(tigan);

                    /**
                     * 答案，答案和选项需要装进另外一个list里面去
                     * 答案拿到是一个
                     */
                    HSSFCell cell2 = row.getCell(2);
                    String daan = "";
                    if(value.equals("多选题") || value.equals("判断题") || value.equals("单选题")){
                        if(cell2==null || cell2.equals("") || cell2.toString()==""){
                            result.setMessage("第"+i+"行的"+tigan+"这道题答案不能为空哦！");
                            result.setSuccess("fail");
                            return result;
                        }
                        daan = cell2.getStringCellValue();
                    }

                    if(value.equals("多选题") || value.equals("判断题") || value.equals("单选题")){

                        if(!daan.contains("A") && !daan.contains("B") && !daan.contains("C") && !daan.contains("D")) {

                            result.setMessage("第"+i+"行的"+tigan+"这道题答案中不包含ABCD这四个答案中的任意一个哦！");
                            result.setSuccess("fail");
                            return result;
                        }
                    }

                    /**
                     * 分支，装进去
                     */
                    HSSFCell cell3 = row.getCell(3);
                    if(cell3==null || cell3.equals("") || cell3.toString()==""){
                        result.setMessage("第"+i+"行这道题的分支不能为空哦！");
                        result.setMessage("fail");
                        return result;
                    }
                    double fenzhi = cell3.getNumericCellValue();
                    exam.setEfenzhi(fenzhi);

                    /**
                     * 选项，开始判断，单选和多选有四个选项
                     * 判断题有两个选项，填空题和问答题，没有选项
                     */

                    support03Excel(value,row,daan,exam,result);

                    list.add(exam);
                }
                result.setData1(list);
            }else{
                result.setMessage("表格类型错误！");
                result.setMessage("fail");
                return result;
            }

            return result;

        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }


}
