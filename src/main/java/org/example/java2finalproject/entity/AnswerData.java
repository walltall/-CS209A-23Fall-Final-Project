package org.example.java2finalproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class AnswerData {
    @Id
    private long answer_id;
    @Setter
    private long question_id;
    @Setter
    private  boolean is_accepted;
    @Setter
    private int score;
    @Setter
    private long last_activity_date;
    @Setter
    private long creation_date;
    @Setter
    private String content_license;
}
