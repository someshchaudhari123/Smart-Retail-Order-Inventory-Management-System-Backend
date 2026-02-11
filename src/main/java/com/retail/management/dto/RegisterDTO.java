package com.retail.management.dto;

import com.retail.management.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    private String name;
    private String email;
    private String password;
    private Role role;
}
