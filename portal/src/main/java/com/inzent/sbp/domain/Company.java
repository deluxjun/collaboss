package com.inzent.sbp.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Company extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotNull    // requires 'spring-boot-starter-validation' dependency
    private String companyName;

    private String tel;
    @NotNull
    private Integer active = 0;
    private String serviceUrl;

    @Lob
    private Byte[] logo;
    private Integer usersLimit;
    private LocalDateTime trialEndDate;
    @NotNull
    private Integer deleted = 0;

    @Builder
    public Company(String companyName) {
        this.companyName = companyName;
    }
}
