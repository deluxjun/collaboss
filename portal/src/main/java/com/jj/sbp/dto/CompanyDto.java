package com.jj.sbp.dto;

import com.jj.sbp.domain.Company;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CompanyDto {
    private String id;
    private String companyName;
    private String adminName;
    private String tel;
    private Integer usersLimit;
    private String memo;

    // manager email
    private String adminEmail;

    private Set<ProductDto> products;

    private String code;    // for verification

    // to dto
    public CompanyDto(Company company) {
        this.id = company.getId().toString();
        this.companyName = company.getCompanyName();
        this.adminName = company.getAdminName();
        this.tel = company.getTel();
        this.usersLimit = company.getUsersLimit();
        this.adminEmail = company.getAdminEmail();
        this.memo = company.getMemo();
        if (company.getCompanyProducts() != null)
            this.products = company.getCompanyProducts().stream()
                    .map(d -> new ProductDto(d.getProduct()))
                    .collect(Collectors.toSet());
    }

    @Builder
    public CompanyDto(String id, String companyName, String tel, Integer usersLimit, String adminEmail, String adminName, String memo, String code,
                      Set<ProductDto> products) {
        this.id = id;
        this.companyName = companyName;
        this.adminName = adminName;
        this.tel = tel;
        this.usersLimit = usersLimit;
        this.adminEmail = adminEmail;
        this.memo = memo;
        this.code = code;
        this.products = products;
    }

    // to entity
    public Company toEntity() {
        return Company.builder()
                .companyName(companyName)
                .tel(tel)
                .usersLimit(usersLimit)
                .adminEmail(adminEmail)
                .adminName(adminName)
                .memo(memo)
                .build();
    }
}
