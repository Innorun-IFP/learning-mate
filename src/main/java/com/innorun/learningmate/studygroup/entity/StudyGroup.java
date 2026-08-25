package com.innorun.learningmate.studygroup.entity;

import com.innorun.learningmate.global.entity.BaseEntity;
import com.innorun.learningmate.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "study_groups")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id", nullable = false)
    private User leader;

    public StudyGroup(String name, String description, User leader) {
        this.name = name;
        this.description = description;
        this.leader = leader;
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
