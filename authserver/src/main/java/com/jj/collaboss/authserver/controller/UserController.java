package com.jj.collaboss.authserver.controller;

import com.jj.collaboss.authserver.entity.User;
import com.jj.collaboss.authserver.repository.UserRepository;
import com.jj.collaboss.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/saveUser")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

}
