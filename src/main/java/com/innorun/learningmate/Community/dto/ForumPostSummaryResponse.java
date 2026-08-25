package com.innorun.learningmate.Community.dto;

import com.innorun.learningmate.Community.entity.ForumBoardType;
import com.innorun.learningmate.Community.entity.ForumPost;

import java.time.LocalDateTime;

public record ForumPostSummaryResponse(
        Long id,
        Long authorId,
        String authorNickname,
        String title,
        ForumBoardType boardType,
        String boardDisplayName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ForumPostSummaryResponse from(ForumPost post) {
        return new ForumPostSummaryResponse(
                post.getId(),
                post.getAuthor().getId(),
                post.getAuthor().getNickname(),
                post.getTitle(),
                post.getBoardType(),
                post.getBoardType().getDisplayName(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
