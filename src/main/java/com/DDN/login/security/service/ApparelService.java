package com.DDN.login.security.service;

import com.DDN.login.dto.filter.ApparelCategoryDTO;
import com.DDN.login.dto.filter.ApparelImagesDTO1;
import com.DDN.login.exception.ApparelNotFoundException;
import com.DDN.login.model.categories.ApparelCategory;
import com.DDN.login.model.categories.GenderCategory;
import com.DDN.login.model.images.ApparelImages;
import com.DDN.login.repository.dao.categories.ApparelCategoryRepository;
import com.DDN.login.repository.dao.categories.GenderCategoryRepository;
import com.DDN.login.repository.dao.images.ApparelImagesRepository;
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
public class ApparelService {
    @Autowired
    private ApparelCategoryRepository apparelCategoryRepository;

    @Autowired
    private ApparelImagesRepository apparelImagesRepository;

    @Autowired
    private GenderCategoryRepository genderCategoryRepository;

    @Autowired
    private Cloudinary cloudinaryConfig;

    public List<ApparelCategory> listApparelCategory() {
        return apparelCategoryRepository.findAll();
    }

    public ApparelCategory getApparelCategoryById(Integer apparelId) throws ApparelNotFoundException {
        Optional<ApparelCategory> apparelCategory = apparelCategoryRepository.findById(apparelId);
        if(apparelCategory.isPresent()) {
            return apparelCategory.get();
        }
        throw new ApparelNotFoundException("Apparel not Found");
    }



    public void updateApparelCategory(ApparelCategoryDTO apparelCategoryDTO) throws ApparelNotFoundException {
        ApparelCategory apparelCategory = apparelCategoryRepository.getOne(apparelCategoryDTO.getId());
        apparelCategory.setType(apparelCategoryDTO.getType());
        apparelCategory.setUpdateDate(new Date());
        apparelCategoryRepository.save(apparelCategory);
    }

    public void updateApparelImages(ApparelImagesDTO1 apparelImagesDTO1) throws ApparelNotFoundException {
        ApparelImages apparelImages = apparelImagesRepository.getOne(apparelImagesDTO1.getId());
        apparelImages.setTitle(apparelImagesDTO1.getTitle());
        apparelImages.setUpdatedDate(new Date());
        apparelImagesRepository.save(apparelImages);
    }

    public void createApparelCategory(ApparelCategoryDTO apparelCategoryDTO) throws  ApparelNotFoundException {
        if(apparelCategoryRepository.findByType(apparelCategoryDTO.getType()) != null) {
            throw new ApparelNotFoundException("Apparel Category existed!");
        }
        ApparelCategory apparelCategory = new ApparelCategory();
        apparelCategory.setId(apparelCategoryDTO.getId());
        apparelCategory.setType(apparelCategoryDTO.getType());
        apparelCategory.setCreatedDate(new Date());
        apparelCategory.setUpdateDate(new Date());
        apparelCategoryRepository.save(apparelCategory);
    }

    public List<ApparelImages> getAllApparelImages() {
        return apparelImagesRepository.findAll();
    }

    public ApparelImages getApparelImagesById(Integer apparelId) throws ApparelNotFoundException {
        Optional<ApparelImages> apparelImages = apparelImagesRepository.findById(apparelId);
        if(apparelImages.isPresent()) {
            return apparelImages.get();
        }
        throw new ApparelNotFoundException("Apparel not Found");
    }

    private File convertMultipartToFile(MultipartFile file) throws IOException {
        File covFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(covFile);
        fos.write(file.getBytes());
        fos.close();
        return covFile;
    }

    public void createApparelImages(ApparelImagesDTO1 apparelImagesDTO1, MultipartFile[] files) {
        ApparelCategory apparelCategory = apparelCategoryRepository.findByType(apparelImagesDTO1.getApparelName());
        GenderCategory genderCategory = genderCategoryRepository.findByType(apparelImagesDTO1.getGenderName());
        String urlUpload = "";

        try {
            List<MultipartFile> fileNames = new ArrayList<>();
            Arrays.asList(files).stream().forEach(file -> {
                fileNames.add(file);
            });
            File uploadFile = convertMultipartToFile(fileNames.get(0));
            Map uploadResult = cloudinaryConfig.uploader().upload(uploadFile, ObjectUtils.emptyMap());
            urlUpload = uploadResult.get("url").toString();
            uploadFile.delete();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        ApparelImages apparelImages = new ApparelImages(apparelImagesDTO1.getTitle(), null, urlUpload,apparelCategory,genderCategory);
        apparelImagesRepository.save(apparelImages);
    }
}
