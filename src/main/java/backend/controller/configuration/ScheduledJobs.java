package backend.controller.configuration;

import backend.service.CommunicationService;
import backend.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class ScheduledJobs {

    @Autowired
    JobService jobService;
    @Autowired
    CommunicationService communicationService;

    @Scheduled(cron = "0 0 23 ? * ?", zone = "UTC")
    public void sendCustomersPurchasesEmail() throws MessagingException {
         jobService.sendCustomersPurchasesEmail();
    }

    @Scheduled(cron = "0 15 23 ? * ?", zone = "UTC")
    public void sendSuppliersSalesEmail() throws MessagingException {
        jobService.sendSuppliersSalesEmail();
    }

    @Scheduled(cron = "${spring.scheduler.cronjob}", zone = "UTC")
    public void sendFakeEmail() throws MessagingException {
        communicationService.sendInvalidServiceEmail("hernan.beca@gmail.com", "Oh man, your service sucks!", "TESTING");;
    }
}
