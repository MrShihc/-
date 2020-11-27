package com.campus.utils;

import com.campus.entity.Exam;
import com.campus.entity.ExamOption;

import java.util.List;

public class ExamReadUtils {

   public static Double getExamScore(List<Exam> examList){
       Double score = 0.0;

       /**
        * 开始阅卷
        */
       for (Exam exam : examList) {
           //用每道题的分支去乘数值，1代表正确，相乘会有变化；
                                //0代表错误，相乘不会相加
           score+=exam.getEfenzhi()*getExamIsTrue(exam);
       }
       return score;
   }


   private static int getExamIsTrue(Exam exam){
       /**
        * 可以分开判断题型
        * 单选：把正确答案找到，isture=1的，然后看这个选项学生有没有选择
        * 多选：判断有几个正确答案，然后看用户选择了几个？要是不一样，肯定错了
        */
       if("单选题".equals(exam.getEtype()) || "多选题".equals(exam.getEtype()) ||
               "判断题".equals(exam.getEtype())){
           //获取到选项信息
           List<ExamOption> options = exam.getOptions();
           //正确的个数
           Integer trueCount = 0;
           //选择的个数
           Integer checkCount = 0;
           //进行循环
           for (ExamOption option : options) {
               /**
                * 判断本题目有几个正确答案
                */
               trueCount+=option.getIstrue();
               /**
                * 判断用户选中了几个正确答案
                */
               if(option.getMytrue()!=null){
                   checkCount++;
               }
           }
           if(trueCount!=checkCount){
                return 0;
           }else{
               for (ExamOption option : options) {
                   /**
                    * 当istrue等于1，表示这个答案是正确的，但是你没有选择，证明该题错了
                    */
                   if(option.getIstrue()==1 && option.getMytrue()==null){
                       return 0;
                   }
               }
               return 1;
           }
       }
       return 0;
   }

}
