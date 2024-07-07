package com.example.shopapp.Service;

import com.example.shopapp.Repository.CategoryRepository;
import com.example.shopapp.Service.imp.ICatoryService;
import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICatoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Category createCategory(CategoryDTO category) {
        Category categoryData =Category
                .builder()
                .name(category.getName())
                .build();
        return categoryRepository.save(categoryData);
    }


    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<Category> getAllCateogry() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existCategory = getCategory(id);
        existCategory.setName(categoryDTO.getName());
        categoryRepository.save(existCategory);
        return existCategory;
    }

    @Override
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }
}
