package com.innorun.learningmate.recruitment.dto.request;

import com.innorun.learningmate.recruitment.entity.StudyType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RecruitmentCreateRequest {

    private Long authorId;
    private String title;
    private String description;
    private StudyType studyType;
    private String onlinePlatform;
    private String offlineLocation;
    private Integer capacity;
    private LocalDateTime recruitmentDeadline;
    private LocalDateTime startAt;

    public RecruitmentCreateRequest(
            Long authorId,
            String title,
            String description,
            StudyType studyType,
            String onlinePlatform,
            String offlineLocation,
            Integer capacity,
            LocalDateTime recruitmentDeadline,
            LocalDateTime startAt
    ) {
        this.authorId = authorId;
        this.title = title;
        this.description = description;
        this.studyType = studyType;
        this.onlinePlatform = onlinePlatform;
        this.offlineLocation = offlineLocation;
        this.capacity = capacity;
        this.recruitmentDeadline = recruitmentDeadline;
        this.startAt = startAt;
    }
}
