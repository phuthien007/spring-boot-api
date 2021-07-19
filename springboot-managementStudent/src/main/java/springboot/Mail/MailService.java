package springboot.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Future;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;


    // send email
    @Async
    public Future<Boolean> sendEmail(StringBuffer path, String email, String token, String username) throws UnsupportedEncodingException, MessagingException {
        System.out.println("run thread send email");
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        helper.setFrom(env.getProperty("spring.mail.username"), "Tran Thien Phu");
        helper.setTo(email);
        Context context = new Context();
        context.setVariable("name", email);
        context.setVariable("url", path.replace(path.indexOf("forgot-password"), path.length(), "reset-password") + "?token=" + token);
        String subject = "Here's enter this token ";
//        String content =
//                " <p> Hello , </p> "
//                        + "<p>You have requested to reset your password.</p>"
//                        + "<p>Here's token you must send to server: </p>"
//                        + "<p><b>"+  + "</b></p>"
//                        + "Ignore this email if you do remember your password, or you have not made request!";
        String content = springTemplateEngine.process("FormSendMail", context);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(msg);
        return new AsyncResult<Boolean>(Boolean.TRUE);
    }

}
