package com.inzent.sbp.controller;

import com.inzent.sbp.dto.CompanyDto;
import com.inzent.sbp.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/service/company")
@RequiredArgsConstructor
public class CompanyController {

    private final MessageSource messages;

    private final CompanyService companyService;

    // create
    @PostMapping("")
    public CompanyDto create(@RequestBody CompanyDto com){
        return companyService.save(com);
    }

    @GetMapping("/{id}")
    public CompanyDto findById(@PathVariable("id") String id){
        return companyService.findById(id);
    }

    @GetMapping("/name/{name}")
    public CompanyDto findByCompanyName(@PathVariable("name") String name){
        return companyService.findByCompanyName(name);
    }

}
