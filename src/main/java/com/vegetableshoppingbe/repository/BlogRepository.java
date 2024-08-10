package com.vegetableshoppingbe.repository;

import com.vegetableshoppingbe.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

    @Query(value = "SELECT * FROM blog b " +
            "WHERE (:keyword IS NULL OR b.title LIKE %:keyword%) " +
            "AND (:active IS NULL OR b.active = :active) " +
            "AND (:blogId IS NULL OR b.id = :blogId) " +
            "AND (:categoryId IS NULL OR b.category_id = :categoryId)", nativeQuery = true)
    Page<Blog> findAllBlogs(@Param("active") Boolean active, @Param("keyword") String keyword, @Param("blogId")Integer blogId, @Param("categoryId") Integer categoryId, Pageable pageable);

    @Query("SELECT b FROM Blog b JOIN b.category c WHERE (:active IS NULL OR b.active = :active) AND (:categoryId IS NULL OR c.categoryId = :categoryId)  AND (:blogId IS NULL OR b.id = :blogId)")
    Page<Blog> findByCategoryId(@Param("categoryId") Integer categoryId, Integer blogId, Pageable pageable);

    @Query(value = "SELECT * FROM blog b WHERE b.active=1 ORDER BY b.created_date DESC LIMIT 3", nativeQuery = true)
    List<Blog> getThreeBlog();

}