package com.innorun.learningmate.studygroup.exception;

import com.innorun.learningmate.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum StudyGroupErrorCode {

    STUDY_GROUP_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "스터디 그룹을 찾을 수 없습니다."
    ),
    NOT_STUDY_GROUP_LEADER(
            HttpStatus.FORBIDDEN,
            "스터디 그룹장만 변경할 수 있습니다."
    );

    private final HttpStatus status;
    private final String message;

    public ServiceException toException() {
        return new ServiceException(status, message);
    }
}
