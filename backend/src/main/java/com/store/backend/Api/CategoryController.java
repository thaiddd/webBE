package com.store.backend.Api;

import com.store.backend.Config.ResponseDataConfiguration;
import com.store.backend.DTO.CategoryDto;
import com.store.backend.Entity.Category;
import com.store.backend.Service.CategoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategory();
    }

    @PostMapping()
    public ResponseEntity<?> save(@ModelAttribute CategoryDto categoryDto) {
        try {
            Category category = new Category();
            category.setCategoryName(categoryDto.getCategoryName());
            category.setDescription(categoryDto.getDescription());
            categoryService.saveCategory(category, categoryDto.getImage());
            return ResponseEntity.ok().body(null);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@ModelAttribute CategoryDto categoryDto, @PathVariable Long id) {
        try {
            Category category = categoryService.searchCategoryById(id);
            category.setCategoryName(categoryDto.getCategoryName());
            category.setDescription(categoryDto.getDescription());
            categoryService.updateCategory(category, id,categoryDto.getImage());
            return ResponseEntity.ok().body(null);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        return ResponseDataConfiguration.success(categoryService.deleteCategoryById(id));
    }
}
