package com.inzent.sbp.config;

import com.inzent.sbp.dto.CompanyDto;
import com.inzent.sbp.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// initialization data for test
@Profile("localdev")
@Component
public class ImportRunner implements ApplicationRunner {

    @Autowired
    CompanyService companyService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        companyService.save(CompanyDto.builder()
                .companyName("inzent")
                .build());
    }
}
