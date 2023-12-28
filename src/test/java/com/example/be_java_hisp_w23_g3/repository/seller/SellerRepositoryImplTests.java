package com.example.be_java_hisp_w23_g3.repository.seller;

import com.example.be_java_hisp_w23_g3.entity.Seller;
import com.example.be_java_hisp_w23_g3.entity.User;
import com.example.be_java_hisp_w23_g3.util.SellerTestDataBuilder;
import com.example.be_java_hisp_w23_g3.util.UserTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class SellerRepositoryImplTests {
    private SellerRepository repository;

    private Seller seller;

    @BeforeEach
    void setUpTestData() {
        repository = new SellerRepositoryImpl();
        seller = new SellerTestDataBuilder().sellerByDefault().build();
    }

    @Test
    void create_shouldCreateAndReturnSeller() {
        Seller savedSeller = repository.create(seller);
        boolean isPresent = repository.read(seller.getId()).isPresent();

        assertTrue(isPresent);
        assertTrue(areEquals(savedSeller));
    }

    @Test
    void createAll_shouldCreateAllAndReturnSellers() {
        Seller seller1 = new SellerTestDataBuilder().sellerByDefault()
                .build();
        Seller seller2 = new SellerTestDataBuilder().sellerByDefault()
                .build();
        Seller seller3 = new SellerTestDataBuilder().sellerByDefault()
                .build();
        Seller seller4 = new SellerTestDataBuilder().sellerByDefault()
                .build();

        List<Seller> sellers = List.of(seller1, seller2, seller3, seller4);
        List<Seller> savedSellers = repository.createAll(sellers);

        assertEquals(sellers.size(), savedSellers.size());
    }

    @Test
    void read_shouldReturnSeller() {
        repository.create(seller);

        Seller readSeller = repository.read(seller.getId()).orElse(null);

        assertNotNull(readSeller);
    }

    @Test
    void read_shouldReturnEmpty() {
        Optional<Seller> readSeller = repository.read(seller.getId());

        assertTrue(readSeller.isEmpty());
    }

    @Test
    void delete_shouldRemoveSeller() {
        repository.create(seller);

        repository.delete(seller.getId());

        assertFalse(repository.read(seller.getId()).isPresent());
    }

    @Test
    void update_shouldUpdate() {
        Seller seller2 = new SellerTestDataBuilder().sellerByDefault()
                .withUsername("Updated Username")
                .build();

        repository.create(seller);

        repository.update(seller.getId(), seller2);

        assertTrue(areEquals(seller2));
    }

    private boolean areEquals(Seller o) {
        if (o == null) return false;
        return Objects.equals(o.getId(), seller.getId()) &&
                Objects.equals(o.getUsername(), seller.getUsername());
    }
}
