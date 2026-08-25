package com.innorun.learningmate.user.dto;

import com.innorun.learningmate.user.entity.Role;
import com.innorun.learningmate.user.entity.User;

public record UserGetResponse(
        Long id,
        String email,
        String nickname,
        Role role
) {
    public static UserGetResponse from(User user) {
        return new UserGetResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getRole()
        );
    }
}
