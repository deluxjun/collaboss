package com.jj.collaboss.dto;

import com.jj.collaboss.domain.Verification;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerificationDto {
    private String emailTo;
    private String emailFrom;
    private Integer type = 0;
    private String code;
//    private boolean confirmed;

    // to dto
    public VerificationDto(Verification verification) {
        this.emailTo = verification.getEmailTo();
        this.emailFrom = verification.getEmailFrom();
        this.code = verification.getCode();
        this.type = verification.getType();
//        this.confirmed = verification.isConfirmed();
    }

    @Builder
    public VerificationDto(String emailTo, String code) {
        this.emailTo = emailTo;
        this.code = code;
    }

    // to entity
    public Verification toEntity() {
        return Verification.builder()
                .emailTo(emailTo)
                .type(type)
                .code(code)
                .build();
    }
}
