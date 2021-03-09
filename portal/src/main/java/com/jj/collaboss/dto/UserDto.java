package com.jj.collaboss.dto;

import java.util.List;

import com.jj.collaboss.domain.Role;
import lombok.Data;

@Data
public class UserDto {

  private Integer id;
  private String username;
  private String email;
  private String password;
  List<Role> roles;

}
