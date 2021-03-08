package com.jj.sbp.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "localdev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void findAll() throws Exception {
        String url = "http://localhost:" + port + "/service/products";

        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(responseEntity);
    }

//    @Test
//    public void findById() throws Exception {
//        String id = "7f000001-7551-1dd1-8175-51bddab10000";
//
//        String url = "http://localhost:" + port + "/service/company/" + id;
//
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).contains("companyName");
//    }


}