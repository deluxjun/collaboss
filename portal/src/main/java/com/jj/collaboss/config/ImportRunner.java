package com.jj.collaboss.config;

import com.jj.collaboss.domain.Role;
import com.jj.collaboss.domain.User;
import com.jj.collaboss.dto.CompanyDto;
import com.jj.collaboss.dto.ProductDto;
import com.jj.collaboss.service.CompanyService;
import com.jj.collaboss.service.ProductService;
import com.jj.collaboss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// initialization data for test
@Profile("localdev")
@Component
public class ImportRunner implements ApplicationRunner {

    @Autowired
    CompanyService companyService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // save products
        productService.save(ProductDto.builder().name("Smartwork 52").build());
        productService.save(ProductDto.builder().name("XEDM Document Manager").build());

        Set<ProductDto> products = Stream.of(ProductDto.builder().id(1L).build(),ProductDto.builder().id(2L).build()).collect(Collectors.toSet());

        companyService.save(CompanyDto.builder()
                .companyName("jj")
                .adminName("junsoo")
                .memo("This is a memo")
                .tel("025580203")
                .usersLimit(200)
                .adminEmail("jsbae@jj.com")
                .products(products)
                .build(), false);

        userService.signup(User.builder()
                .username("admin")
                .email("smartwork52@jj.com")
                .password("jj")
                .roles(Arrays.asList(Role.ROLE_ADMIN))
                .build()
        );
    }
}
