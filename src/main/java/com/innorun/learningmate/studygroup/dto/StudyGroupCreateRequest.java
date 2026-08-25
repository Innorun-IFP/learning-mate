package com.innorun.learningmate.studygroup.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class StudyGroupCreateRequest {

    @NotBlank(message = "스터디 그룹 이름은 필수입니다.")
    @Size(max = 100, message = "스터디 그룹 이름은 100자 이하로 입력해야 합니다.")
    private String name;

    @NotBlank(message = "스터디 그룹 설명은 필수입니다.")
    private String description;
}
