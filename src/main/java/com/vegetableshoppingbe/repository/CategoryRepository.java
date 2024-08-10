package com.vegetableshoppingbe.repository;

import com.vegetableshoppingbe.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("select c from Category c " +
            "where c.categoryName like %:keyword%")
    Page<Category> findCategoriesLikeCategoryName(@Param("keyword") String keyword , Pageable pageable);

}

