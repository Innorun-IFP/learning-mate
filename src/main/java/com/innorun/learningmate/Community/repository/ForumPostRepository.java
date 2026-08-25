package com.innorun.learningmate.Community.repository;

import com.innorun.learningmate.Community.entity.ForumPost;
import com.innorun.learningmate.Community.entity.ForumBoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long> {

    @Override
    @EntityGraph(attributePaths = "author")
    Optional<ForumPost> findById(Long id);

    @Override
    @EntityGraph(attributePaths = "author")
    Page<ForumPost> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "author")
    Page<ForumPost> findAllByBoardType(ForumBoardType boardType, Pageable pageable);
}
