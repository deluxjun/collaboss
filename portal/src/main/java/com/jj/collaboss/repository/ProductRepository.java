package com.jj.collaboss.repository;

import com.jj.collaboss.domain.Company;
import com.jj.collaboss.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Company> findByName(String name);

}
