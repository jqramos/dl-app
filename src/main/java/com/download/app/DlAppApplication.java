package com.download.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.download")
public class DlAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DlAppApplication.class, args);
    }

}
