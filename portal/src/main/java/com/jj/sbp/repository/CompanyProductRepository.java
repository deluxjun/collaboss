package com.jj.sbp.repository;

import com.jj.sbp.domain.CompanyProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CompanyProductRepository extends JpaRepository<CompanyProduct, Long> {
    @Modifying
    @Query("delete from CompanyProduct p where p.company.id = ?1")
    void deleteByCompanyId(UUID companyId);

}
