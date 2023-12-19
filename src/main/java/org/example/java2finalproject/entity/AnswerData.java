package org.example.java2finalproject.entity;

import cn.hutool.json.JSONObject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Entity
public class AnswerData {
   @Id
   private long answer_id;
   @Getter
   private long comment_count;
   @Getter
   private int score;
   @Getter
   private long last_activity_date;
   @Getter
   private long creation_date;
   @Getter
   private long question_id;
   @Getter
   @Lob
   @Column(columnDefinition="TEXT")
   private String body_markdown;
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
        this.answer_id=jsonObject.getLong("answer_id");
        this.comment_count=jsonObject.getLong("comment_count");
        this.score=jsonObject.getInt("score");
        this.last_activity_date=jsonObject.getLong("last_activity_date");
        this.creation_date=jsonObject.getLong("creation_date");
        this.question_id=jsonObject.getLong("question_id");
        this.body_markdown=jsonObject.getStr("body_markdown");
        this.link=jsonObject.getStr("link");
        this.title=jsonObject.getStr("title");
        this.body=jsonObject.getStr("body");
    }

    public AnswerData() {

    }

}
