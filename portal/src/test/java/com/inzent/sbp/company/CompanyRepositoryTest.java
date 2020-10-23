package com.inzent.sbp.company;

import com.inzent.sbp.domain.Company;
import com.inzent.sbp.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CompanyRepositoryTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CompanyRepository companyRepository;

    @Test
    public void di() {
        Company com = new Company();
        com.setCompanyName("inzent");
        Company newCompany = companyRepository.save(com);
        assertThat(newCompany).isNotNull();

        Company eCompany = companyRepository.findByCompanyName(newCompany.getCompanyName()).orElseThrow();
        assertThat(eCompany).isNotNull();
    }
}