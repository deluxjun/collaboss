package com.jj.collaboss.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jj.collaboss.domain.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// code for reference
// uncomment 2 lines for test
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureMockMvc

//@ContextConfiguration(classes = { SpringTestConfiguration.class })
//@WebMvcTest(CompanyController.class)
class CompanyControllerMockTest {
    @Autowired
    MockMvc mockMvc; // @WebMvcTest를 사용하면 자동으로 Bean으로 등록됨

    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/service/company/hello"))           // 요청을 만드는 단계
                // 응답을 확인하는 단계
                .andExpect(status().isOk())
                .andExpect(content().string("hello1"))
                .andDo(print())
        ;
    }

    @Test
    public void create() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Company com = new Company();
        com.setCompanyName("jj");
        String json = mapper.writeValueAsString(com);
//        String json = "{\"username\": \"junseo\", \"password\": \"1234\"}";

        mockMvc.perform(post("/service/company/create")           // 요청을 만드는 단계
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                // 응답을 확인하는 단계
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName", is(equalTo("jj"))))
                ;
    }
}