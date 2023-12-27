package com.example.be_java_hisp_w23_g3.service.user;

import com.example.be_java_hisp_w23_g3.dto.response.UserDTO;
import com.example.be_java_hisp_w23_g3.entity.Seller;
import com.example.be_java_hisp_w23_g3.entity.User;
import com.example.be_java_hisp_w23_g3.exception.NotFoundException;
import com.example.be_java_hisp_w23_g3.repository.seller.SellerRepository;
import com.example.be_java_hisp_w23_g3.repository.user.UserRepository;
import com.example.be_java_hisp_w23_g3.util.SellerTestDataBuilder;
import com.example.be_java_hisp_w23_g3.util.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTests {

    @Mock
    UserRepository userRepository;

    @Mock
    SellerRepository sellerRepository;

    @InjectMocks
    UserServiceImpl service;


    @Test
    void getFollowersList_shouldReturnNameAscOrderedList() {
        Long sellerId = 1L;
        String order = "name_asc";
        Seller seller = new SellerTestDataBuilder()
                .withId(sellerId)
                .sellerWithFollowers()
                .build();
        List<Long> expected = seller.getFollower().stream()
                .sorted(Comparator.comparing(User::getUsername))
                .map(User::getId).toList();

        when(sellerRepository.read(sellerId)).thenReturn(Optional.of(seller));

        List<Long> actual = service.getFollowersList(sellerId, order).getFollowers()
                .stream().map(UserDTO::getUserId).toList();

        assertEquals(actual, expected);
    }

    @Test
    void getFollowersList_shouldReturnNameDescOrderedList() {
        Long sellerId = 1L;
        String order = "name_desc";
        Seller seller = new SellerTestDataBuilder()
                .withId(sellerId)
                .sellerWithFollowers()
                .build();
        List<Long> expected = seller.getFollower().stream()
                .sorted(Comparator.comparing(User::getUsername).reversed())
                .map(User::getId).toList();

        when(sellerRepository.read(sellerId)).thenReturn(Optional.of(seller));

        List<Long> actual = service.getFollowersList(sellerId, order).getFollowers()
                .stream().map(UserDTO::getUserId).toList();

        assertEquals(actual, expected);
    }
}
