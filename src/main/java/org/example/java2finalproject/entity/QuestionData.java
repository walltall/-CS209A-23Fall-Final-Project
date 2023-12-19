package org.example.java2finalproject.entity;

import cn.hutool.json.JSONObject;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class QuestionData {
    @Id
    @Getter
    private long questionId =-1;
    @Getter
    private String tags="";
    @Getter
    private int commentCount =0;
    @Getter
    private boolean isAnswered =false;
    @Getter
    private long viewCount =0;
    @Getter
    private long answerCount =0;
    @Getter
    private int score=0;
    @Getter
    private long lastActivityDate =0;
    @Getter
    private long creationDate =0;
    @Getter
    private long lastEditDate =0;
    @Getter
    @Lob
    @Column(columnDefinition="TEXT")
    private String bodyMarkdown;
    @Getter
    private String link;
    @Getter
    private String title="";
    @Getter
    @Lob
    @Column(columnDefinition="TEXT")
    private String body;
    @Getter
    private long bountyAmount =0;

    @Transient
    public static final String type="question";
    public QuestionData(JSONObject jsonObject){
        this.questionId =jsonObject.getLong("question_id");
        this.tags=jsonObject.getStr("tags");
        this.commentCount =jsonObject.getInt("comment_count");
        this.isAnswered =jsonObject.getBool("is_answered");
        this.viewCount =jsonObject.getLong("view_count");
        this.answerCount =jsonObject.getLong("answer_count");
        this.score=jsonObject.getInt("score");
        this.lastActivityDate =jsonObject.getLong("last_activity_date");
        this.creationDate =jsonObject.getLong("creation_date");
        if(jsonObject.containsKey("last_edit_date")){
            this.lastEditDate =jsonObject.getLong("last_edit_date");
        }
        this.bodyMarkdown =jsonObject.getStr("body_markdown");
        this.link=jsonObject.getStr("link");
        this.title=jsonObject.getStr("title");
        this.body=jsonObject.getStr("body");
        if(jsonObject.containsKey("bounty_amount")){
            this.bountyAmount =jsonObject.getLong("bounty_amount");
        }
    }


    public QuestionData() {

    }
}
