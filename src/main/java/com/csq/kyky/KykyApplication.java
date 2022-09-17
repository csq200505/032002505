package com.csq.kyky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KykyApplication {

    public static void main(String[] args) {
        SpringApplication.run(KykyApplication.class, args);
    }

}
