package com.jj.sbp.controller;

import com.jj.sbp.domain.Verification;
import com.jj.sbp.dto.VerificationDto;
import com.jj.sbp.error.NotFoundException;
import com.jj.sbp.repository.VerificationRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"hidden", "localdev"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VerificationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private VerificationRepository verificationRepository;


    @Test
    @Order(1)
    public void sendVerificationCode() throws Exception {
        String name = "jsbae@jj.com";

//        String url = "http://localhost:" + port + "/service/confirm/" + name;
        String url = "http://localhost:" + port + "/service/confirm/requestCode";

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, name, String.class);
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(2)
    public void checkCode() throws Exception {
//        sendVerificationCode();

        Verification verification = verificationRepository.findById(1L).orElseThrow(() -> new NotFoundException("1L not found"));

        String url = "http://localhost:" + port + "/service/confirm/checkCode";
        VerificationDto dto = VerificationDto.builder()
                .emailTo(verification.getEmailTo()).code(verification.getCode())
                .build();

        ResponseEntity responseEntity = restTemplate.postForEntity(url, dto, VerificationDto.class);
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}