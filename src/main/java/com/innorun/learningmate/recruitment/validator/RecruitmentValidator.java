package com.innorun.learningmate.recruitment.validator;

import com.innorun.learningmate.global.exception.ServiceException;
import com.innorun.learningmate.recruitment.dto.request.RecruitmentCreateRequest;
import com.innorun.learningmate.recruitment.dto.request.RecruitmentUpdateRequest;
import com.innorun.learningmate.recruitment.entity.Recruitment;
import com.innorun.learningmate.recruitment.entity.RecruitmentStatus;
import com.innorun.learningmate.recruitment.entity.StudyType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RecruitmentValidator {

    public void validateCreate(RecruitmentCreateRequest request) {
        validateAuthorId(request.getAuthorId());
        validateBasicInfo(
                request.getTitle(),
                request.getDescription(),
                request.getCapacity()
        );
        validateSchedule(
                request.getRecruitmentDeadline(),
                request.getStartAt()
        );
        validateLocation(
                request.getStudyType(),
                request.getOnlinePlatform(),
                request.getOfflineLocation()
        );
    }

    public void validateUpdate(
            Recruitment recruitment,
            RecruitmentUpdateRequest request
    ) {
        validateUpdatable(recruitment);
        validateBasicInfo(
                request.getTitle(),
                request.getDescription(),
                request.getCapacity()
        );
        validateSchedule(
                request.getRecruitmentDeadline(),
                request.getStartAt()
        );
        validateLocation(
                request.getStudyType(),
                request.getOnlinePlatform(),
                request.getOfflineLocation()
        );
    }

    private void validateAuthorId(Long authorId) {
        if (authorId == null) {
            throw new ServiceException(
                    HttpStatus.BAD_REQUEST,
                    "작성자 ID는 필수입니다."
            );
        }
    }

    private void validateBasicInfo(
            String title,
            String description,
            Integer capacity
    ) {
        if (title == null || title.isBlank()) {
            throw new ServiceException(
                    HttpStatus.BAD_REQUEST,
                    "제목은 필수입니다."
            );
        }

        if (title.length() > 100) {
            throw new ServiceException(
                    HttpStatus.BAD_REQUEST,
                    "제목은 100자 이하로 입력해야 합니다."
            );
        }

        if (description == null || description.isBlank()) {
            throw new ServiceException(
                    HttpStatus.BAD_REQUEST,
                    "내용은 필수입니다."
            );
        }

        if (capacity == null || capacity < 1) {
            throw new ServiceException(
                    HttpStatus.BAD_REQUEST,
                    "모집 인원은 1명 이상이어야 합니다."
            );
        }
    }

    private void validateSchedule(
            LocalDateTime recruitmentDeadline,
            LocalDateTime startAt
    ) {
        if (recruitmentDeadline == null || startAt == null) {
            throw new ServiceException(
                    HttpStatus.BAD_REQUEST,
                    "모집 마감일과 시작일은 필수입니다."
            );
        }

        if (!recruitmentDeadline.isBefore(startAt)) {
            throw new ServiceException(
                    HttpStatus.BAD_REQUEST,
                    "모집 마감일은 시작일보다 이전이어야 합니다."
            );
        }
    }

    private void validateLocation(
            StudyType studyType,
            String onlinePlatform,
            String offlineLocation
    ) {
        if (studyType == null) {
            throw new ServiceException(
                    HttpStatus.BAD_REQUEST,
                    "진행 방식은 필수입니다."
            );
        }

        if (studyType == StudyType.ONLINE && isBlank(onlinePlatform)) {
            throw new ServiceException(
                    HttpStatus.BAD_REQUEST,
                    "온라인 플랫폼은 필수입니다."
            );
        }

        if (studyType == StudyType.OFFLINE && isBlank(offlineLocation)) {
            throw new ServiceException(
                    HttpStatus.BAD_REQUEST,
                    "오프라인 장소는 필수입니다."
            );
        }

        if (studyType == StudyType.HYBRID
                && (isBlank(onlinePlatform) || isBlank(offlineLocation))) {
            throw new ServiceException(
                    HttpStatus.BAD_REQUEST,
                    "온·오프라인 장소를 모두 입력해야 합니다."
            );
        }
    }

    private void validateUpdatable(Recruitment recruitment) {
        if (recruitment.getStatus() != RecruitmentStatus.OPEN) {
            throw new ServiceException(
                    HttpStatus.BAD_REQUEST,
                    "모집 중인 공고만 수정할 수 있습니다."
            );
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
