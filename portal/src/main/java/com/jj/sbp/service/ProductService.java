package com.jj.sbp.service;

import com.jj.sbp.domain.Product;
import com.jj.sbp.dto.ProductDto;
import com.jj.sbp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor    // inject dependency
@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public List<ProductDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto save(ProductDto productDto) {
        Product p = productRepository.save(productDto.toEntity());
        return new ProductDto(p);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

}
