package com.download.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.download")
public class DlAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(DlAppApplication.class, args);
    }

     private static final Logger LOGGER = LoggerFactory.getLogger(DlAppApplication.class);

    @Scheduled(fixedRate = 5000)
    public void logMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long mb = 1024 * 1024;
//        log memory usage in one line
        LOGGER.info("free memory: " + freeMemory / mb + " MB, allocated memory: " + allocatedMemory / mb + " MB, max memory: " + maxMemory / mb + " MB");
    }
}
