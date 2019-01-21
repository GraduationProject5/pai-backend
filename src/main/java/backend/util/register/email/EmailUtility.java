package backend.util.register.email;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by lienming on 2019/1/21.
 */
public class EmailUtility {

    public static final String SMTPSERVER = "smtp.qq.com";
    public static final String SMTPPORT = "465"; //邮箱服务器默认端口
    private static final String FROM = "728385437@qq.com";
    private static final String PWD = "okqbuktdkltmbahf" ;

    //Tips : Use this Method in Class Servlet.
    public static String sendAccountActivateEmail(String email) {

        Properties props = new Properties();

        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", SMTPSERVER);
        props.setProperty("mail.smtp.port", SMTPPORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
        props.setProperty("mail.smtp.ssl.enable", "true");// 开启ssl

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PWD);
            }
        });


        // 开启debug模式，可以看到更多详细的输入日志
        session.setDebug(true);
        //创建邮件

        String code = EmailUtility.generateCode() ;
        try {
            MimeMessage message = new MimeMessage(session);

            message.setSentDate(new Date());
            message.setFrom(new InternetAddress(FROM,"J2EE Lem","utf-8"));
            message.setRecipient(RecipientType.TO, new InternetAddress(email,"J2EE email authentication","utf-8"));
            message.setSubject("【机器学习平台PAI】","utf-8");

            String content = "您的注册验证码为："+ code ;
            message.setContent(content, "text/html;charset=utf-8");
            message.setSentDate(new Date());
            message.saveChanges();

            Transport transport = session.getTransport();
            transport.connect(SMTPSERVER,FROM,PWD);
            Transport.send(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code ;
    }

    public static String generateCode(){
        String random=(int)((Math.random()*9+1)*100000)+"";
        return random;
    }

    public static void main(String[] args) {
        EmailUtility.sendAccountActivateEmail("javalem@163.com");
    }

}
