package com.example.be_java_hisp_w23_g3.util;

import com.example.be_java_hisp_w23_g3.dto.response.FollowedListDTO;
import com.example.be_java_hisp_w23_g3.dto.response.FollowersListDTO;
import com.example.be_java_hisp_w23_g3.dto.response.SellerDTO;
import com.example.be_java_hisp_w23_g3.dto.response.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FollowedListDTOTestDataBuilder {
    public Long userId;
    public String userName;
    public List<SellerDTO> followed;

    public FollowedListDTOTestDataBuilder followersListDTOByDefault() {
        this.userId = 1L;
        this.userName = "username1";
        this.followed = List.of();
        return this;
    }

    public FollowedListDTOTestDataBuilder followersListDTOWithFollowers() {
        this.userId = 1L;
        this.userName = "username1";
        this.followed = List.of(
                SellerMapper.mapToDTO(new SellerTestDataBuilder().sellerByDefault().withId(101L).build()),
                SellerMapper.mapToDTO(new SellerTestDataBuilder().sellerByDefault().withId(102L).build()),
                SellerMapper.mapToDTO(new SellerTestDataBuilder().sellerByDefault().withId(103L).build())
        );
        return this;
    }

    public FollowedListDTO build() {
        return new FollowedListDTO(userId, userName, followed);
    }
}
