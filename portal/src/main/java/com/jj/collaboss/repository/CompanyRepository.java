package com.jj.collaboss.repository;

import com.jj.collaboss.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID>, CustomRepository<Company, UUID> {
    Optional<Company> findByCompanyName(String companyName);

}
