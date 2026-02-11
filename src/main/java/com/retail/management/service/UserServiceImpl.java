package com.retail.management.service;

import com.retail.management.entity.User;
import com.retail.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    @Override
    public User addUser(User user) {
        return repo.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existing = getUserById(id);
        existing.setEmail(user.getEmail());
        existing.setPassword(user.getPassword());
        existing.setRole(user.getRole());
        return repo.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        repo.deleteById(id);
    }
}
