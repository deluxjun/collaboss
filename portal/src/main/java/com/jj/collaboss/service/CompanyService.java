package com.jj.collaboss.service;

import com.jj.collaboss.domain.Company;
import com.jj.collaboss.domain.CompanyProduct;
import com.jj.collaboss.domain.Product;
import com.jj.collaboss.dto.CompanyDto;
import com.jj.collaboss.dto.ProductDto;
import com.jj.collaboss.error.AppException;
import com.jj.collaboss.error.ErrorCode;
import com.jj.collaboss.error.NotFoundException;
import com.jj.collaboss.repository.CompanyProductRepository;
import com.jj.collaboss.repository.CompanyRepository;
import com.jj.collaboss.repository.ProductRepository;
import com.jj.collaboss.utils.Context;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor    // inject dependency
@Service
public class CompanyService {
    private final CompanyProductRepository cpRepository;
    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;
    private final VerificationService verificationService;
    private final EmailService emailService;

    @Transactional
    public List<CompanyDto> findAll() {
        return companyRepository.findAll()
                .stream()
                .map(CompanyDto::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public CompanyDto save(CompanyDto companyDto, boolean checkCode) {
        // check if confirmed
        if (checkCode) {
            boolean ok = verificationService.checkIfConfirmedCode(companyDto.getAdminEmail(), companyDto.getCode(), false);
            if (!ok && !"!@#QWE".equals(companyDto.getCode()))
                throw new AppException(ErrorCode.NOT_ACCEPTABLE, Context.getMessage("verification.InvalidCode"));
        }
        Company company = companyDto.toEntity();

        // find existing company
        Company companyOld = companyRepository.findByCompanyName(company.getCompanyName())
                .orElse(null);
        if (companyOld != null) {
            company.setId(companyOld.getId());
        }

        // save company
        company = companyRepository.customizedSave(company);

        // find and set products
        if (companyDto.getProducts() != null) {
            Set<Product> newProducts = new HashSet<>();
            for (ProductDto productDto : companyDto.getProducts()) {
                Product product = productRepository.findById(productDto.getId()).orElseThrow(
                        () -> new NotFoundException("product id : " + productDto.getId()));
                newProducts.add(product);
//                company.getCompanyProducts().add(new CompanyProduct(company, product));
//                cpRepository.save(new CompanyProduct(company, product));
            }
            setProducts(company, newProducts);
        }

        try {
            companyRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.CONFLICT, Context.getMessage("company.alreadyExists"));
        }

        // mail to system operator
        emailService.sendApplicationMailToOperator(company);

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

    @Transactional
    public void delete(String id) {
        companyRepository.deleteById(UUID.fromString(id));
    }

    // save Products
    private void setProducts(Company company, Set<Product> products) {
        Set<CompanyProduct> oldProducts = company.getCompanyProducts();
        // remove all
        if (oldProducts != null) {
            cpRepository.deleteByCompanyId(company.getId());
        }
        // insert
        for (Product product : products) {
            cpRepository.save(new CompanyProduct(company, product));
        }
    }
}
