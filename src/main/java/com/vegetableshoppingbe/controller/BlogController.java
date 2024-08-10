package com.vegetableshoppingbe.controller;

import com.vegetableshoppingbe.dto.request.BlogRequest;
import com.vegetableshoppingbe.dto.response.BlogResponse;
import com.vegetableshoppingbe.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blogs")
@CrossOrigin("http://localhost:8081")
public class BlogController {

    private final BlogService blogServiceImpl;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getBlogs(
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "active", required = false) Boolean active,
            @RequestParam(name = "categoryId", required = false) Integer categoryId,
            @RequestParam(name = "blogId", required = false) Integer blogId,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        Page<BlogResponse> blogResponses = blogServiceImpl.getAll(pageNo, keyword, active, categoryId, blogId, pageSize);
        Map<String, Object> response = new HashMap<>();
        response.put("blogs", blogResponses.getContent());
        response.put("totalPages", blogResponses.getTotalPages());
        response.put("currentPage", blogResponses.getNumber() + 1);
        response.put("totalItems", blogResponses.getTotalElements());
        System.out.printf(response.toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> getBlog(@PathVariable Integer id){
        return ResponseEntity.ok(blogServiceImpl.getBlog(id));
    }

    @GetMapping("/threeBlogs")
    public ResponseEntity<List<BlogResponse>> getThreeBlogs(){
        return ResponseEntity.ok(blogServiceImpl.getThreeBlog());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Map<String, Object>> getBlogsByCategoryId(
            @PathVariable Integer categoryId,
            @PathVariable Integer blogId,
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo
    ) {
        Page<BlogResponse> blogResponses = blogServiceImpl.getByCategoryId(categoryId, blogId, pageNo);
        Map<String, Object> response = new HashMap<>();
        response.put("blogs", blogResponses.getContent());
        response.put("totalPages", blogResponses.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BlogRequest> saveBlog(@ModelAttribute BlogRequest blogRequest, @RequestParam("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(blogServiceImpl.saveBlog(blogRequest, file));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BlogRequest> updateBlog(@PathVariable Integer id,
                                                  @ModelAttribute BlogRequest blogRequest,
                                                  @RequestParam(value = "file", required = false)MultipartFile file){
        return ResponseEntity.ok(blogServiceImpl.updateBlog(id, blogRequest, file));
    }


}