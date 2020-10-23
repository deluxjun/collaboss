package com.inzent.sbp.controller;

import com.inzent.sbp.dto.CompanyDto;
import com.inzent.sbp.error.AppException;
import com.inzent.sbp.error.ErrorCode;
import com.inzent.sbp.service.CompanyService;
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
