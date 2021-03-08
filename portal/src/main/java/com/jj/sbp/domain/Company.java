package com.jj.sbp.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Company")
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

    @Column(unique=true, nullable = false)
    @NotNull    // requires 'spring-boot-starter-validation' dependency
    private String companyName;

    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Column(nullable = false)
    @NotNull
    private String adminName;

    private String memo;

    private String tel;

    @NotNull
    private Integer active = 0;
    private String serviceUrl;

    @Lob
    private Byte[] logo;

    @Min(value = 1, message = "Users should not be less than 1")
//    @Max(value = 65, message = "Age should not be greater than 65")
    private Integer usersLimit;

    @Column(unique = true, nullable = false)
    private String adminEmail;

    private LocalDateTime trialEndDate;

    @NotNull
    private Integer deleted = 0;

    @OneToMany(mappedBy = "company")
    private Set<CompanyProduct> companyProducts = new HashSet<>();

    @Builder
    public Company(String companyName, String tel, Integer usersLimit, String adminEmail, String adminName, String memo) {
        this.companyName = companyName;
        this.adminName = adminName;
        this.tel = tel;
        this.memo = memo;
        this.usersLimit = usersLimit;
        this.adminEmail = adminEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;

        return Objects.equals(this.companyName, company.getCompanyName());
    }

    @Override
    public int hashCode() {
		return Objects.hash(companyName);
    }

}
