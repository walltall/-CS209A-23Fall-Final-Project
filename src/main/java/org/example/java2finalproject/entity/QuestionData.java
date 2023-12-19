package org.example.java2finalproject.entity;

import cn.hutool.json.JSONObject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class QuestionData {
    @Id
    @Getter
    private long question_id=-1;
    @Getter
    private String tags="";
    @Getter
    private int comment_count=0;
    @Getter
    private boolean is_answered=false;
    @Getter
    private long view_count=0;
    @Getter
    private long answer_count=0;
    @Getter
    private int score=0;
    @Getter
    private long last_activity_date=0;
    @Getter
    private long creation_date=0;
    @Getter
    private long last_edit_date=0;
    @Getter
    @Lob
    @Column(columnDefinition="TEXT")
    private String body_markdown;
    @Getter
    private String link;
    @Getter
    private String title="";
    @Getter
    @Lob
    @Column(columnDefinition="TEXT")
    private String body;
    @Getter
    private long bounty_amount=0;

    @Transient
    public static final String type="question";
    public QuestionData(JSONObject jsonObject){
        this.question_id=jsonObject.getLong("question_id");
        this.tags=jsonObject.getStr("tags");
        this.comment_count=jsonObject.getInt("comment_count");
        this.is_answered=jsonObject.getBool("is_answered");
        this.view_count=jsonObject.getLong("view_count");
        this.answer_count=jsonObject.getLong("answer_count");
        this.score=jsonObject.getInt("score");
        this.last_activity_date=jsonObject.getLong("last_activity_date");
        this.creation_date=jsonObject.getLong("creation_date");
        if(jsonObject.containsKey("last_edit_date")){
            this.last_edit_date=jsonObject.getLong("last_edit_date");
        }
        this.body_markdown=jsonObject.getStr("body_markdown");
        this.link=jsonObject.getStr("link");
        this.title=jsonObject.getStr("title");
        this.body=jsonObject.getStr("body");
        if(jsonObject.containsKey("bounty_amount")){
            this.bounty_amount=jsonObject.getLong("bounty_amount");
        }
    }


    public QuestionData() {

    }
}
