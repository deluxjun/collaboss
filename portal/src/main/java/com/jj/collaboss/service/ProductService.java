package com.jj.collaboss.service;

import com.jj.collaboss.domain.Product;
import com.jj.collaboss.dto.ProductDto;
import com.jj.collaboss.repository.ProductRepository;
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
