package com.innorun.learningmate.Community.repository;

import com.innorun.learningmate.Community.entity.ForumComment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ForumCommentRepository extends JpaRepository<ForumComment, Long> {

    @Override
    @EntityGraph(attributePaths = {"post", "author", "parent"})
    Optional<ForumComment> findById(Long id);

    @EntityGraph(attributePaths = {"author", "parent"})
    List<ForumComment> findAllByPostIdOrderByCreatedAtAsc(Long postId);

    boolean existsByParentId(Long parentId);

    @Modifying(clearAutomatically = true)
    @Query("delete from ForumComment comment where comment.post.id = :postId and comment.parent is not null")
    void deleteRepliesByPostId(@Param("postId") Long postId);

    @Modifying(clearAutomatically = true)
    @Query("delete from ForumComment comment where comment.post.id = :postId and comment.parent is null")
    void deleteRootCommentsByPostId(@Param("postId") Long postId);
}
