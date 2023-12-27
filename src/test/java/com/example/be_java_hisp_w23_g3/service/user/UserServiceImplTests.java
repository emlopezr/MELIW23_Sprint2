package com.example.be_java_hisp_w23_g3.service.user;

import com.example.be_java_hisp_w23_g3.dto.response.UserDTO;
import com.example.be_java_hisp_w23_g3.entity.Seller;
import com.example.be_java_hisp_w23_g3.entity.User;
import com.example.be_java_hisp_w23_g3.exception.InvalidOrderException;
import com.example.be_java_hisp_w23_g3.exception.NotFoundException;
import com.example.be_java_hisp_w23_g3.entity.Seller;
import com.example.be_java_hisp_w23_g3.entity.User;
import com.example.be_java_hisp_w23_g3.repository.seller.SellerRepository;
import com.example.be_java_hisp_w23_g3.repository.user.UserRepository;
import com.example.be_java_hisp_w23_g3.util.SellerTestDataBuilder;
import com.example.be_java_hisp_w23_g3.util.UserMapper;
import com.example.be_java_hisp_w23_g3.util.UserTestDataBuilder;
import org.junit.jupiter.api.Test;
import com.example.be_java_hisp_w23_g3.util.SellerTestDataBuilder;
import com.example.be_java_hisp_w23_g3.util.UserTestDataBuilder;
import org.junit.jupiter.api.Assertions;
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

import java.util.Optional;

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
    void getFollowersCount_getResultOfNumberOfUsersWhoFollowingSpecificSeller(){
        Long param = 1L;

        Seller seller = new SellerTestDataBuilder()
                .withId(param)
                .sellerWithFollowers()
                .build();

        Integer expected = seller.getFollower().size();

        when(sellerRepository.read(param)).thenReturn(Optional.of(seller));

        Integer actual = service.getFollowersCount(param).getFollowersCount();

        Assertions.assertEquals(expected, actual);
    }


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

    @Test
    void getFollowersList_shouldReturnNotOrderedList() {
        Long sellerId = 1L;
        Seller seller = new SellerTestDataBuilder()
                .withId(sellerId)
                .sellerWithFollowers()
                .build();
        List<Long> expected = seller.getFollower().stream()
                .map(User::getId).toList();

        when(sellerRepository.read(sellerId)).thenReturn(Optional.of(seller));

        List<Long> actual = service.getFollowersList(sellerId, null).getFollowers()
                .stream().map(UserDTO::getUserId).toList();

        assertEquals(actual, expected);
    }

    @Test
    void getFollowersList_shouldThrowInvalidOrderException() {
        Long sellerId = 1L;
        String order = "any value other than 'name_asc' or 'name_desc'";
        Seller seller = new SellerTestDataBuilder()
                .withId(sellerId)
                .sellerWithFollowers()
                .build();

        when(sellerRepository.read(sellerId)).thenReturn(Optional.of(seller));

        assertThrows(InvalidOrderException.class, () -> service.getFollowersList(sellerId, order));
    }

    @Test
    void getFollowersList_shouldReturnEmptyList() {
        Long sellerId = 1L;
        Seller seller = new SellerTestDataBuilder()
                .sellerByDefault()
                .build();

        when(sellerRepository.read(sellerId)).thenReturn(Optional.of(seller));

        List<Long> actual = service.getFollowersList(sellerId, null).getFollowers()
                .stream().map(UserDTO::getUserId).toList();

        assertTrue(actual.isEmpty());
    }

    @Test
    void getFollowersList_shouldThrowNotFoundException() {
        when(sellerRepository.read(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getFollowersList(1L, null));
    }

    @Test
    void getFollowedSellersList_shouldReturnNameAscOrderedList() {
        Long userId = 1L;
        String order = "name_asc";
        User user = new UserTestDataBuilder()
                .withId(userId)
                .userWithFollowings()
                .build();
        List<Long> expected = user.getFollowing().stream()
                .sorted(Comparator.comparing(User::getUsername))
                .map(User::getId).toList();

        when(userRepository.read(userId)).thenReturn(Optional.of(user));

        List<Long> actual = service.getFollowedSellersList(userId, order).getFollowed()
                .stream().map(UserDTO::getUserId).toList();

        assertEquals(actual, expected);
    }

    @Test
    void getFollowedSellersList_shouldReturnNameDescOrderedList() {
        Long userId = 1L;
        String order = "name_desc";
        User user = new UserTestDataBuilder()
                .withId(userId)
                .userWithFollowings()
                .build();
        List<Long> expected = user.getFollowing().stream()
                .sorted(Comparator.comparing(User::getUsername).reversed())
                .map(User::getId).toList();

        when(userRepository.read(userId)).thenReturn(Optional.of(user));

        List<Long> actual = service.getFollowedSellersList(userId, order).getFollowed()
                .stream().map(UserDTO::getUserId).toList();

        assertEquals(actual, expected);
    }

    @Test
    void getFollowedSellersList_shouldReturnNotOrderedList() {
        Long userId = 1L;
        User user = new UserTestDataBuilder()
                .withId(userId)
                .userWithFollowings()
                .build();
        List<Long> expected = user.getFollowing().stream()
                .map(User::getId).toList();

        when(userRepository.read(userId)).thenReturn(Optional.of(user));

        List<Long> actual = service.getFollowedSellersList(userId, null).getFollowed()
                .stream().map(UserDTO::getUserId).toList();

        assertEquals(actual, expected);
    }

    @Test
    void getFollowedSellersList_shouldThrowInvalidOrderException() {
        Long userId = 1L;
        String order = "any value other than 'name_asc' or 'name_desc'";
        User user = new UserTestDataBuilder()
                .withId(userId)
                .userWithFollowings()
                .build();

        when(userRepository.read(userId)).thenReturn(Optional.of(user));

        assertThrows(InvalidOrderException.class, () -> service.getFollowedSellersList(userId, order));
    }

    @Test
    void getFollowedSellersList_shouldReturnEmptyList() {
        Long userId = 1L;
        User user = new UserTestDataBuilder()
                .userByDefault()
                .build();

        when(userRepository.read(userId)).thenReturn(Optional.of(user));

        List<Long> actual = service.getFollowedSellersList(userId, null).getFollowed()
                .stream().map(UserDTO::getUserId).toList();

        assertTrue(actual.isEmpty());
    }

    @Test
    void getFollowedSellersList_shouldThrowNotFoundException() {
        when(userRepository.read(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getFollowedSellersList(1L, null));
    }
}
