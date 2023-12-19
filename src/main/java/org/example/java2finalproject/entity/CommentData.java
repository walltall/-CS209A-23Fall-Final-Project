package org.example.java2finalproject.entity;

import cn.hutool.json.JSONObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import lombok.Getter;


@Entity
public class CommentData {
    @Id
    private long commentId =-1L;
    @Getter
    private int score;
    @Getter
    private String postType ="";
    @Getter
    private long creationDate =0L;
    @Getter
    private long postId =0L;
    @Getter
    @Lob
    @Column(columnDefinition="TEXT")
    private String bodyMarkdown ="";
    @Getter
    private String link="";
    @Getter
    @Lob
    @Column(columnDefinition="TEXT")
    private String body="";
    public CommentData(JSONObject jsonObject){
        this.commentId = jsonObject.getLong("comment_id");
        this.score = jsonObject.getInt("score");
        this.postType = jsonObject.getStr("post_type");
        this.creationDate = jsonObject.getLong("creation_date");
        this.postId = jsonObject.getLong("post_id");
        this.bodyMarkdown = jsonObject.getStr("body_markdown");
        this.link = jsonObject.getStr("link");
        this.body = jsonObject.getStr("body");
    }

    public CommentData() {

    }
}
