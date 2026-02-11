package com.retail.management.controller;

import com.retail.management.dto.LoginDTO;
import com.retail.management.dto.LoginResponseDTO;
import com.retail.management.dto.RegisterDTO;
import com.retail.management.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public String register(@RequestBody RegisterDTO dto){
        return service.register(dto);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO dto){
        return service.login(dto);
    }
}
