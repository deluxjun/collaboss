package com.jj.sbp.controller;

import com.jj.sbp.dto.ProductDto;
import com.jj.sbp.service.CompanyService;
import com.jj.sbp.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final MessageSource messages;

    private final CompanyService companyService;
    private final ProductService productService;

    // create
    @PostMapping("/sservice/product")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductDto create(@RequestBody ProductDto com){
        return productService.save(com);
    }

    @GetMapping("/service/products")
    public List<ProductDto> findAll(){
        return productService.findAll();
    }

//    @GetMapping("/name/{name}")
//    public ProductDto findByName(@PathVariable("name") String name){
//        return productService.findByName(name);
//    }

    // create
    @GetMapping("/sservice/product/deleteId/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable("id") String id){
        productService.delete(Long.parseLong(id));
    }

}
