package com.example.be_java_hisp_w23_g3.controller;

import com.example.be_java_hisp_w23_g3.service.user.UserService;

import com.example.be_java_hisp_w23_g3.dto.response.FollowersListDTO;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<FollowersListDTO> getFollowersList(
        @PathVariable("userId") @Positive(message = "El user_id debe ser mayor que cero") Long userId,
        @RequestParam(required = false) @Pattern(regexp = "^(?i)(NAME_ASC|NAME_DESC)$", message = "El campo order solo puede ser NAME_ASC o NAME_DESC") String order
    ) {
        return new ResponseEntity<>(userService.getFollowersList(userId, order), HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<?> getFollowersCount(
        @PathVariable @Positive(message = "El user_id debe ser mayor que cero") Long userId
    ) {
        return new ResponseEntity<>(userService.getFollowersCount(userId), HttpStatus.OK);
    }

    @GetMapping("/{userID}/followed/list")
    public ResponseEntity<?> getFollowedSellerList(
        @PathVariable @Positive(message = "El user_id debe ser mayor que cero") Long userID,
        @RequestParam(required = false) @Pattern(regexp = "^(?i)(NAME_ASC|NAME_DESC)$", message = "El campo order solo puede ser NAME_ASC o NAME_DESC") String order
    ) {
        return new ResponseEntity<>(userService.getFollowedSellersList(userID, order), HttpStatus.OK);
    }

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> followSeller(
        @PathVariable @Positive(message = "El user_id seguidor debe ser mayor que cero") Long userId,
        @PathVariable @Positive(message = "El user_id a seguir debe ser mayor que cero") Long userIdToFollow
    ) {
        return new ResponseEntity<>(userService.followSeller(userId, userIdToFollow), HttpStatus.OK);
    }
    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unFollowSeller(
        @PathVariable @Positive(message = "El user_id seguidor debe ser mayor que cero") Long userId,
        @PathVariable @Positive(message = "El user_id a dejar de seguir debe ser mayor que cero") Long userIdToUnfollow
    ) {
        return new ResponseEntity<>(userService.unFollowSeller(userId, userIdToUnfollow), HttpStatus.OK);
    }
}
