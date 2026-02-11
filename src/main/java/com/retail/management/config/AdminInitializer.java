package com.retail.management.config;

import com.retail.management.entity.Role;
import com.retail.management.entity.User;
import com.retail.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    @Bean
    CommandLineRunner initAdmin() {
        return args -> {

            if (repo.findByEmail("admin@gmail.com").isEmpty()) {

                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(encoder.encode("1234"));
                admin.setRole(Role.ADMIN);

                repo.save(admin);

                System.out.println("DEFAULT ADMIN CREATED");
            }
        };
    }
}
