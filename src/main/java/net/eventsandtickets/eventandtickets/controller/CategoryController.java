package net.eventsandtickets.eventandtickets.controller;

import net.eventsandtickets.eventandtickets.model.Category;
import net.eventsandtickets.eventandtickets.repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository repo;

    public CategoryController(CategoryRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Category save(@RequestBody Category category) {
        return repo.save(category);
    }

    @GetMapping
    public List<Category> getAll() {
        return repo.findAll();
    }
}