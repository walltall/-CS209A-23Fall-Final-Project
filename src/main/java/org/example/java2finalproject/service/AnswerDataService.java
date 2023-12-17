package org.example.java2finalproject.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Setter;
import org.example.java2finalproject.GetAnswerData;
import org.example.java2finalproject.dao.AnswerDataRepository;
import org.example.java2finalproject.entity.AnswerData;
import org.example.java2finalproject.entity.ThreadsData;
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
    @Autowired
    ThreadDataService threadDataService;
    public boolean getAnswerData(){
        //先去网上爬数据
        List<ThreadsData> allData=threadDataService.getAllThreadData();
        String urlPrefix="https://api.stackexchange.com/2.3/questions/";
        String urlSuffix="/answers?order=desc&sort=activity&site=stackoverflow";
        for(int i=85;i<allData.size();i++){
            String url=urlPrefix+allData.get(i).getQuestion_id()+urlSuffix;
            GetAnswerData.getUrlData(url,i);
        }
        return true;

    }
    public int loadAnswerData()throws IOException {
        String dataPathPrefix = "AnswerDataSource/data_";
        String dataPathSuffix = ".json";
        int dataNum=84;
        int count=0;
        for(int i=0;i<=dataNum;i++){
            String dataPath = dataPathPrefix + i + dataPathSuffix;
            File file = new File(dataPath);
            if(file.exists()){
                String jsonData = new String(Files.readAllBytes(Paths.get(dataPath)));
                JSONObject jsonObject= JSONUtil.parseObj(jsonData);
                JSONArray itemsArray=jsonObject.getJSONArray("items");
                for(int j=0;j< itemsArray.size();j++){
                    count++;
                    AnswerData answerData =new AnswerData((JSONObject) itemsArray.get(j));
                    answerDataRepository.save(answerData);
                }
            }
        }
        return count;
    }
}
