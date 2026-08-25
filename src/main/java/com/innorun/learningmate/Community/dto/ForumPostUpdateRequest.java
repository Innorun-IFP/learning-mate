package com.innorun.learningmate.Community.dto;

import com.innorun.learningmate.Community.entity.ForumBoardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ForumPostUpdateRequest(

        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 150, message = "제목은 150자 이하여야 합니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        String content,

        @NotNull(message = "게시판 유형은 필수입니다.")
        ForumBoardType boardType
) {
}
