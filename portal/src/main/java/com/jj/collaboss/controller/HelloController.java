package com.jj.collaboss.controller;

import com.jj.collaboss.error.AppException;
import com.jj.collaboss.error.ErrorCode;
import com.jj.collaboss.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/service/company")
@RequiredArgsConstructor
public class HelloController {

    private final MessageSource messages;

    private final CompanyService companyService;

    @GetMapping("/hello")
    public String hello() {
        return messages.getMessage("label.welcome", null, LocaleContextHolder.getLocale());
    }

    @GetMapping("/error")
    public String error() {
        throw new AppException(ErrorCode.GENERAL);
    }

}
