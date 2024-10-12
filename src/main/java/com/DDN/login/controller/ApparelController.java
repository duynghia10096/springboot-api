package com.DDN.login.controller;

import com.DDN.login.common.ApiResponse;
import com.DDN.login.dto.filter.ApparelCategoryDTO;

import com.DDN.login.dto.filter.ApparelImagesDTO1;
import com.DDN.login.exception.ApparelNotFoundException;
import com.DDN.login.model.categories.ApparelCategory;
import com.DDN.login.model.images.ApparelImages;
import com.DDN.login.repository.dao.categories.ApparelCategoryRepository;
import com.DDN.login.repository.dao.images.ApparelImagesRepository;
import com.DDN.login.security.service.ApparelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/apparel")
public class ApparelController {
    @Autowired
    private ApparelService apparelService;

    @Autowired
    private ApparelCategoryRepository apparelCategoryRepository;

    @Autowired
    ApparelImagesRepository apparelImagesRepository;


    @RequestMapping(value = "/addApparelCategory", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse> addApparelCategory(@ModelAttribute ApparelCategoryDTO apparelCategoryDTO){
        apparelService.createApparelCategory(apparelCategoryDTO);
        return new ResponseEntity<>(new ApiResponse(true, "Apparel Category has been created!"), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/addApparelImages", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse> addApparelImages(@RequestHeader HttpHeaders headers, @ModelAttribute ApparelImagesDTO1 apparelImagesDTO1) {
        apparelService.createApparelImages(apparelImagesDTO1, apparelImagesDTO1.getImages());
        return new ResponseEntity<>(new ApiResponse(true, "Apparel Iamges has been created!"), HttpStatus.CREATED);
    }

    @GetMapping("/getAllApparelCategory")
    public ResponseEntity<List<ApparelCategory>> getAllApparelCategory() {
        List<ApparelCategory> apparelCategories = apparelService.listApparelCategory();
       
        return new ResponseEntity<>(apparelCategories, HttpStatus.OK);
    }

    @GetMapping("/getAllApparelImages")
    public ResponseEntity<Map<String, Object>> getAllApparelImages(@RequestParam int page, @RequestParam int pagesize) {
        List<ApparelImages> apparelImages = new ArrayList<>();
        Pageable pagingSort = PageRequest.of(page,pagesize);
        Page<ApparelImages> pageImages;

        pageImages = apparelImagesRepository.findAll(pagingSort);
        apparelImages = pageImages.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("apparelImages", apparelImages);
        response.put("currentPage", pageImages.getNumber());
        response.put("totalItems", pageImages.getTotalElements());
        response.put("totalPages", pageImages.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getApparelCategoryDetail/{id}")
    public ResponseEntity<ApparelCategoryDTO> getCategoryDetail(@PathVariable int id) {
        ApparelCategory apparelCategory = apparelService.getApparelCategoryById(id);
        ApparelCategoryDTO apparelCategoryDTO = new ApparelCategoryDTO(apparelCategory);

        return new ResponseEntity<>(apparelCategoryDTO, HttpStatus.OK);
    }

    @GetMapping("/getApparelImagesDetail/{id}")
    public ResponseEntity<ApparelImages> getImageDetail(@PathVariable int id) {
        ApparelImages apparelImages = apparelService.getApparelImagesById(id);
        return new ResponseEntity<>(apparelImages, HttpStatus.OK);
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("id") int id, @RequestBody ApparelCategoryDTO apparelCategoryDTO) throws ApparelNotFoundException {
        apparelService.updateApparelCategory(apparelCategoryDTO);
        return new ResponseEntity<>(new ApiResponse(true, "You have updated Apparel Category"), HttpStatus.OK );
    }

    @PutMapping("/updateImages/{id}")
    public ResponseEntity<ApiResponse> updateImages(@PathVariable("id") int id, @RequestBody ApparelImagesDTO1 apparelImagesDTO1) {
        apparelService.updateApparelImages(apparelImagesDTO1);
        return new ResponseEntity<>(new ApiResponse(true, "You have updated Apparel Images"), HttpStatus.OK);
    }

}
