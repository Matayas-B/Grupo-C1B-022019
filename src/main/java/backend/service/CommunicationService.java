package backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class CommunicationService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    public void sendSimpleEmail(String toMail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toMail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    public void sendWelcomeEmail(String toMail, String subject, String userName) {
        MimeMessagePreparator messagePreparator = buildMessagePreparator(toMail, subject, buildWelcomeEmail(userName));
        javaMailSender.send(messagePreparator);
    }

    /* Private Methods */

    private MimeMessagePreparator buildMessagePreparator(String toMail, String subject, String content) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setTo(toMail);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            messageHelper.addInline("viendasya_icon.png", new ClassPathResource("./images/viendasya_icon.png"));
            messageHelper.addInline("linkedin.png", new ClassPathResource("./images/linkedin.png"));
        };
    }

    private String buildWelcomeEmail(String userName) {
        Context context = new Context();
        context.setVariable("message", userName);
        return templateEngine.process("newUserMailTemplate", context);
    }
}
