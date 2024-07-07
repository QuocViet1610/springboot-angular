package com.example.shopapp.Service.imp;

import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.model.Category;

import java.util.List;

public interface ICatoryService {

    Category createCategory(CategoryDTO category);

    Category getCategory(Long id);

    List<Category> getAllCateogry();

    Category updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(long id);

}
