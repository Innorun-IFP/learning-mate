package com.innorun.learningmate.Community.repository;

import com.innorun.learningmate.Community.entity.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<ForumPost, Long> {
}
