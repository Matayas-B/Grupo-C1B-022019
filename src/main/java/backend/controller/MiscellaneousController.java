package backend.controller;

import backend.service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MiscellaneousController {

    @Autowired
    private CommunicationService communicationService;

    @GetMapping(value = "miscellaneous/sendFakeMail")
    public void sendFakeMail(String toMail) {
        communicationService.sendSimpleEmail(toMail, "I dare you to enter...", "You've entered... Sadly, you've been cheated, man.");
    }

    @GetMapping(value = "miscellaneous/sendFakeWelcomeMail")
    public void sendFakeWelcomeMail(String toMail, String userName) {
        communicationService.sendWelcomeEmail(toMail, "Welcome to our world !", userName);
    }
}
