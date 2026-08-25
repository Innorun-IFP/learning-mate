package com.innorun.learningmate.studymember.repository;

import com.innorun.learningmate.studymember.entity.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

    List<StudyMember> findAllByStudyGroupIdAndUserDeletedAtIsNull(Long studyGroupId);

    boolean existsByUserIdAndStudyGroupId(Long userId, Long studyGroupId);

    Optional<StudyMember> findByUserIdAndStudyGroupId(
            Long userId,
            Long studyGroupId
    );

    void deleteAllByStudyGroupId(Long studyGroupId);
}
