package com.inzent.sbp;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SbpApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SbpApplication.class);
        app.run(args);
    }


    public static class TestPojo {
        @Getter
        @Setter
        private int test;
    }

}
