package com.jj.collaboss.company;

import com.jj.collaboss.domain.Company;
import com.jj.collaboss.error.AppException;
import com.jj.collaboss.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest
class CompanyRepositoryTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CompanyRepository companyRepository;

    @Test
    public void di() throws Exception {
        Company com = new Company();
        com.setCompanyName("jj");
        Company newCompany = companyRepository.save(com);
        assertThat(newCompany).isNotNull();

        Company eCompany = companyRepository.findByCompanyName(newCompany.getCompanyName()).orElseThrow(() -> new AppException());
        assertThat(eCompany).isNotNull();
    }
}