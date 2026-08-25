package com.innorun.learningmate.Community.dto;

import com.innorun.learningmate.Community.entity.ForumComment;

import java.time.LocalDateTime;

public record ForumCommentResponse(
        Long id,
        Long postId,
        Long authorId,
        String authorNickname,
        Long parentCommentId,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ForumCommentResponse from(ForumComment comment) {
        return new ForumCommentResponse(
                comment.getId(),
                comment.getPost().getId(),
                comment.getAuthor().getId(),
                comment.getAuthor().getNickname(),
                comment.getParent() == null ? null : comment.getParent().getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
