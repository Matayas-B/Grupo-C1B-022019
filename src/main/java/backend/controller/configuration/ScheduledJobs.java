package backend.controller.configuration;

import backend.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledJobs {

    @Autowired
    JobService jobService;

    // @Scheduled(cron = "0 0 23 ? * ?", zone = "UTC") ---> TODO: Real job hour
    @Scheduled(cron = "0 0 16 ? * ?", zone = "UTC")
    public void sendCustomersPurchasesEmail() {
         jobService.sendCustomersPurchasesEmail();
    }

    @Scheduled(cron = "0 15 23 ? * ?", zone = "UTC")
    public void sendSuppliersSalesEmail() {
        jobService.sendSuppliersSalesEmail();
    }
}
