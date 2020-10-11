package com.meifute.nm.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class PayWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayWebApplication.class, args);
    }

}
