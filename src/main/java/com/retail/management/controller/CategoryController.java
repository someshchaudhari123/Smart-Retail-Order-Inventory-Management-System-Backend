package com.retail.management.controller;

import com.retail.management.entity.Category;
import com.retail.management.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository repo;

    // ADD CATEGORY
    @PostMapping
    public Category add(@RequestBody Category c){
        return repo.save(c);
    }

    // GET ALL
    @GetMapping
    public List<Category> getAll(){
        return repo.findAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id){
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category Not Found"));
    }

    // UPDATE
    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category c){
        Category existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category Not Found"));

        existing.setName(c.getName());
        return repo.save(existing);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        Category category = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category Not Found"));

        repo.delete(category);
        return "Category Deleted Successfully";
    }
}
