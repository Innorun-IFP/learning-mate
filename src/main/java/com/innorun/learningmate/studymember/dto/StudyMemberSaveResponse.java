package com.innorun.learningmate.studymember.dto;

import lombok.Getter;

@Getter
public class StudyMemberSaveResponse {

    private final Long id;
    private final Long userId;
    private final String nickname;
    private final Long studyGroupId;

    public StudyMemberSaveResponse(
            Long id,
            Long userId,
            String nickname,
            Long studyGroupId
    ) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.studyGroupId = studyGroupId;
    }
}
