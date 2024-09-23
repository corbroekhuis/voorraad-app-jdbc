package com.warehouse.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class RestockScheduler {

    @Scheduled(fixedDelay = 1000)
    //@Scheduled(fixedRate = 1000)
    //@Scheduled(cron = "0 15 10 15 * ?")
    public void RestockTask() {
        System.out.println(
                "Fixed delay task - " + System.currentTimeMillis() / 10000);
    }
}
