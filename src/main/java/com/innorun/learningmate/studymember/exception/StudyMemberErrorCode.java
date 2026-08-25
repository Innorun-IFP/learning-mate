package com.innorun.learningmate.studymember.exception;

import com.innorun.learningmate.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum StudyMemberErrorCode {

    STUDY_MEMBER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "스터디 그룹 멤버를 찾을 수 없습니다."
    ),
    ALREADY_JOINED(
            HttpStatus.CONFLICT,
            "이미 스터디 그룹에 가입한 사용자입니다."
    ),
    LEADER_CANNOT_LEAVE(
            HttpStatus.BAD_REQUEST,
            "스터디 그룹장은 그룹을 탈퇴할 수 없습니다."
    );

    private final HttpStatus status;
    private final String message;

    public ServiceException toException() {
        return new ServiceException(status, message);
    }
}
