package com.retail.management.controller;

import com.retail.management.entity.User;
import com.retail.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    // 1️⃣ GET ALL USERS
    @GetMapping
    public List<User> getAll(){
        return service.getAllUsers();
    }

    // 2️⃣ GET USER BY ID
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id){
        return service.getUserById(id);
    }

    // 3️⃣ CREATE USER
    @PostMapping
    public User add(@RequestBody User user){
        return service.addUser(user);
    }

    // 4️⃣ UPDATE USER
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user){
        return service.updateUser(id, user);
    }

    // 5️⃣ DELETE USER
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        service.deleteUser(id);
        return "User Deleted Successfully";
    }
}
