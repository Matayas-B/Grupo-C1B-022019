package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MiscellanousController {

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping(value = "miscellanous/mail")
    public void sendEmail(String toMail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toMail);
        message.setSubject("I dare you to enter...");
        message.setText("You've entered... Sadly, you've been cheated, man.");
        javaMailSender.send(message);
    }
}
