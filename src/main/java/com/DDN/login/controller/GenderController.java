package com.DDN.login.controller;

import com.DDN.login.common.ApiResponse;
import com.DDN.login.dto.filter.GenderCategoryDTO;
import com.DDN.login.model.categories.GenderCategory;
import com.DDN.login.security.service.GenderService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/gender")
public class GenderController {
    @Autowired
    private GenderService genderService;

    @PostMapping("/addGender")
    public ResponseEntity<ApiResponse> addGenderCategory(@ModelAttribute GenderCategoryDTO genderCategoryDTO) {
        genderService.createGenderCategory(genderCategoryDTO);
        return new ResponseEntity<>(new ApiResponse(true, "Gender Category has been created"), HttpStatus.CREATED);
    }

    @GetMapping("/getAllGenders")
    public ResponseEntity<List<GenderCategory>> getAllGenderCategories() {
        List<GenderCategory> genderCategories = genderService.getAllGenderCategories();
        return new ResponseEntity<>(genderCategories, HttpStatus.OK);
    }
}
