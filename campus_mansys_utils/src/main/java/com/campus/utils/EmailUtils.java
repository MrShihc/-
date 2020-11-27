package com.campus.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtils {
    // 邮件服务器的主机名:如 "smtp.qq.com"，不同的邮箱服务器主机名是不一样的，例如网易163邮箱是"smtp.163.com"
    private final static String mailServer = "smtp.qq.com";
    // 登录邮箱的账号:如我的 "2505613293@qq.com"，这里改成你自己的
    private final static String loginAccount = "2996912571@qq.com";
    // 登录邮箱时候需要的授权码:可以进入邮箱,账号设置那里"生成授权码"，这里改成你自己的
    private final static String loginAuthCode = "vkjmlopjpkaidhaf";
    // 发件人，发件人的邮箱，跟上面那个一样就可以了
    private final static String sender = "2996912571@qq.com";

    /**
     *
     * @param receiverAddresses
     * @param s
     * @return
     */

    public static int sendEmail(String[] receiverAddresses,StringBuilder s) {

        int res = 0;
        String emailSubject="石海超";
        try {
            // 跟smtp服务器建立一个连接
            Properties p = new Properties();
            // 设置邮件服务器主机名
            p.setProperty("mail.host", mailServer);
            // 发送服务器需要身份验证,要采用指定用户名密码的方式去认证
            p.setProperty("mail.smtp.auth", "true");
            // 发送邮件协议名称
            p.setProperty("mail.transport.protocol", "smtp");

            // 开启SSL加密，否则会失败
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            p.put("mail.smtp.ssl.enable", "true");
            p.put("mail.smtp.ssl.socketFactory", sf);

            // 创建session
            Session session = Session.getDefaultInstance(p,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            // 用户名可以用QQ账号也可以用邮箱的别名:第一个参数为邮箱账号,第二个为授权码
                            PasswordAuthentication pa = new PasswordAuthentication(
                                    loginAccount, loginAuthCode);
                            return pa;
                        }
                    });

            // 设置打开调试状态
            session.setDebug(true);

            // 可以发送几封邮件:可以在这里 for循环多次
            // 声明一个Message对象(代表一封邮件),从session中创建
            MimeMessage msg = new MimeMessage(session);
            // 邮件信息封装

            // 1发件人
            InternetAddress sendInternetAddress = new InternetAddress(sender);
            msg.setFrom(sendInternetAddress);

            // 2一个的收件人
         /*InternetAddress receiveInternetAddress = new InternetAddress(receiverAddress);
         msg.setRecipient(RecipientType.TO, receiveInternetAddress);*/

            // 2多个收件人,把String数组转成InternetAddress数组
            InternetAddress[] receiveInternetAddresses = new
                    InternetAddress[receiverAddresses.length];
            for (int i = 0; i < receiverAddresses.length; i++) {
                receiveInternetAddresses[i] = new
                        InternetAddress(receiverAddresses[i]);
            }
            msg.setRecipients(RecipientType.TO, receiveInternetAddresses);

            // 3邮件内容:主题、内容
            msg.setSubject(emailSubject);
            // msg.setContent("Hello, 我是debug!!!", );//纯文本
            msg.setContent(s.toString(),"text/html;charset=utf-8");// 发html格式的文本
            // 发送动作
            Transport.send(msg);
            System.out.println("邮件发送成功");
            res = 1;
        } catch (Exception e) {
            System.out.println("邮件发送失败: " + e.getMessage());
            res = 0;
        }
        return res;
    }
}
