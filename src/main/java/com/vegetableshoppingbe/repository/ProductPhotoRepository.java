package com.vegetableshoppingbe.repository;

import com.vegetableshoppingbe.entity.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Integer> {
    @Query("SELECT o FROM ProductPhoto o WHERE o.product.productId = ?1")
    List<ProductPhoto> findByProductId(int productId);
}
