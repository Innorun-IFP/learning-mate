package com.innorun.learningmate.Community.dto;

import com.innorun.learningmate.Community.entity.ForumBoardType;
import com.innorun.learningmate.Community.entity.ForumPost;

import java.time.LocalDateTime;

public record ForumPostResponse(
        Long id,
        Long authorId,
        String authorNickname,
        String title,
        String content,
        ForumBoardType boardType,
        String boardDisplayName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ForumPostResponse from(ForumPost post) {
        return new ForumPostResponse(
                post.getId(),
                post.getAuthor().getId(),
                post.getAuthor().getNickname(),
                post.getTitle(),
                post.getContent(),
                post.getBoardType(),
                post.getBoardType().getDisplayName(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
