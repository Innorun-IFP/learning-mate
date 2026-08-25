package com.innorun.learningmate.Community.service;

import com.innorun.learningmate.Community.dto.ForumCommentCreateRequest;
import com.innorun.learningmate.Community.dto.ForumCommentResponse;
import com.innorun.learningmate.Community.dto.ForumCommentUpdateRequest;
import com.innorun.learningmate.Community.entity.ForumComment;
import com.innorun.learningmate.Community.entity.ForumPost;
import com.innorun.learningmate.Community.repository.ForumCommentRepository;
import com.innorun.learningmate.Community.repository.ForumPostRepository;
import com.innorun.learningmate.user.entity.User;
import com.innorun.learningmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ForumCommentService {

    private final ForumPostRepository forumPostRepository;
    private final ForumCommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public ForumCommentResponse createComment(Long postId, ForumCommentCreateRequest request) {
        ForumPost post = getForumPost(postId);
        User author = getUser(request.authorId());
        ForumComment parent = getAndValidateParent(postId, request.parentCommentId());

        ForumComment comment = new ForumComment(post, author, parent, request.content());
        return ForumCommentResponse.from(commentRepository.save(comment));
    }

    public List<ForumCommentResponse> getComments(Long postId) {
        if (!forumPostRepository.existsById(postId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다.");
        }
        return commentRepository.findAllByPostIdOrderByCreatedAtAsc(postId).stream()
                .map(ForumCommentResponse::from)
                .toList();
    }

    public ForumCommentResponse getComment(Long commentId) {
        return ForumCommentResponse.from(findComment(commentId));
    }

    @Transactional
    public ForumCommentResponse updateComment(Long commentId, ForumCommentUpdateRequest request) {
        ForumComment comment = findComment(commentId);
        validateAuthor(comment.getAuthor().getId(), request.authorId());
        comment.update(request.content());
        return ForumCommentResponse.from(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, Long authorId) {
        ForumComment comment = findComment(commentId);
        validateAuthor(comment.getAuthor().getId(), authorId);
        if (commentRepository.existsByParentId(commentId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "답글이 있는 댓글은 삭제할 수 없습니다.");
        }
        commentRepository.delete(comment);
    }

    private ForumComment getAndValidateParent(Long postId, Long parentCommentId) {
        if (parentCommentId == null) {
            return null;
        }

        ForumComment parent = findComment(parentCommentId);
        if (!Objects.equals(parent.getPost().getId(), postId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "같은 게시글의 댓글에만 답글을 작성할 수 있습니다.");
        }
        if (parent.isReply()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "답글에는 추가 답글을 작성할 수 없습니다.");
        }
        return parent;
    }

    private ForumPost getForumPost(Long postId) {
        return forumPostRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."));
    }

    private ForumComment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }

    private void validateAuthor(Long ownerId, Long requesterId) {
        if (!Objects.equals(ownerId, requesterId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "작성자만 댓글을 수정하거나 삭제할 수 있습니다.");
        }
    }
}
