package com.DDN.login.security.service;

import com.DDN.login.dto.filter.BrandDTO;
import com.DDN.login.dto.filter.BrandImagesDTO1;
import com.DDN.login.exception.BrandNotFoundException;
import com.DDN.login.model.categories.ProductBrandCategory;
import com.DDN.login.model.images.BrandImages;
import com.DDN.login.repository.dao.categories.ProductBrandCategoryRepository;
import com.DDN.login.repository.dao.images.BrandImagesRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class BrandService {
    @Autowired
    private ProductBrandCategoryRepository productBrandCategoryRepository;
    @Autowired
    private BrandImagesRepository brandImagesRepository;

    @Autowired
    private Cloudinary cloudinary;

    public List<ProductBrandCategory> listBrandCategory() {
        return productBrandCategoryRepository.findAll();
    }

    public ProductBrandCategory getBrandById(Integer brandId) throws BrandNotFoundException {
        Optional<ProductBrandCategory> brandCategory = productBrandCategoryRepository.findById(brandId);
        if(brandCategory.isPresent()) {
            return brandCategory.get();
        }
        throw new BrandNotFoundException("Brand not Found");
    }

    public void updateBrandCategory(BrandDTO brandDTO) throws BrandNotFoundException {
        ProductBrandCategory brandCategory = productBrandCategoryRepository.getOne(brandDTO.getId());
        brandCategory.setType(brandDTO.getType());
        brandCategory.setUpdatedDate(new Date());
        productBrandCategoryRepository.save(brandCategory);
    }

    public void updateBrandImages(BrandImagesDTO1 brandImagesDTO1) throws BrandNotFoundException {
        BrandImages brandImages = brandImagesRepository.getOne(brandImagesDTO1.getId());
        brandImages.setTitle(brandImagesDTO1.getTitle());
        brandImages.setUpdatedDate(new Date());
    }

    public void createBrandCategory(BrandDTO brandDTO) throws BrandNotFoundException {
        if(productBrandCategoryRepository.findByType(brandDTO.getType()) != null) {
            throw new BrandNotFoundException("Brand Category existed");
        }
        ProductBrandCategory brandCategory = new ProductBrandCategory(brandDTO.getType());
        productBrandCategoryRepository.save(brandCategory);
    }

    public List<BrandImages> getAllBrandImages() {
        return brandImagesRepository.findAll();
    }

    public BrandImages getBrandImagesById(Integer brandId) throws BrandNotFoundException {
        Optional<BrandImages> brandImages = brandImagesRepository.findById(brandId);
        if(brandImages.isPresent()) {
            return brandImages.get();
        }
        throw new BrandNotFoundException("Brand Not Found");
    }

    private File convertMultipartToFile(MultipartFile file) throws IOException {
        File covFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(covFile);
        fos.write(file.getBytes());
        fos.close();
        return covFile;
    }

    public void createBrandImages(BrandImagesDTO1 brandImagesDTO1, MultipartFile[] files) {
        ProductBrandCategory brandCategory = productBrandCategoryRepository.findByType(brandImagesDTO1.getBrandName());

        String urlUpload = "";

        try {
            List<MultipartFile> fileNames = new ArrayList<>();
            Arrays.asList(files).stream().forEach(file -> {
                fileNames.add(file);
            });
            File uploadFile = convertMultipartToFile(fileNames.get(0));
            Map uploadResult = cloudinary.uploader().upload(uploadFile, ObjectUtils.emptyMap());
            urlUpload = uploadResult.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        BrandImages brandImages = new BrandImages(brandImagesDTO1.getTitle(), null, urlUpload,brandCategory);
        brandImagesRepository.save(brandImages);
    }
}
