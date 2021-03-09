package com.jj.collaboss.repository;

import com.jj.collaboss.domain.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Slf4j
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
        S saved = (S) companyRepository.save(company);
        log.debug("[customizedSave] modified : " + company.getModifiedDate());
        return saved;
    }
}
