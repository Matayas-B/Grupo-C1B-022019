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
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setTo(toMail);
            messageHelper.setSubject(subject);
            String content = buildWelcomeEmail(userName);
            messageHelper.setText(content, true);
            messageHelper.addInline("viendasya_icon.png", new ClassPathResource("./images/viendasya_icon.png"));
            messageHelper.addInline("linkedin.png", new ClassPathResource("./images/linkedin.png"));
        };

        javaMailSender.send(messagePreparator);
    }

    /* Private Methods */

    private String buildWelcomeEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("newUserMailTemplate", context);
    }
}
