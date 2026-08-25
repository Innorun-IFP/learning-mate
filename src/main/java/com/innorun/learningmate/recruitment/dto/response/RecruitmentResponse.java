package com.innorun.learningmate.recruitment.dto.response;

import com.innorun.learningmate.recruitment.entity.Recruitment;
import com.innorun.learningmate.recruitment.entity.RecruitmentStatus;
import com.innorun.learningmate.recruitment.entity.StudyType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RecruitmentResponse {

    private Long id;
    private Long authorId;
    private String authorNickname;
    private String title;
    private String description;
    private StudyType studyType;
    private String onlinePlatform;
    private String offlineLocation;
    private Integer capacity;
    private LocalDateTime recruitmentDeadline;
    private LocalDateTime startAt;
    private RecruitmentStatus status;

    public RecruitmentResponse(Recruitment recruitment) {
        this.id = recruitment.getId();
        this.authorId = recruitment.getAuthor().getId();
        this.authorNickname = recruitment.getAuthor().getNickname();
        this.title = recruitment.getTitle();
        this.description = recruitment.getDescription();
        this.studyType = recruitment.getStudyType();
        this.onlinePlatform = recruitment.getOnlinePlatform();
        this.offlineLocation = recruitment.getOfflineLocation();
        this.capacity = recruitment.getCapacity();
        this.recruitmentDeadline = recruitment.getRecruitmentDeadline();
        this.startAt = recruitment.getStartAt();
        this.status = recruitment.getStatus();
    }
}
