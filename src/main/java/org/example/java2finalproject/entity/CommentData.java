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
    private long comment_id=-1L;
    @Getter
    private int score;
    @Getter
    private String post_type="";
    @Getter
    private long creation_date=0L;
    @Getter
    private long post_id=0L;
    @Getter
    @Lob
    @Column(columnDefinition="TEXT")
    private String body_markdown="";
    @Getter
    private String link="";
    @Getter
    @Lob
    @Column(columnDefinition="TEXT")
    private String body="";
    public CommentData(JSONObject jsonObject){
        this.comment_id = jsonObject.getLong("comment_id");
        this.score = jsonObject.getInt("score");
        this.post_type = jsonObject.getStr("post_type");
        this.creation_date = jsonObject.getLong("creation_date");
        this.post_id = jsonObject.getLong("post_id");
        this.body_markdown = jsonObject.getStr("body_markdown");
        this.link = jsonObject.getStr("link");
        this.body = jsonObject.getStr("body");
    }

    public CommentData() {

    }
}
