package com.jj.collaboss.controller;

import com.jj.collaboss.dto.CompanyDto;
import com.jj.collaboss.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CompanyController {

    private final MessageSource messages;

    private final CompanyService companyService;

    // create
    @PostMapping("/service/company")
    public void create(@RequestBody CompanyDto com){
        companyService.save(com, true);
    }

    // admin services

    @PostMapping("/sservice/company")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createWithNoCode(@RequestBody CompanyDto com){
        companyService.save(com, false);
    }

    @GetMapping("/sservice/company/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompanyDto findById(@PathVariable("id") String id){
        return companyService.findById(id);
    }

    @GetMapping("/sservice/company/name/{name}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompanyDto findByCompanyName(@PathVariable("name") String name){
        return companyService.findByCompanyName(name);
    }

    @GetMapping("/sservice/company/deleteId/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable("id") String id){
        companyService.delete(id);
    }

    @GetMapping("/sservice/companies")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<CompanyDto> findAll(){
        return companyService.findAll();
    }
}
