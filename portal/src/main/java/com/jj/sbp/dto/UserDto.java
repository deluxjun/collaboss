package com.jj.sbp.dto;

import java.util.List;

import com.jj.sbp.domain.Role;
import lombok.Data;

@Data
public class UserDto {

  private Integer id;
  private String username;
  private String email;
  private String password;
  List<Role> roles;

}
