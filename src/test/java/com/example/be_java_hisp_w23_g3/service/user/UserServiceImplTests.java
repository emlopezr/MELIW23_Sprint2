package com.example.be_java_hisp_w23_g3.service.user;

import com.example.be_java_hisp_w23_g3.dto.response.MessageResponseDTO;
import com.example.be_java_hisp_w23_g3.entity.Seller;
import com.example.be_java_hisp_w23_g3.entity.User;
import com.example.be_java_hisp_w23_g3.exception.NotFoundException;
import com.example.be_java_hisp_w23_g3.repository.seller.SellerRepository;
import com.example.be_java_hisp_w23_g3.repository.seller.SellerRepositoryImpl;
import com.example.be_java_hisp_w23_g3.repository.user.UserRepository;
import com.example.be_java_hisp_w23_g3.util.SellerTestDataBuilder;
import com.example.be_java_hisp_w23_g3.util.UserTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTests {

    @Mock
    UserRepository userRepository;

    @Mock
    SellerRepository sellerRepository;

    @InjectMocks
    UserServiceImpl service;

    @Test
    void followSeller_shouldWorkWhenSellerExists(){
        Long userId = 1L;
        Long sellerIdToFollow = 9L;
        User user = new UserTestDataBuilder().userByDefault().withId(userId).withUsername("Lisandro").build();
        Seller sellerToFollow = new SellerTestDataBuilder().sellerByDefault().withId(sellerIdToFollow).withUsername("Karla").build();

        when(userRepository.read(userId)).thenReturn(Optional.ofNullable(user));
        when(sellerRepository.read(sellerIdToFollow)).thenReturn(Optional.ofNullable(sellerToFollow));
        MessageResponseDTO respond =service.followSeller(userId,sellerIdToFollow);

        assertEquals(user, sellerToFollow.getFollower().stream().findFirst().get());
        assertEquals(sellerToFollow, user.getFollowing().stream().findFirst().get());
        assertEquals(new MessageResponseDTO("Following a new Seller!"), respond);
        verify(sellerRepository,times(1)).read(sellerIdToFollow);
        verify(userRepository, times(1)).read(userId);
    }

}
