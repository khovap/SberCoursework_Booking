package com.github.khovap.coursework.bookingsource_main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BookingSourceMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingSourceMainApplication.class, args);
    }

}
