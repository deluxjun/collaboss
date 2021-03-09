package com.jj.collaboss.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jj.collaboss.domain.Verification;
import com.jj.collaboss.dto.CompanyDto;
import com.jj.collaboss.dto.ProductDto;
import com.jj.collaboss.repository.CompanyRepository;
import com.jj.collaboss.repository.VerificationRepository;
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

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "localdev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompanyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    @Test
    @Order(2)
    public void findByCompanyName() throws Exception {
        String name = "jj";

        String url = "http://localhost:" + port + "/service/company/name/" + name;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).contains("companyName");
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


    @Test
    @Order(1)
    public void save() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        String code = "111111";

        Verification verification = Verification.builder()
                .emailTo("jsbae@jj.com")
                .type(Verification.VerificationType.CONFIRMATION.ordinal())
                .code(code)
                .build();
        verificationRepository.saveAndFlush(verification);

        Set<ProductDto> products = Stream.of(ProductDto.builder().id(1L).build(),ProductDto.builder().id(2L).build()).collect(Collectors.toSet());

        CompanyDto companyDto = CompanyDto.builder()
                .companyName("jj2")
                .adminName("junsoo")
                .memo("This is a memo")
                .tel("025580203")
                .usersLimit(200)
                .adminEmail("jsbae@jj.com")
                .code(code)
                .products(products)
                .build();

        System.out.println(mapper.writeValueAsString(companyDto));

        String url = "http://localhost:" + port + "/service/company";

        ResponseEntity<CompanyDto> responseEntity = restTemplate.postForEntity(url, companyDto, CompanyDto.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}