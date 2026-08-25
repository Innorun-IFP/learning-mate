package com.innorun.learningmate.Community.controller;

import com.innorun.learningmate.Community.dto.ForumCommentCreateRequest;
import com.innorun.learningmate.Community.dto.ForumCommentResponse;
import com.innorun.learningmate.Community.dto.ForumCommentUpdateRequest;
import com.innorun.learningmate.Community.service.ForumCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/forum")
@RequiredArgsConstructor
public class ForumCommentController {

    private final ForumCommentService forumCommentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ForumCommentResponse> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody ForumCommentCreateRequest request
    ) {
        ForumCommentResponse response = forumCommentService.createComment(postId, request);
        URI location = URI.create("/api/forum/comments/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<ForumCommentResponse> getComments(@PathVariable Long postId) {
        return forumCommentService.getComments(postId);
    }

    @GetMapping("/comments/{commentId}")
    public ForumCommentResponse getComment(@PathVariable Long commentId) {
        return forumCommentService.getComment(commentId);
    }

    @PutMapping("/comments/{commentId}")
    public ForumCommentResponse updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody ForumCommentUpdateRequest request
    ) {
        return forumCommentService.updateComment(commentId, request);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @RequestParam Long authorId
    ) {
        forumCommentService.deleteComment(commentId, authorId);
        return ResponseEntity.noContent().build();
    }
}
