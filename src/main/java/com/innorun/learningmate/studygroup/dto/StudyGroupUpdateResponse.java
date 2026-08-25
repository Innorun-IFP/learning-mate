package com.innorun.learningmate.studygroup.dto;

import lombok.Getter;

@Getter
public class StudyGroupUpdateResponse {

    private final String name;
    private final String description;

    public StudyGroupUpdateResponse(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
