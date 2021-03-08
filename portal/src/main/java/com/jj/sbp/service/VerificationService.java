package com.jj.sbp.service;

import com.jj.sbp.domain.Verification;
import com.jj.sbp.repository.VerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Random;

@RequiredArgsConstructor    // inject dependency
@Service
public class VerificationService {

    private final VerificationRepository verificationRepository;
    private final EmailService emailService;

    @Autowired
    @Qualifier("emailMessages")
    private ResourceBundleMessageSource emailMessageSource;

    @Value("${verification.code.timeout:180}")
    private int verificationTimeout;

    // send code&mail
    @Transactional
    public void sendConfirmation(String id) {
        String code = createCode(id);

        // mail to
        emailService.sendVerificationCode(id, code);

    }

    // verify
    public boolean checkIfConfirmedCode(String id, String code, boolean checkTime) {
        Verification value =  verificationRepository.getByToAndType(id, Verification.VerificationType.CONFIRMATION.ordinal(), code)
                .orElse(null);
        if (value != null) {
            // check timeout
            if(checkTime && value.getModifiedDate().isBefore(LocalDateTime.now().minusSeconds(verificationTimeout)))
                return false;
            return true;
        }
        return false;
    }

    // create code
    public String createCode(String id) {
        String code = getRandomNumberString();

        Verification verification =  verificationRepository.getByToAndType(id, Verification.VerificationType.CONFIRMATION.ordinal())
                .orElse(null);
        if (verification == null) {
            verification = Verification.builder()
                    .emailTo(id)
                    .type(Verification.VerificationType.CONFIRMATION.ordinal())
                    .code(code)
                    .build();
        } else {
            verification.setCode(code);
        }
        // save to table
        verificationRepository.saveAndFlush(verification);

        return code;
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
