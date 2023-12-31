package com.example.be_java_hisp_w23_g3.repository.product;

import com.example.be_java_hisp_w23_g3.entity.product.Post;
import com.example.be_java_hisp_w23_g3.entity.user.Seller;
import com.example.be_java_hisp_w23_g3.util.PostTestDataBuilder;
import com.example.be_java_hisp_w23_g3.util.ProductTestDataBuilder;
import com.example.be_java_hisp_w23_g3.util.SellerTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryImplTests {
    private ProductRepository repository;

    private Post post;

    @BeforeEach
    void setUpTestData() {
        repository = new ProductRepositoryImpl();
        post = new PostTestDataBuilder().postByDefault().build();
    }

    @Test
    void create_shouldCreateAndReturnPost() {
        // Arrange
        Post savedPost = repository.create(post);

        // Act
        boolean isPresent = repository.read(savedPost.getId()).isPresent();

        // Assert
        assertTrue(isPresent);
        assertTrue(areEquals(savedPost));
    }

    @Test
    void createAll_shouldCreateAllAndReturnPosts() {
        // Arrange
        Post post1 = new PostTestDataBuilder().postByDefault().build();
        Post post2 = new PostTestDataBuilder().postByDefault().build();
        Post post3 = new PostTestDataBuilder().postByDefault().build();
        Post post4 = new PostTestDataBuilder().postByDefault().build();
        List<Post> posts = List.of(post1, post2, post3, post4);

        // Act
        List<Post> savedPosts = repository.createAll(posts);

        // Assert
        assertEquals(posts.size(), savedPosts.size());
    }

    @Test
    void read_shouldReturnPost() {
        // Arrange
        repository.create(post);

        // Act
        Post readPost = repository.read(post.getId()).orElse(null);

        // Assert
        assertNotNull(readPost);
    }

    @Test
    void read_shouldReturnEmpty() {
        // Act
        Optional<Post> readPost = repository.read(post.getId());

        // Assert
        assertTrue(readPost.isEmpty());
    }

    @Test
    void readBatchBySellersId_shouldReturnSellersPosts() {
        // Arrange
        Seller seller1 = new SellerTestDataBuilder().sellerByDefault().withId(101L).build();
        Seller seller2 = new SellerTestDataBuilder().sellerByDefault().withId(102L).build();
        Seller seller3 = new SellerTestDataBuilder().sellerByDefault().withId(103L).build();
        Seller seller4 = new SellerTestDataBuilder().sellerByDefault().withId(104L).build();

        Post post1 = new PostTestDataBuilder().postByDefault()
                .withSeller(seller1)
                .build();
        Post post2 = new PostTestDataBuilder().postByDefault()
                .withSeller(seller2)
                .build();
        Post post3 = new PostTestDataBuilder().postByDefault()
                .withSeller(seller3)
                .build();
        Post post4 = new PostTestDataBuilder().postByDefault()
                .withSeller(seller4)
                .build();

        List<Post> posts = List.of(post1, post2, post3, post4);
        repository.createAll(posts);

        List<Long> sellerIds = List.of(101L, 103L, 104L);

        // Act
        List<Post> readPosts = repository.readBatchBySellerIds(sellerIds);

        // Assert
        assertEquals(3, readPosts.size());

        assertTrue(readPosts.stream().anyMatch(p -> areEquals(post1, p)));
        assertTrue(readPosts.stream().anyMatch(p -> areEquals(post3, p)));
        assertTrue(readPosts.stream().anyMatch(p -> areEquals(post4, p)));

        assertFalse(readPosts.stream().anyMatch(p -> areEquals(post2, p)));
    }

    @Test
    void readBatchBySellersId_shouldReturnEmptySellersPosts() {
        // Arrange
        Seller seller1 = new SellerTestDataBuilder().sellerByDefault().withId(101L).build();
        Seller seller2 = new SellerTestDataBuilder().sellerByDefault().withId(102L).build();
        Seller seller3 = new SellerTestDataBuilder().sellerByDefault().withId(103L).build();
        Seller seller4 = new SellerTestDataBuilder().sellerByDefault().withId(104L).build();

        Post post1 = new PostTestDataBuilder().postByDefault()
                .withSeller(seller1)
                .build();
        Post post2 = new PostTestDataBuilder().postByDefault()
                .withSeller(seller2)
                .build();
        Post post3 = new PostTestDataBuilder().postByDefault()
                .withSeller(seller3)
                .build();
        Post post4 = new PostTestDataBuilder().postByDefault()
                .withSeller(seller4)
                .build();

        List<Post> posts = List.of(post1, post3, post4);
        repository.createAll(posts);

        List<Long> sellerIds = List.of(102L);

        // Act
        List<Post> readPosts = repository.readBatchBySellerIds(sellerIds);

        // Assert
        assertTrue(readPosts.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllPosts() {
        // Arrange
        Post post1 = new PostTestDataBuilder().postByDefault()
                .build();
        Post post2 = new PostTestDataBuilder().postByDefault()
                .build();
        Post post3 = new PostTestDataBuilder().postByDefault()
                .build();
        Post post4 = new PostTestDataBuilder().postByDefault()
                .build();

        List<Post> posts = List.of(post1, post2, post3, post4);
        repository.createAll(posts);

        // Act
        List<Post> readPosts = repository.findAll();

        // Assert
        assertEquals(posts.size(), readPosts.size());
    }

    @Test
    void delete_shouldRemovePost() {
        // Arrange
        repository.create(post);

        // Act
        repository.delete(post.getId());

        // Assert
        assertFalse(repository.read(post.getId()).isPresent());
    }

    @Test
    void update_shouldUpdate() {
        // Arrange
        Post post1 = new PostTestDataBuilder().postByDefault().build();
        Post post2 = new PostTestDataBuilder().postByDefault()
                .withSeller(new SellerTestDataBuilder().sellerByDefault().build())
                .withProduct(new ProductTestDataBuilder().productByDefault().build())
                .withDate(LocalDate.of(2023, 12, 28))
                .withPrice(400.50)
                .withCategory(100)
                .build();

        repository.create(post1);

        // Act
        repository.update(post1.getId(), post2);

        // Assert
        assertTrue(areEquals(post1, post2));
    }

    private boolean areEquals(Post o) {
        if (o == null) return false;
        return o.getCategory() == post.getCategory() &&
                Objects.equals(o.getId(), post.getId()) &&
                Objects.equals(o.getSeller().getId(), post.getSeller().getId()) &&
                Objects.equals(o.getProduct().getId(), post.getProduct().getId()) &&
                Objects.equals(o.getDate(), post.getDate())
                && Objects.equals(o.getPrice(), post.getPrice());
    }

    private boolean areEquals(Post o, Post o2) {
        if (o == null) return false;
        if (o2 == null) return false;
        return o.getCategory() == o2.getCategory() &&
                Objects.equals(o.getId(), o2.getId()) &&
                Objects.equals(o.getSeller().getId(), o2.getSeller().getId()) &&
                Objects.equals(o.getProduct().getId(), o2.getProduct().getId()) &&
                Objects.equals(o.getDate(), o2.getDate())
                && Objects.equals(o.getPrice(), o2.getPrice());
    }
}
