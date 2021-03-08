package com.jj.sbp.controller;

import com.jj.sbp.dto.VerificationDto;
import com.jj.sbp.error.AppException;
import com.jj.sbp.error.ErrorCode;
import com.jj.sbp.service.EmailService;
import com.jj.sbp.service.VerificationService;
import com.jj.sbp.utils.Context;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/service/confirm")
@RequiredArgsConstructor
public class VerificationController {

    private final MessageSource messages;

    private final EmailService emailService;

    private final VerificationService verificationService;


    @PostMapping("/requestCode")
    public void sendVerificationCode(@RequestBody String id) {
        verificationService.sendConfirmation(id);
    }

    // check code
    @PostMapping("/checkCode")
    public void checkCode(@RequestBody VerificationDto code) {
        boolean confirmed = verificationService.checkIfConfirmedCode(code.getEmailTo(), code.getCode(), true);
        if (!confirmed)
            throw new AppException(ErrorCode.NOT_ACCEPTABLE, Context.getMessage("verification.InvalidCode"));
    }

}
