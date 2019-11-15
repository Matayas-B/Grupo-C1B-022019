package backend.controller;

import backend.service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class MiscellaneousController {

    @Autowired
    private CommunicationService communicationService;

    @GetMapping(value = "miscellaneous/sendFakeMail")
    public void sendFakeMail(String toMail) {
        communicationService.sendSimpleEmail(toMail, "I dare you to enter...", "You've entered... Sadly, you've been cheated, man.");
    }

    @GetMapping(value = "miscellaneous/sendFakeWelcomeMail")
    public void sendFakeWelcomeMail(String toMail, String userName) throws MessagingException {
        communicationService.sendWelcomeEmail(toMail, "Welcome to our world !", userName);
    }

    @GetMapping(value = "miscellaneous/sendFakeInvalidMenuMail")
    public void sendFakeWelcomeMail(String toMail, String menuName, Double score) throws MessagingException {
        communicationService.sendInvalidMenuEmail(toMail, "You have lost a menu!", menuName, score);
    }
}
