package com.innorun.learningmate.recruitment.entity;

import com.innorun.learningmate.global.entity.BaseEntity;
import com.innorun.learningmate.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "recruitments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruitment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StudyType studyType;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private LocalDateTime recruitmentDeadline;

    @Column(nullable = false)
    private LocalDateTime meetingAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RecruitmentStatus status;

    public Recruitment(
            User author,
            String title,
            String description,
            StudyType studyType,
            String location,
            Integer capacity,
            LocalDateTime recruitmentDeadline,
            LocalDateTime meetingAt
    ) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.studyType = studyType;
        this.location = location;
        this.capacity = capacity;
        this.recruitmentDeadline = recruitmentDeadline;
        this.meetingAt = meetingAt;
        this.status = RecruitmentStatus.OPEN;
    }

    public void close() {
        this.status = RecruitmentStatus.CLOSED;
    }

    public void cancel() {
        this.status = RecruitmentStatus.CANCELLED;
    }
}
