package com.vegetableshoppingbe.repository;

import com.vegetableshoppingbe.dto.projection.FeatureProduct;
import com.vegetableshoppingbe.entity.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.data.jpa.repository.Query;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p " +
            "where p.productName like %:productName%")
    Page<Product> findProductsLikeProductName(@Param("productName") String productName, Pageable pageable);

    @Query("SELECT o FROM Product o WHERE :categoryId is null or o.category.categoryId= :categoryId")
    Page<Product> findProductByCategoryId(@Param("categoryId") Integer categoryId, Pageable pageable);

    @Query(value = """
                SELECT p.product_id   AS productId,
                       p.product_name AS productName,
                       p.photo        AS photo,
                       p.price        AS price
                FROM product p
                order by created_date desc
                LIMIT 8
            """, nativeQuery = true)
    List<FeatureProduct> getFeatureProducts();

}
