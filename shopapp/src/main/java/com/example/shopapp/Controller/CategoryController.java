package com.example.shopapp.Controller;

import com.example.shopapp.Service.CategoryService;
import com.example.shopapp.dtos.CategoryDTO;
import com.example.shopapp.model.Category;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/category")
//@Validated

public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("")
    public ResponseEntity<?> getAllCategory(
            @RequestParam("Page") int page,
            @RequestParam("limit") int limit
    ){
        List<Category> categories=  categoryService.getAllCateogry();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> insertCategory(@RequestBody CategoryDTO categoryDTO, BindingResult result){
            if(result.hasErrors()){
                List<String> ErroMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                String errorMessage = String.join(", ", ErroMessage);

                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
            }
        categoryService.createCategory(categoryDTO);
            return new ResponseEntity<>(String.format("save successfully ", categoryDTO), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory( @PathVariable Long id,@Valid @RequestBody CategoryDTO categoryDTO){
        categoryService.updateCategory(id, categoryDTO);
        return new ResponseEntity<>("  update successfully "+ categoryDTO.getName(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("detelet successfuly", HttpStatus.OK);
    }
}
