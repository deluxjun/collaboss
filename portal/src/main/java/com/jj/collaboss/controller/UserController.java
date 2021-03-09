package com.jj.collaboss.controller;

import javax.servlet.http.HttpServletRequest;

import com.jj.collaboss.domain.User;
import com.jj.collaboss.dto.UserDto;
import com.jj.collaboss.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiParam;

@RestController
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping("/service/users/signin")
  public String login(//
      @ApiParam("Username") @RequestParam String username, //
      @ApiParam("Password") @RequestParam String password) {
    return userService.signin(username, password);
  }

  // admin services
  // TODO: open to clients later

  @PostMapping("/sservice/users/signup")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String signup(@ApiParam("Signup User") @RequestBody UserDto user) {
    return userService.signup(modelMapper.map(user, User.class));
  }

  @DeleteMapping(value = "/service/admin/users/{username}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String delete(@ApiParam("Username") @PathVariable String username) {
    userService.delete(username);
    return username;
  }

  @GetMapping(value = "/sservice/users/{username}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public UserDto search(@ApiParam("Username") @PathVariable String username) {
    return modelMapper.map(userService.search(username), UserDto.class);
  }

  @GetMapping(value = "/sservice/users/me")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
  public UserDto whoami(HttpServletRequest req) {
    return modelMapper.map(userService.whoami(req), UserDto.class);
  }

  @GetMapping("/sservice/users/refresh")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
  public String refresh(HttpServletRequest req) {
    return userService.refresh(req.getRemoteUser());
  }

}
