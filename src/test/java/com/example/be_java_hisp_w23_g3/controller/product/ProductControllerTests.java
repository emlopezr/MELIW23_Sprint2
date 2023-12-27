package com.example.be_java_hisp_w23_g3.controller.product;

import com.example.be_java_hisp_w23_g3.controller.ProductController;
import com.example.be_java_hisp_w23_g3.service.product.ProductService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductControllerTests {

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController controller;

}
