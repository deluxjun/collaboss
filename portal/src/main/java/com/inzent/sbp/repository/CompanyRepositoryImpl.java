package com.inzent.sbp.repository;

import com.inzent.sbp.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.Optional;

public class CompanyRepositoryImpl<T, ID> implements CustomRepository<T, ID> {

    @PersistenceContext
    EntityManager em;

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public Optional<T> findById(ID id) {
//        entityManager
        return Optional.empty();
    }

    @Override
    public <S extends T> S customizedSave(S s) {
        Company company = (Company) s;
        // custom here
        System.out.println("called : " + company.getModifiedDate());
        return (S) companyRepository.save(company);
    }
}
