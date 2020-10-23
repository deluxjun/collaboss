package com.inzent.sbp.dto;

import com.inzent.sbp.domain.Company;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyDto {
    private String id;
    private String companyName;

    // to dto
    public CompanyDto(Company company) {
        this.id = company.getId().toString();
        this.companyName = company.getCompanyName();
    }

    @Builder
    public CompanyDto(String id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    // to entity
    public Company toEntity() {
        return Company.builder()
                .companyName(companyName)
                .build();
    }
}
