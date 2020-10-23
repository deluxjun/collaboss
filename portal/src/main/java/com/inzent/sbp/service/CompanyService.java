package com.inzent.sbp.service;

import com.inzent.sbp.domain.Company;
import com.inzent.sbp.dto.CompanyDto;
import com.inzent.sbp.error.NotFoundException;
import com.inzent.sbp.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@RequiredArgsConstructor    // inject dependency
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Transactional
    public CompanyDto save(CompanyDto companyDto) {
        Company company = companyRepository.customizedSave(companyDto.toEntity());
        return new CompanyDto(company);
    }

    @Transactional
    public CompanyDto findByCompanyName(String companyName) {
        Company company = companyRepository.findByCompanyName(companyName)
                .orElseThrow(() -> new NotFoundException(companyName));
        return new CompanyDto(company);
    }

    @Transactional
    public CompanyDto findById(String id) {
        Company company = companyRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(id));
        return new CompanyDto(company);
    }
}
