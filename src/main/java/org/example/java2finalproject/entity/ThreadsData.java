package org.example.java2finalproject.entity;

import cn.hutool.json.JSONObject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Entity
@Table(name = "ThreadsData")
public class ThreadsData {
    @Getter
    @Setter
    private String tags;
    @Getter
    @Setter
    private boolean is_answered=false;
    @Getter
    @Setter
    private  long view_count;
    @Getter
    @Setter
    private long answer_count;
    @Getter
    @Setter
    private int score;
    @Getter
    @Setter
    private long accepted_answer_id;
    @Getter
    @Setter
    private long last_activity_date;
    @Getter
    @Setter
    private long creation_date;
    @Id
    @Getter
    @Setter
    private long last_edit_date;
    @Getter
    @Setter
    private long question_id;
    @Getter
    @Setter
    private String content_license;
    @Getter
    @Setter
    private String link;
    @Getter
    @Setter
    private String title;
    public ThreadsData(JSONObject jsonObject){
        this.content_license=jsonObject.getStr("content_license");
        this.link=jsonObject.getStr("link");
        this.last_activity_date=jsonObject.getLong("last_activity_date");
        this.creation_date=jsonObject.getLong("creation_date");
        this.answer_count=jsonObject.getLong("answer_count");
        this.title=jsonObject.getStr("title");
        this.question_id=jsonObject.getLong("question_id");
        this.tags=jsonObject.getStr("tags");
        this.score=jsonObject.getInt("score");
        Object temp=jsonObject.getLong("accepted_answer_id");
        if(temp!=null) {
            this.accepted_answer_id = jsonObject.getLong("accepted_answer_id");
        }else this.accepted_answer_id=-1L;
        this.is_answered=jsonObject.getBool("is_answered");
        this.view_count=jsonObject.getLong("view_count");
        temp=jsonObject.getLong("last_edit_date");
        if(temp!=null) {
            this.last_edit_date = jsonObject.getLong("last_edit_date");
        }else this.last_edit_date=-1L;

    }


    public ThreadsData() {

    }
}
