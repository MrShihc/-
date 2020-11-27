package com.campus.controller;

import com.campus.entity.Exam;
import com.campus.utils.PoiImportExcel;
import com.campus.common.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 文件上传控制层
 */
@Controller
@RequestMapping("/file")
public class FileController {

    /**
     * 上传文件
     * @param filename
     * @return
     */
    @RequestMapping("/fileUpload")
    @ResponseBody
    public Result fileUpload(@RequestBody MultipartFile filename, HttpServletRequest request){
        /**
         * 应该返回上传成功还是失败，失败了，需要把原因返回出去，
         * 成功了，需要把总分返回出去
         */

        //创建Result对象
        Result result1 = new Result();

        if(filename==null || filename.equals("") || filename.toString()==""){
            result1.setSuccess("fail");
            result1.setMessage("您还没有上传文件哦！");
            return result1;
        }

        //获取导入的Excel表格的所有信息
        Result result = PoiImportExcel.fileupload(filename);



        //获取Excel表格的信息
        List<Exam> data1 = (List<Exam>) result.getData1();
        request.getSession().setAttribute("exam",data1);

        //判断结果是否为success
        if(result.getSuccess()=="success"){
            //求出总分
            Double efenzhi = 0.0;
            for(Exam exam:data1){
                efenzhi += exam.getEfenzhi();
            }
            //把得到的总分进行赋值
            result.setData2(efenzhi+"");

        }



        //返回结果
        return result;
    }
}
