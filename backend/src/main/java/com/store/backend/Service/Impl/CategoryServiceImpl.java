package com.store.backend.Service.Impl;

import com.store.backend.Entity.Category;
import com.store.backend.Repository.CategoryRepository;
import com.store.backend.Service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean deleteCategoryById(Long id) {
        try {
            categoryRepository.deleteById(id);
        }catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean existCategory(Category category) {
        return false;
    }

    @Override
    public Category saveCategory(Category category, MultipartFile image) throws IOException {
        Category cgr = categoryRepository.save(category);

        return categoryRepository.save(cgr);
    }

    @Override
    public Category updateCategory(Category category, Long id,MultipartFile image) throws IOException {
        Category cgr = categoryRepository.findById(id).get();
        cgr.setCategoryName(category.getCategoryName());
        cgr.setDescription(category.getDescription());


        return categoryRepository.save(cgr);
    }

    @Override
    public Category searchCategoryByName(String name) {
        return null;
    }

    @Override
    public Category searchCategoryById(Long id) {
        return categoryRepository.searchCategoriById(id);
    }
}
