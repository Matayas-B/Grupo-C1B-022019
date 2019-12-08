package backend.controller;

import backend.service.CommunicationService;
import backend.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class MiscellaneousController {

    @Autowired
    private CommunicationService communicationService;
    @Autowired
    private JobService jobService;

    @GetMapping
    public String Index() {
        return "<h1 style=\"text-align:center;color: limegreen;\">. . : ViendasYa is up and running : . .</h1>";
    }

    @GetMapping(value = "miscellaneous/sendFakeMail")
    public void sendFakeMail(String toMail) {
        communicationService.sendSimpleEmail(toMail, "I dare you to enter...", "You've entered... Sadly, you've been cheated, man.");
    }

    @GetMapping(value = "miscellaneous/sendFakeWelcomeMail")
    public void sendFakeWelcomeMail(String toMail, String userName) throws MessagingException {
        communicationService.sendWelcomeEmail(toMail, "Welcome to our world !", userName, "123");
    }

    @GetMapping(value = "miscellaneous/sendFakeInvalidMenuMail")
    public void sendFakeInvalidMenuMail(String toMail, String menuName, Double score) throws MessagingException {
        communicationService.sendInvalidMenuEmail(toMail, "You have lost a menu!", menuName, score);
    }

    @GetMapping(value = "miscellaneous/sendFakeInvalidServiceMail")
    public void sendFakeInvalidServiceMail(String toMail, String serviceName) throws MessagingException {
        communicationService.sendInvalidServiceEmail(toMail, "Oh man, your service sucks!", serviceName);
    }

    @GetMapping(value = "miscellaneous/customerPurchasesEmail")
    public void customerPurchasesEmail() throws MessagingException {
        jobService.sendCustomersPurchasesEmail();
    }

    @GetMapping(value = "miscellaneous/supplierPurchasesEmail")
    public void supplierPurchasesEmail() throws MessagingException {
        jobService.sendSuppliersSalesEmail();
    }
}
