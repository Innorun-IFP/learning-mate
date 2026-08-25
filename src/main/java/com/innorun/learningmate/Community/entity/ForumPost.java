package com.innorun.learningmate.Community.entity;

import com.innorun.learningmate.global.entity.BaseEntity;
import com.innorun.learningmate.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "forum_posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ForumPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(nullable = false, length = 150)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "board_type", nullable = false, length = 30)
    private ForumBoardType boardType;

    public ForumPost(User author, String title, String content, ForumBoardType boardType) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.boardType = boardType;
    }

    public void update(String title, String content, ForumBoardType boardType) {
        this.title = title;
        this.content = content;
        this.boardType = boardType;
    }
}
