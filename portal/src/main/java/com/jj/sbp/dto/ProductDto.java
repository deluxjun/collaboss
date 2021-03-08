package com.jj.sbp.dto;

import com.jj.sbp.domain.Product;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String name;

    // to dto
    public ProductDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
    }

    @Builder
    public ProductDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // to entity
    public Product toEntity() {
        return Product.builder()
                .name(name)
                .build();
    }
}
