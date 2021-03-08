package com.jj.sbp.repository;

import com.jj.sbp.domain.Company;
import com.jj.sbp.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Company> findByName(String name);

}
