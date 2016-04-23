package me.aipao.plugin.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;


/**
 * 发送普通邮件
 */
public class EmailSender {

	private static final Log logger = Log.getLog(EmailSender.class);
	
    // 邮箱配置
    private static String host = PropKit.use("config.txt").get("mail.host");
    private static String user = PropKit.use("config.txt").get("mail.user");
    private static String pass = PropKit.use("config.txt").get("mail.pass");
    private static String name = PropKit.use("config.txt").get("mail.name");
    
    /**
     * 此段代码用来发送普通电子邮件
     */
    public static boolean send(String subject, String body, String[] receivers) {
        try {
            Properties props = new Properties(); // 获取系统环境
            Authenticator auth = new MyAutherticator(); // 进行邮件服务器用户认证
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props, auth);
            // 设置session,和邮件服务器进行通讯。
            MimeMessage message = new MimeMessage(session);
            // message.setContent("foobar, "application/x-foobar"); // 设置邮件格式
            message.setSubject(subject); // 设置邮件主题
            message.setText(body); // 设置邮件正文
//			message.setHeader(mail_head_name, mail_head_value); // 设置邮件标题
            message.setSentDate(new Date()); // 设置邮件发送日期
            Address address = new InternetAddress(user, name);
            message.setFrom(address); // 设置邮件发送者的地址
            Address toAddress = null;
            for (int i = 0; i < receivers.length; i++) {
                toAddress = new InternetAddress(receivers[i]); // 设置邮件接收方的地址
                message.addRecipient(Message.RecipientType.TO, toAddress);
            }
            toAddress = null;
            Transport.send(message); // 发送邮件
            System.out.println("send ok!");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("EmailSender.send error.", ex);
        }
        return false;
    }

    /**
     * 用来进行服务器对用户的认证
     */
    private static class MyAutherticator extends Authenticator {
        @Override
		public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, pass);
        }
    }

    public static void main(String[] args) {
        send("测试邮件标题", "测试邮件内容", new String[]{"mobangjack@foxmail.com"});
    }

}
