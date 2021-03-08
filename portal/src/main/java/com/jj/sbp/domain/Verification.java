package com.jj.sbp.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Verification",
        uniqueConstraints={
                @UniqueConstraint(name="verification_key1", columnNames = {"email_to", "type"})
        }
)
@Data
@NoArgsConstructor
public class Verification extends BaseTimeEntity {
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @Id long id;

    @NotNull
    @Column(name="email_to")
    private String emailTo;

    private String emailFrom;

    @NotNull
    private Integer type = 0;

    private String code;

//    @NotNull
//    private boolean confirmed;

    @Builder
    public Verification(String emailTo, int type, String code) {
        this.emailTo = emailTo;
        this.type = type;
        this.code = code;
    }

    public static enum VerificationType {
        CONFIRMATION("template-code.ftl"),
        INVITATION("template-code.ftl"),    //TODO: change
        CHANGE_PASSWORD("template-code.ftl")
        ;

        private String mailTemplate;
        VerificationType(String mailTemplate) {
            this.mailTemplate = mailTemplate;
        }

        public String getMailTemplate() {
            return this.mailTemplate;
        }


    }
}
