package backend.controller.configuration;

import backend.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class ScheduledJobs {

    @Autowired
    JobService jobService;

    // @Scheduled(cron = "0 0 23 ? * ?", zone = "UTC") ---> TODO: uncomment
    @Scheduled(cron = "0 35 00 ? * ?", zone = "UTC")
    public void sendCustomersPurchasesEmail() throws MessagingException {
         jobService.sendCustomersPurchasesEmail();
    }

    // @Scheduled(cron = "0 15 23 ? * ?", zone = "UTC") ---> TODO: uncomment
    @Scheduled(cron = "0 35 00 ? * ?", zone = "UTC")
    public void sendSuppliersSalesEmail() throws MessagingException {
        jobService.sendSuppliersSalesEmail();
    }
}
