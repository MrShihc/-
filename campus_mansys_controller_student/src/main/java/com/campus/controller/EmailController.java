package com.campus.controller;

import com.campus.common.Result;
import com.campus.utils.EmailUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Random;

@RestController
@RequestMapping("/email")
public class EmailController {
    //发送短信
    @RequestMapping("/goEamil")
    public Result goemail(String email, HttpSession session){
        String[] se = {email};
        try {
            //生成六位随机验证码
            String random = "0123456789";
            StringBuilder str = new StringBuilder(6);
            StringBuilder st = new StringBuilder(50);
            for (int i=0;i<6;i++){
                char c = random.charAt(new Random().nextInt(random.length()));
                str.append(c);
            }
            //六位数验证码存入session
            session.setAttribute("ucode",str.toString());
            st.append("验证码"+str.toString()+"用于注册登录，泄露后果自负，如非本人操作请忽略");
            //调用发送验证码方法
            int em = EmailUtils.sendEmail(se,st);
            if(em==1){
                return new Result(true,"发送成功");
            }else{
                return new Result(false,"发送异常");
            }
        } catch (Exception e) {
            return new Result(false,"发送失败");
        }
    }
}
