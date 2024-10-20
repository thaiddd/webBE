package com.store.backend.Service;

import com.store.backend.Entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    boolean deleteCategoryById(Long Id);
    boolean existCategory(Category category);
    Category saveCategory(Category category, MultipartFile image) throws IOException;
    Category updateCategory(Category category, Long id,MultipartFile image) throws IOException;
    Category searchCategoryByName(String name);
    Category searchCategoryById(Long id);

}
