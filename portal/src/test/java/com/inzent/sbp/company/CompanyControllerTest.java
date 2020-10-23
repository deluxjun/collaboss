package com.inzent.sbp.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inzent.sbp.domain.Company;
import com.inzent.sbp.dto.CompanyDto;
import com.inzent.sbp.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompanyControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void findByCompanyName() throws Exception {
        String name = "inzent";

        String url = "http://localhost:" + port + "/service/company/name/" + name;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).contains("companyName");
    }

    @Test
    public void findById() throws Exception {
        String id = "7f000001-7551-1dd1-8175-51bddab10000";

        String url = "http://localhost:" + port + "/service/company/" + id;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).contains("companyName");
    }


    @Test
    public void save() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        CompanyDto companyDto = CompanyDto.builder()
                .companyName("inzent").build();

        String url = "http://localhost:" + port + "/service/company";

        ResponseEntity<CompanyDto> responseEntity = restTemplate.postForEntity(url, companyDto, CompanyDto.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}