package com.innorun.learningmate.Community.controller;

import com.innorun.learningmate.Community.dto.ForumPostCreateRequest;
import com.innorun.learningmate.Community.dto.ForumPostResponse;
import com.innorun.learningmate.Community.dto.ForumPostSummaryResponse;
import com.innorun.learningmate.Community.dto.ForumPostUpdateRequest;
import com.innorun.learningmate.Community.entity.ForumBoardType;
import com.innorun.learningmate.Community.service.ForumPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/forum/posts")
@RequiredArgsConstructor
public class ForumPostController {

    private final ForumPostService forumPostService;

    @PostMapping
    public ResponseEntity<ForumPostResponse> createPost(@Valid @RequestBody ForumPostCreateRequest request) {
        ForumPostResponse response = forumPostService.createPost(request);
        return ResponseEntity.created(URI.create("/api/forum/posts/" + response.id())).body(response);
    }

    @GetMapping("/{postId}")
    public ForumPostResponse getPost(@PathVariable Long postId) {
        return forumPostService.getPost(postId);
    }

    @GetMapping
    public Page<ForumPostSummaryResponse> getPosts(
            @RequestParam(required = false) ForumBoardType boardType,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return forumPostService.getPosts(boardType, pageable);
    }

    @PutMapping("/{postId}")
    public ForumPostResponse updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody ForumPostUpdateRequest request
    ) {
        return forumPostService.updatePost(postId, request);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @RequestParam Long authorId
    ) {
        forumPostService.deletePost(postId, authorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
