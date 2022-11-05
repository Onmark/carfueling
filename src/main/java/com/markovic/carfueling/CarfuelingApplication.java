package com.markovic.carfueling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CarfuelingApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CarfuelingApplication.class, args);
    }

}
