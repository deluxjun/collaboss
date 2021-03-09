package com.jj.collaboss.repository;

import com.jj.collaboss.domain.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    @Query("select p from Verification p where p.emailTo = ?1 and p.type = ?2 and p.code = ?3")
    Optional<Verification> getByToAndType(String to, int type, String code);

    @Query("select p from Verification p where p.emailTo = ?1 and p.type = ?2")
    Optional<Verification> getByToAndType(String to, int type);
}
