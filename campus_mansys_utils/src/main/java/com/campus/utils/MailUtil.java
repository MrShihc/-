package com.campus.utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.sun.mail.util.MailSSLSocketFactory;

public class MailUtil {

    /**
     * 发送邮件的方法： 发送一封激活邮件
     *
     * @param mail    收件人邮箱
     * @param actcode 收件人激活码
     */
    public static void sendMail(String mail, String actcode) {
        // 1.创建连接对象javax.mail.Session
        // 2.创建邮件对象 javax.mail.Message
        final String from = "2996912571@qq.com";// 发件人电子邮箱  shc0108@163.com
        String host = "smtp.qq.com"; // 指定发送邮件的主机smtp.qq.com(QQ)|smtp.163.com(网易)
        final String authorizationCode = "vkjmlopjpkaidhaf"; //授权码   QQ授权码： vkjmlopjpkaidhaf    网易授权码：CJMZQXCYQMHPKLNP
        Properties properties = System.getProperties();// 获取系统属性
        properties.setProperty("mail.smtp.host", host);// 设置邮件服务器
        properties.setProperty("mail.smtp.auth", "true");// 打开认证

        try {
            //QQ邮箱需要下面这段代码，163邮箱不需要
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);


            // 1.获取默认session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, authorizationCode); // 发件人邮箱账号、授权码
                }
            });

            // 2.创建邮件对象
            Message message = new MimeMessage(session);
            // 2.1设置发件人
            message.setFrom(new InternetAddress(from));
            // 2.2设置接收人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            // 2.3设置邮件主题
            message.setSubject("账号激活");
            // 2.4设置邮件内容
            String content = "<html><head></head><body><h1>请点击以下链接激活注册账号</h1><h3><a href='http://localhost:8080/ActiveServlet?actcode="
                    + actcode + "'>http://localhost:8080/active?actcode=" + actcode
                    + "</href></h3></body></html>";

            message.setContent(content, "text/html;charset=UTF-8");
            // 3.发送邮件
            Transport.send(message);
            System.out.println("激活邮件成功发送!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
