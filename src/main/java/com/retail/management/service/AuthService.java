package com.retail.management.service;

import com.retail.management.dto.LoginDTO;
import com.retail.management.dto.LoginResponseDTO;
import com.retail.management.dto.RegisterDTO;
import com.retail.management.entity.User;
import com.retail.management.repository.UserRepository;
import com.retail.management.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    // REGISTER
    public String register(RegisterDTO dto) {

        if (repo.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        User u = new User();
        u.setName(dto.getName());
        u.setEmail(dto.getEmail());
        u.setPassword(encoder.encode(dto.getPassword()));
        u.setRole(dto.getRole());

        repo.save(u);
        return "User Registered Successfully";
    }

    // LOGIN
    public LoginResponseDTO login(LoginDTO dto) {

        User user = repo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong Password");
        }

        String token = jwt.generateToken(user.getEmail());

        return new LoginResponseDTO(
                token,
                user.getRole().name(),
                user.getEmail()
        );
    }
}
