package com.jj.collaboss.authserver.repository;

import com.jj.collaboss.authserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
