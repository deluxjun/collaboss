package com.inzent.sbp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomRepository<T, ID> {
    Optional<T> findById(ID id);
    <S extends T> S customizedSave(S s);
}
