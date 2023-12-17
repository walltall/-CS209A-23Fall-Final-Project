package org.example.java2finalproject.entity;

import cn.hutool.json.JSONObject;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class AnswerData {
    @Id
    @Getter
    private long answer_id;
    @Getter
    @Setter
    private long question_id;
    @Getter
    @Setter
    private  boolean is_accepted;
    @Getter
    @Setter
    private int score;
    @Getter
    @Setter
    private long last_activity_date;
    @Getter
    @Setter
    private long creation_date;
    @Getter
    @Setter
    private String content_license;

    public AnswerData(JSONObject jsonObject){
        this.is_accepted=jsonObject.getBool("is_accepted");
        this.score=jsonObject.getInt("score");
        this.last_activity_date=jsonObject.getLong("last_activity_date");
        this.creation_date=jsonObject.getLong("creation_date");
        this.content_license=jsonObject.getStr("content_license");
        this.answer_id=jsonObject.getLong("answer_id");
        this.question_id=jsonObject.getLong("question_id");
        this.creation_date=jsonObject.getLong("creation_date");
    }

    public AnswerData() {

    }
}
