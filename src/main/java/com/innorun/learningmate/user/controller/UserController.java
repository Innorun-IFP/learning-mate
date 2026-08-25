package com.innorun.learningmate.user.controller;

import com.innorun.learningmate.global.security.principal.CurrentUserId;
import com.innorun.learningmate.user.dto.UserGetResponse;
import com.innorun.learningmate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/me")
    public ResponseEntity<UserGetResponse> getMe(
            @CurrentUserId Long userId
    ) {
        return ResponseEntity.ok(userService.getMe(userId));
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<Void> withdrawMe(
            @CurrentUserId Long userId
    ) {
        userService.withdrawMe(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> withdrawByAdmin(
            @PathVariable Long userId
    ) {
        userService.withdrawByAdmin(userId);
        return ResponseEntity.noContent().build();
    }
}
