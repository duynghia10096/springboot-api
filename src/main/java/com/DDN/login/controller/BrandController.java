package com.DDN.login.controller;

import com.DDN.login.common.ApiResponse;
import com.DDN.login.dto.filter.BrandDTO;
import com.DDN.login.dto.filter.BrandImagesDTO1;
import com.DDN.login.exception.ApparelNotFoundException;
import com.DDN.login.model.categories.ProductBrandCategory;
import com.DDN.login.model.images.BrandImages;
import com.DDN.login.repository.dao.categories.ProductBrandCategoryRepository;
import com.DDN.login.repository.dao.images.BrandImagesRepository;
import com.DDN.login.security.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductBrandCategoryRepository brandCategoryRepository;

    @Autowired
    private BrandImagesRepository brandImagesRepository;

    @PostMapping("/addBrandCategory")
    public ResponseEntity<ApiResponse> addBrandCategory(@ModelAttribute BrandDTO brandDTO) {
        brandService.createBrandCategory(brandDTO);
        return new ResponseEntity<>(new ApiResponse(true, "Brand Category has been created!"), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/addBrandImages", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse> addBrandImages(@RequestHeader HttpHeaders headers, @ModelAttribute BrandImagesDTO1 brandImagesDTO1) {
        brandService.createBrandImages(brandImagesDTO1, brandImagesDTO1.getImages());
        return new ResponseEntity<>(new ApiResponse(true, "Brand Images has been created!"), HttpStatus.CREATED);
    }

    @GetMapping("/getAllBrandCategory")
    public ResponseEntity<List<ProductBrandCategory>> getAllBrandCategory() {
        List<ProductBrandCategory> brandCategories = brandService.listBrandCategory();
        
        return new ResponseEntity<>(brandCategories, HttpStatus.OK);
    }

    @GetMapping("/getAllBrandImages")
    public ResponseEntity<Map<String,Object>> getAllBrandImages(@RequestParam int page, @RequestParam int pagesize) {
        List<BrandImages> brandImages = new ArrayList<>();
        Pageable pagingSort = PageRequest.of(page, pagesize);
        Page<BrandImages> pageBrands;

        pageBrands = brandImagesRepository.findAll(pagingSort);
        brandImages = pageBrands.getContent();

        Map<String,Object> response = new HashMap<>();
        response.put("apparelCategories", brandImages);
        response.put("currentPage", pageBrands.getNumber());
        response.put("totalItems", pageBrands.getTotalElements());
        response.put("totalPages", pageBrands.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getBrandCategoryDetail/{id}")
    public ResponseEntity<BrandDTO> getCategoryDetail(@PathVariable int id) {
        ProductBrandCategory brandCategory = brandService.getBrandById(id);
        BrandDTO brandDTO = new BrandDTO(brandCategory);
        return new ResponseEntity<>(brandDTO, HttpStatus.OK);
    }

    @GetMapping("/getBrandImagesDetail/{id}")
    public ResponseEntity<BrandImages> getBrandImagesDetail(@PathVariable int id) {
        BrandImages brandImages = brandService.getBrandImagesById(id);
        return new ResponseEntity<>(brandImages, HttpStatus.OK);
    }

    @PutMapping("/updateCategory/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("id") int id, @RequestBody BrandDTO brandDTO) throws ApparelNotFoundException {
        brandService.updateBrandCategory(brandDTO);
        return new ResponseEntity<>(new ApiResponse(true, "You have updated Apparel Category"), HttpStatus.OK );
    }

    @PutMapping("/updateImages/{id}")
    public ResponseEntity<ApiResponse> updateImages(@PathVariable("id") int id, @RequestBody BrandImagesDTO1 brandImagesDTO1) {
        brandService.updateBrandImages(brandImagesDTO1);
        return new ResponseEntity<>(new ApiResponse(true, "You have updated Apparel Images"), HttpStatus.OK);
    }
}
