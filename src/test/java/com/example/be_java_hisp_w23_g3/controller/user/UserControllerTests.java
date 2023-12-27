package com.example.be_java_hisp_w23_g3.controller.user;


import com.example.be_java_hisp_w23_g3.controller.UserController;
import com.example.be_java_hisp_w23_g3.dto.response.FollowersListDTO;
import com.example.be_java_hisp_w23_g3.exception.InvalidOrderException;
import com.example.be_java_hisp_w23_g3.service.user.UserService;
import com.example.be_java_hisp_w23_g3.util.FollowersListDTOTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTests {

    @Mock
    UserService userService;

    @InjectMocks
    UserController controller;

    @Test
    void getFollowersList_shouldAcceptNameAscOrderParameter() {
        Long userId = 1L;
        String order = "name_asc";

        FollowersListDTO followersListDTO = new FollowersListDTOTestDataBuilder()
                .followersListDTOWithFollowers().build();

        when(userService.getFollowersList(userId, order))
                .thenReturn(followersListDTO);

        controller.getFollowersList(userId, order);

        verify(userService).getFollowersList(userId, order);
    }

    @Test
    void getFollowersList_shouldAcceptNameDescOrderParameter() {
        Long userId = 1L;
        String order = "name_desc";

        FollowersListDTO followersListDTO = new FollowersListDTOTestDataBuilder()
                .followersListDTOWithFollowers().build();

        when(userService.getFollowersList(userId, order))
                .thenReturn(followersListDTO);

        controller.getFollowersList(userId, order);

        verify(userService).getFollowersList(userId, order);
    }

    @Test
    void getFollowersList_shouldAcceptNullOrderParameter() {
        Long userId = 1L;

        FollowersListDTO followersListDTO = new FollowersListDTOTestDataBuilder()
                .followersListDTOWithFollowers().build();

        when(userService.getFollowersList(userId, null))
                .thenReturn(followersListDTO);

        controller.getFollowersList(userId, null);

        verify(userService).getFollowersList(userId, null);
    }

    @Test
    void getFollowersList_shouldThrowInvalidOrderException() {
        Long userId = 1L;
        String order = "any other than name_asc or name_desc";

        doThrow(new InvalidOrderException(
                "The 'order' parameter is invalid. The permitted values are 'name_asc' or 'name_desc'."))
                .when(userService).getFollowersList(userId, order);

        assertThrows(InvalidOrderException.class, () -> controller.getFollowersList(userId, order));

        verify(userService).getFollowersList(userId, order);
    }

}
