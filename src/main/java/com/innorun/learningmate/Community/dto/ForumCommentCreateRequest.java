package com.innorun.learningmate.Community.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ForumCommentCreateRequest(
        @NotNull(message = "작성자 ID는 필수입니다.")
        Long authorId,

        Long parentCommentId,

        @NotBlank(message = "댓글 내용은 필수입니다.")
        @Size(max = 2000, message = "댓글은 2000자 이하여야 합니다.")
        String content
) {
}
