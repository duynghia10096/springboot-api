package com.DDN.login.controller;

import com.DDN.login.common.ApiResponse;
import com.DDN.login.model.Category;
import com.DDN.login.security.service.CategoryService;
import com.DDN.login.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> body = categoryService.listCategories();
        return new ResponseEntity<List<Category>>(body, HttpStatus.OK);
    }

    @PostMapping("/create")

    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category category){
        if(Helper.notNull(categoryService.readCategory(category.getCategoryName()))){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category already exists"), HttpStatus.CONFLICT);
        }
        categoryService.createCategory(category);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "created the category"), HttpStatus.CREATED);
    }

    @PostMapping("/update/{categoryID}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryID") Integer categoryID, @Valid @RequestBody Category category){
        //Check to see if the category exists
        if(Helper.notNull(categoryService.readCategory(categoryID))){
            categoryService.updateCategory(categoryID,category);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "updated the category"), HttpStatus.OK);
        }
        //if the category doesn't exist then return  a response of unsuccessful
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category doesn't exist"), HttpStatus.NOT_FOUND);
    }
}
