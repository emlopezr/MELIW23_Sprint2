package com.example.be_java_hisp_w23_g3.controller.product;

import com.example.be_java_hisp_w23_g3.controller.ProductController;
import com.example.be_java_hisp_w23_g3.dto.request.PostRequestDTO;
import com.example.be_java_hisp_w23_g3.dto.response.FollowedPostsListDTO;
import com.example.be_java_hisp_w23_g3.dto.response.PostResponseDTO;
import com.example.be_java_hisp_w23_g3.service.product.ProductService;
import com.example.be_java_hisp_w23_g3.util.FollowedPostsListDTOTestDataBuilder;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTests {

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController controller;

    @Test
    void followedPostsList_ReturnsCorrectResponseWithValidOrder() {
        Long userId = 1L;
        String order = "DATE_ASC";
        FollowedPostsListDTO followedPostsListDTO = new FollowedPostsListDTOTestDataBuilder().followedPostsListDTOWithPosts().build();
        when(productService.followedPostsList(userId, order)).thenReturn(followedPostsListDTO);

        ResponseEntity<?> response = controller.followedPostsList(userId, order);

        assertEquals(ResponseEntity.ok().body(followedPostsListDTO), response);
        verify(productService, times(1)).followedPostsList(userId, order);
    }

    @Test
    void followedPostsList_ReturnsCorrectResponseWithNullOrder() {
        Long userId = 1L;
        FollowedPostsListDTO followedPostsListDTO = new FollowedPostsListDTOTestDataBuilder().followedPostsListDTOWithPosts().build();
        when(productService.followedPostsList(userId, null)).thenReturn(followedPostsListDTO);

        ResponseEntity<?> response = controller.followedPostsList(userId, null);

        assertEquals(ResponseEntity.ok().body(followedPostsListDTO), response);
        verify(productService, times(1)).followedPostsList(userId, null);
    }

    @Test
    void followedPostsList_ThrowsExceptionWithInvalidOrder() {
        Long userId = 1L;
        String order = "INVALID_ORDER";
        when(productService.followedPostsList(userId, order)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> controller.followedPostsList(userId, order));
        verify(productService, times(1)).followedPostsList(userId, order);
    }
}
