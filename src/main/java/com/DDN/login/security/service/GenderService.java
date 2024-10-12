package com.DDN.login.security.service;

import com.DDN.login.dto.filter.GenderCategoryDTO;
import com.DDN.login.exception.GenderNotFoundException;
import com.DDN.login.model.categories.GenderCategory;
import com.DDN.login.repository.dao.categories.GenderCategoryRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenderService {
    @Autowired
    private GenderCategoryRepository genderCategoryRepository;

    public void createGenderCategory(GenderCategoryDTO genderCategoryDTO) throws GenderNotFoundException {
        if(genderCategoryRepository.findByType(genderCategoryDTO.getType()) != null) {
            throw new GenderNotFoundException("Gender Category existed!");
        }

        GenderCategory genderCategory = new GenderCategory();
        genderCategory.setType(genderCategoryDTO.getType());
        genderCategoryRepository.save(genderCategory);
    }

    public List<GenderCategory> getAllGenderCategories() {
        return genderCategoryRepository.findAll();
    }

    
}
