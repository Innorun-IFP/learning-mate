package com.innorun.learningmate.Community.entity;


import com.innorun.learningmate.global.entity.BaseEntity;
import com.innorun.learningmate.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(
        name = "forum_comments"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ForumComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private ForumPost post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private ForumComment parent;

    @Column(nullable = false, length = 2000)
    private String content;



    public ForumComment(ForumPost post, User author, String content) {
        this(post, author, null, content);
    }

    public ForumComment(ForumPost post, User author, ForumComment parent, String content) {
        this.post = post;
        this.author = author;
        this.parent = parent;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }

    public boolean isReply() {
        return parent != null;
    }


}
