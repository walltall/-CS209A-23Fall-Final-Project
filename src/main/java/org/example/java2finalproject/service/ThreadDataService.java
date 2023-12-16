package org.example.java2finalproject.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.example.java2finalproject.dao.ThreadRepository;
import org.example.java2finalproject.entity.ThreadsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ThreadDataService {
    @Autowired
    ThreadRepository threadRepository;
    public Optional<ThreadsData> getThreadData(Long threadId) {
        return threadRepository.findById(threadId);
    }
    public List<ThreadsData>getAllThreadData() {
        return threadRepository.findAll();
    }
    public ThreadsData createThreadData(ThreadsData threadData) {
        return threadRepository.save(threadData);
    }
    public int loadData() throws IOException {
        String dataPathPrefix = "result_page_";
        String dataPathSuffix = ".json";
        int dataNum=10;
        int count=0;
        for(int i=1;i<=dataNum;i++){
            String dataPath = dataPathPrefix + i + dataPathSuffix;
            File file = new File(dataPath);
            if(file.exists()){
                String jsonData = new String(Files.readAllBytes(Paths.get(dataPath)));
                JSONObject jsonObject= JSONUtil.parseObj(jsonData);
                JSONArray itemsArray=jsonObject.getJSONArray("items");
                for(int j=0;j< itemsArray.size();j++){
                    count++;
                    ThreadsData threadsData=new ThreadsData((JSONObject) itemsArray.get(j));
                    threadRepository.save(threadsData);
                }
            }
        }
        return count;
    }

    public void deleteAll() {
        threadRepository.deleteAll();
    }
}
