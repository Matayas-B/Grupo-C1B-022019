package backend.service;

import backend.controller.configuration.emailTemplateDTOs.UserPurchases;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.List;

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

    public void sendWelcomeEmail(String toMail, String subject, String userName) throws MessagingException {
        javaMailSender.send(buildWelcomeEmail(toMail, subject, userName));
    }

    public void sendInvalidMenuEmail(String toMail, String subject, String menuName, Double score) throws MessagingException {
        javaMailSender.send(buildInvalidMenuEmail(toMail, subject, menuName, score));
    }

    public void sendInvalidServiceEmail(String toMail, String subject, String serviceName) throws MessagingException {
        javaMailSender.send(buildInvalidServiceEmail(toMail, subject, serviceName));
    }

    public void sendDailyPurchases(String subject, List<UserPurchases> dailyPurchases, boolean isSupplier) throws MessagingException {
        for (UserPurchases dailyPurchase : dailyPurchases) {
            javaMailSender.send(buildDailyPurchasesEmail(dailyPurchase.getUser().getEmail(), subject, dailyPurchase, isSupplier));
        }
    }

    /* Private Methods */

    private MimeMessagePreparator buildWelcomeEmail(String toMail, String subject, String userName) throws MessagingException {
        Context context = new Context();
        context.setVariable("message", userName);
        String content = templateEngine.process("NewUserMailTemplate", context);
        List<Pair<String, String>> resources = Arrays.asList(
                new Pair<>("viendasya_icon.png", "./images/viendasya_icon.png"),
                new Pair<>("linkedin.png", "./images/linkedin.png"));
        return buildFinalMessagePreparator(toMail, subject, content, resources, true);
    }

    private MimeMessagePreparator buildInvalidMenuEmail(String toMail, String subject, String menuName, Double score) throws MessagingException {
        Context context = new Context();
        context.setVariable("message", menuName);
        context.setVariable("score", score);
        String content = templateEngine.process("InvalidMenuMailTemplate", context);
        List<Pair<String, String>> resources = Arrays.asList(
                new Pair<>("sad_emoji.png", "./images/sad_emoji.png"),
                new Pair<>("viendasya_icon.png", "./images/viendasya_icon.png"),
                new Pair<>("linkedin.png", "./images/linkedin.png"));
        return buildFinalMessagePreparator(toMail, subject, content, resources, true);
    }

    private MimeMessagePreparator buildInvalidServiceEmail(String toMail, String subject, String serviceName) throws MessagingException {
        Context context = new Context();
        context.setVariable("message", serviceName);
        String content = templateEngine.process("InvalidServiceMailTemplate", context);
        List<Pair<String, String>> resources = Arrays.asList(
                new Pair<>("sealed_emoji.png", "./images/sealed_emoji.png"),
                new Pair<>("animated_emoji.png", "./images/animated_emoji.png"),
                new Pair<>("viendasya_icon.png", "./images/viendasya_icon.png"),
                new Pair<>("linkedin.png", "./images/linkedin.png"));
        return buildFinalMessagePreparator(toMail, subject, content, resources, true);
    }

    private MimeMessagePreparator buildDailyPurchasesEmail(String toMail, String subject, UserPurchases dailyPurchase, boolean isSupplier) throws MessagingException {
        Context context = new Context();
        context.setVariable("isSupplier", isSupplier);
        context.setVariable("userName", dailyPurchase.getUser().getName());
        context.setVariable("today", dailyPurchase.getToday().toString("dd/MM/yyyy"));
        context.setVariable("menuPurchases", dailyPurchase.getMenuPurchases());
        context.setVariable("totalAmount", dailyPurchase.getTotalAmount());
        String content = templateEngine.process("DailyPurchasesMailTemplate", context);
        List<Pair<String, String>> resources = Arrays.asList(
                new Pair<>("viendasya_icon.png", "./images/viendasya_icon.png"),
                new Pair<>("linkedin.png", "./images/linkedin.png"));
        return buildFinalMessagePreparator(toMail, subject, content, resources, true);
    }

    private MimeMessagePreparator buildFinalMessagePreparator(String toMail, String subject, String content, List<Pair<String, String>> resourcesList, boolean shouldIncludeSignature) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setTo(toMail);
            messageHelper.setSubject(subject);
            String finalContent = shouldIncludeSignature ? content + addSignatureToEmail() : content;
            messageHelper.setText(finalContent, true);
            for (Pair<String, String> resource : resourcesList) {
                messageHelper.addInline(resource.getValue0(), new ClassPathResource(resource.getValue1()));
            }
        };
    }

    private String addSignatureToEmail() {
        Context context = new Context();
        return templateEngine.process("SignatureMailTemplate", context);
    }
}
