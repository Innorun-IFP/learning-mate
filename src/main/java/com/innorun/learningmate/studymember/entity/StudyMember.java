package com.innorun.learningmate.studymember.entity;

import com.innorun.learningmate.global.entity.BaseEntity;
import com.innorun.learningmate.studygroup.entity.StudyGroup;
import com.innorun.learningmate.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "study_members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_group_id", nullable = false)
    private StudyGroup studyGroup;

    public StudyMember(User user, StudyGroup studyGroup) {
        this.user = user;
        this.studyGroup = studyGroup;
    }
}
