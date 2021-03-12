package com.jj.collaboss.authserver.service;

import com.jj.collaboss.authserver.entity.User;
import com.jj.collaboss.authserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
