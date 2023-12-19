package org.example.java2finalproject.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.example.java2finalproject.GetUrlData;
import org.example.java2finalproject.dao.AnswerDataRepository;
import org.example.java2finalproject.entity.AnswerData;
import org.example.java2finalproject.entity.QuestionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class AnswerDataService {
    @Autowired
    AnswerDataRepository answerDataRepository;



    public List<AnswerData>getAllAnswerData(){
        return answerDataRepository.findAll();
    }
//    public AnswerData getAnswerDataByAnswerId(Long answerId){
//        return answerDataRepository.findByAnswer_id(answerId);
//    }
//    public List<AnswerData>getAnswerDataByQuestionId(Long questionId){
//        return answerDataRepository.findByQuestionId(questionId);
//    }
}
