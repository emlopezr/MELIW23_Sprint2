package com.example.be_java_hisp_w23_g3.controller.user;


import com.example.be_java_hisp_w23_g3.controller.UserController;
import com.example.be_java_hisp_w23_g3.service.user.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserControllerTests {

    @Mock
    UserService userService;

    @InjectMocks
    UserController controller;

}
