package com.innorun.learningmate.studygroup.dto;

import lombok.Getter;

@Getter
public class StudyGroupCreateResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final Long leaderId;
    private final String leaderNickname;

    public StudyGroupCreateResponse(
            Long id,
            String name,
            String description,
            Long leaderId,
            String leaderNickname
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.leaderId = leaderId;
        this.leaderNickname = leaderNickname;
    }
}
