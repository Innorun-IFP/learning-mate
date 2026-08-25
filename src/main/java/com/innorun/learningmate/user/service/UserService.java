package com.innorun.learningmate.user.service;

import com.innorun.learningmate.user.dto.UserGetResponse;
import com.innorun.learningmate.user.entity.User;
import com.innorun.learningmate.user.exception.UserErrorCode;
import com.innorun.learningmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserGetResponse getMe(Long userId) {
        return UserGetResponse.from(getActiveUser(userId));
    }

    @PreAuthorize("#userId == authentication.principal.id")
    @Transactional
    public void withdrawMe(Long userId) {
        User user = getActiveUser(userId);
        user.markDeleted();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void withdrawByAdmin(Long targetUserId) {
        User user = getActiveUser(targetUserId);
        user.markDeleted();
    }

    private User getActiveUser(Long userId) {
        return userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(UserErrorCode.USER_NOT_FOUND::toException);
    }
}
