package com.example.be_java_hisp_w23_g3.service.product;

import com.example.be_java_hisp_w23_g3.dto.response.FollowedPostsListDTO;
import com.example.be_java_hisp_w23_g3.entity.Post;
import com.example.be_java_hisp_w23_g3.entity.User;
import com.example.be_java_hisp_w23_g3.exception.NotFoundException;
import com.example.be_java_hisp_w23_g3.repository.product.ProductRepository;
import com.example.be_java_hisp_w23_g3.repository.seller.SellerRepository;
import com.example.be_java_hisp_w23_g3.repository.user.UserRepository;
import com.example.be_java_hisp_w23_g3.util.PostTestDataBuilder;
import com.example.be_java_hisp_w23_g3.util.UserTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

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

    @Test
    void followedPosts_ListReturnsCorrectDTOWhenUserFollowsSellers() {
        Long userId = 1L;
        User user = new UserTestDataBuilder().userWithFollowings().withId(userId).build();
        when(userRepository.read(userId)).thenReturn(Optional.of(user));

        List<Post> posts = Collections.singletonList(new PostTestDataBuilder().postByDefault().build());
        when(productRepository.readBatchBySellerIds(anyList())).thenReturn(posts);

        FollowedPostsListDTO result = service.followedPostsList(userId, null);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(posts.size(), result.getPosts().size());
    }

    @Test
    void followedPosts_ListThrowsNotFoundExceptionWhenUserDoesNotExist() {
        Long userId = 1L;
        when(userRepository.read(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.followedPostsList(userId, null));
    }

    @Test
    void followedPosts_ListReturnsEmptyDTOWhenUserFollowsNoSellers() {
        Long userId = 1L;
        User user = new UserTestDataBuilder().userByDefault().withId(userId).build();

        when(userRepository.read(userId)).thenReturn(Optional.of(user));

        FollowedPostsListDTO result = service.followedPostsList(userId, null);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertTrue(result.getPosts().isEmpty());
    }

    @Test
    void followedPosts_ListReturnsCorrectlyOrderedDTOWhenOrderIsDateAsc() {
    Long userId = 1L;
    User user = new UserTestDataBuilder().userWithFollowings().withId(userId).build();
    when(userRepository.read(userId)).thenReturn(Optional.of(user));

    List<Post> posts = Arrays.asList(
            new PostTestDataBuilder().postByDefault().withId(1L).withDate(LocalDate.now().minusDays(2)).build(),
            new PostTestDataBuilder().postByDefault().withId(2L).withDate(LocalDate.now().minusDays(1)).build(),
            new PostTestDataBuilder().postByDefault().withId(3L).withDate(LocalDate.now()).build()
    );
    when(productRepository.readBatchBySellerIds(anyList())).thenReturn(posts);

    FollowedPostsListDTO result = service.followedPostsList(userId, "date_asc");

    assertNotNull(result);
    assertEquals(userId, result.getUserId());
    assertEquals(posts.size(), result.getPosts().size());
    assertEquals(1L, result.getPosts().get(0).getPostId());
    assertEquals(2L, result.getPosts().get(1).getPostId());
    assertEquals(3L, result.getPosts().get(2).getPostId());
}

    @Test
    void followedPosts_ListReturnsCorrectlyOrderedDTOWhenOrderIsDateDesc() {
        Long userId = 1L;
        User user = new UserTestDataBuilder().userWithFollowings().withId(userId).build();
        when(userRepository.read(userId)).thenReturn(Optional.of(user));

        List<Post> posts = Arrays.asList(
                new PostTestDataBuilder().postByDefault().withId(1L).withDate(LocalDate.now().minusDays(2)).build(),
                new PostTestDataBuilder().postByDefault().withId(2L).withDate(LocalDate.now().minusDays(1)).build(),
                new PostTestDataBuilder().postByDefault().withId(3L).withDate(LocalDate.now()).build()
        );
        when(productRepository.readBatchBySellerIds(anyList())).thenReturn(posts);

        FollowedPostsListDTO result = service.followedPostsList(userId, "date_desc");

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(posts.size(), result.getPosts().size());
        assertEquals(3L, result.getPosts().get(0).getPostId());
        assertEquals(2L, result.getPosts().get(1).getPostId());
        assertEquals(1L, result.getPosts().get(2).getPostId());
    }
}
