package com.innorun.learningmate.Community.entity;


import lombok.Getter;

@Getter
public enum ForumBoardType {
    JOB_INFO("취업정보"),
    CAREER_QNA("취업·진로 Q&A"),
    FREE("자유게시판");

    private final String displayName;

    ForumBoardType(String displayName) {
        this.displayName = displayName;
    }

}
