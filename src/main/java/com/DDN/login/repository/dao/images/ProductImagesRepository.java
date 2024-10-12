package com.DDN.login.repository.dao.images;

import com.DDN.login.model.images.CarouselImages;
import com.DDN.login.model.images.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImagesRepository extends JpaRepository<ProductImages, Integer> {
}
