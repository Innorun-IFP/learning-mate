package com.innorun.learningmate.user.repository;

import com.innorun.learningmate.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
