package com.jj.collaboss.repository;

import java.util.Optional;

public interface CustomRepository<T, ID> {
    Optional<T> findById(ID id);
    <S extends T> S customizedSave(S s);
}
