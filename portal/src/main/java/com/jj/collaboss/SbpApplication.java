package com.jj.collaboss;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class SbpApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SbpApplication.class);
        app.run(args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static class TestPojo {
        @Getter
        @Setter
        private int test;
    }

}
