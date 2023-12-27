package com.example.be_java_hisp_w23_g3.service.user;

import com.example.be_java_hisp_w23_g3.entity.Seller;
import com.example.be_java_hisp_w23_g3.entity.User;
import com.example.be_java_hisp_w23_g3.repository.seller.SellerRepository;
import com.example.be_java_hisp_w23_g3.repository.user.UserRepository;
import com.example.be_java_hisp_w23_g3.util.SellerTestDataBuilder;
import com.example.be_java_hisp_w23_g3.util.UserTestDataBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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


}
