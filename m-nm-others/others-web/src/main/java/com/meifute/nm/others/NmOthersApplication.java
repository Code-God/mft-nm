package com.meifute.nm.others;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
@ComponentScan(value = "com.meifute.nm")
public class NmOthersApplication {

    public static void main(String[] args) {
        SpringApplication.run(NmOthersApplication.class, args);
    }

}
