package com.example.be_java_hisp_w23_g3.service.product;

import com.example.be_java_hisp_w23_g3.repository.product.ProductRepository;
import com.example.be_java_hisp_w23_g3.repository.seller.SellerRepository;
import com.example.be_java_hisp_w23_g3.repository.user.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTests {

    @Mock
    ProductRepository productRepository;

    @Mock
    SellerRepository sellerRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    ProductServiceImpl service;

}
