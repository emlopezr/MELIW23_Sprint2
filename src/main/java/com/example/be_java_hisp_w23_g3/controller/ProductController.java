package com.example.be_java_hisp_w23_g3.controller;

import com.example.be_java_hisp_w23_g3.dto.request.PostRequestDTO;
import com.example.be_java_hisp_w23_g3.dto.response.PostResponseDTO;
import com.example.be_java_hisp_w23_g3.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/post")
    public ResponseEntity<PostResponseDTO> postProduct(@Valid @RequestBody PostRequestDTO postRequestDTO) {
        PostResponseDTO postResponseDTO = productService.postProduct(postRequestDTO);
        return new ResponseEntity<>(postResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<?> followedPostsList(@PathVariable Long userId,
                                               @RequestParam(required = false) String order) {
        return ResponseEntity.ok().body(productService.followedPostsList(userId, order));
    }
}
