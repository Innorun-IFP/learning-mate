package com.innorun.learningmate.Community.service;

import com.innorun.learningmate.Community.dto.ForumPostCreateRequest;
import com.innorun.learningmate.Community.dto.ForumPostResponse;
import com.innorun.learningmate.Community.dto.ForumPostSummaryResponse;
import com.innorun.learningmate.Community.dto.ForumPostUpdateRequest;
import com.innorun.learningmate.Community.entity.ForumBoardType;
import com.innorun.learningmate.Community.entity.ForumPost;
import com.innorun.learningmate.Community.repository.ForumCommentRepository;
import com.innorun.learningmate.Community.repository.ForumRepository;
import com.innorun.learningmate.user.entity.User;
import com.innorun.learningmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ForumPostService {

    private final ForumRepository forumRepository;
    private final ForumCommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public ForumPostResponse createPost(ForumPostCreateRequest request) {
        User author = getUser(request.authorId());
        ForumPost post = new ForumPost(author, request.title(), request.content(), request.boardType());
        return ForumPostResponse.from(forumRepository.save(post));
    }

    public ForumPostResponse getPost(Long postId) {
        return ForumPostResponse.from(getForumPost(postId));
    }

    public Page<ForumPostSummaryResponse> getPosts(ForumBoardType boardType, Pageable pageable) {
        Page<ForumPost> posts = boardType == null
                ? forumRepository.findAll(pageable)
                : forumRepository.findAllByBoardType(boardType, pageable);
        return posts.map(ForumPostSummaryResponse::from);
    }

    @Transactional
    public ForumPostResponse updatePost(Long postId, ForumPostUpdateRequest request) {
        ForumPost post = getForumPost(postId);
        validateAuthor(post.getAuthor().getId(), request.authorId());
        post.update(request.title(), request.content(), request.boardType());
        return ForumPostResponse.from(post);
    }

    @Transactional
    public void deletePost(Long postId, Long authorId) {
        ForumPost post = getForumPost(postId);
        validateAuthor(post.getAuthor().getId(), authorId);

        commentRepository.deleteRepliesByPostId(postId);
        commentRepository.deleteRootCommentsByPostId(postId);
        forumRepository.delete(post);
    }

    private ForumPost getForumPost(Long postId) {
        return forumRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }

    private void validateAuthor(Long ownerId, Long requesterId) {
        if (!Objects.equals(ownerId, requesterId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "작성자만 게시글을 수정하거나 삭제할 수 있습니다.");
        }
    }
}
