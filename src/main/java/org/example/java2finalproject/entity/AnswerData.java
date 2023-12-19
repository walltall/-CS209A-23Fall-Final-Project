package org.example.java2finalproject.entity;

import cn.hutool.json.JSONObject;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class AnswerData {
   @Id
   private long answerId;
   @Getter
   private long commentCount;
   @Getter
   private int score;
   @Getter
   private long lastActivityDate;
   @Getter
   private long creationDate;
   @Getter
   private long questionId;
   @Getter
   @Lob
   @Column(columnDefinition="TEXT")
   private String bodyMarkdown;
   @Getter
   private String link;
   @Getter
   private String title;
   @Getter
   @Lob
   @Column(columnDefinition="TEXT")
   private String body;

   @Transient
   public static final String type="answer";


    public AnswerData(JSONObject jsonObject){
        this.answerId =jsonObject.getLong("answerId");
        this.commentCount =jsonObject.getLong("commentCount");
        this.score=jsonObject.getInt("score");
        this.lastActivityDate =jsonObject.getLong("lastActivityDate");
        this.creationDate =jsonObject.getLong("creation_date");
        this.questionId =jsonObject.getLong("questionId");
        this.bodyMarkdown =jsonObject.getStr("body_markdown");
        this.link=jsonObject.getStr("link");
        this.title=jsonObject.getStr("title");
        this.body=jsonObject.getStr("body");
    }

    public AnswerData() {

    }

}
