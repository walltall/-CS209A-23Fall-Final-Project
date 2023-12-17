package org.example.java2finalproject.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.example.java2finalproject.common.NumCountObject;
import org.example.java2finalproject.common.TagsUtil;
import org.example.java2finalproject.dao.ThreadRepository;
import org.example.java2finalproject.entity.ThreadsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.java2finalproject.GetAnswerData;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ThreadDataService {
    @Autowired
    ThreadRepository threadRepository;
    public Optional<ThreadsData> getThreadData(Long threadId) {
        return threadRepository.findById(threadId);
    }
    public List<ThreadsData>getAllThreadData() {
        List<ThreadsData> allData = threadRepository.findAll();
        allData.sort(Comparator.comparingLong(ThreadsData::getQuestion_id));
        return allData;
    }
    public ThreadsData createThreadData(ThreadsData threadData) {
        return threadRepository.save(threadData);
    }
    public int loadData() throws IOException {
        String dataPathPrefix = "result_page_";
        String dataPathSuffix = ".json";
        int dataNum=15;
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
    public boolean getAnswerData(){
        //先去网上爬数据
        List<ThreadsData>allData=getAllThreadData();
        String urlPrefix="https://api.stackexchange.com/2.3/questions/";
        String urlSuffix="/answers?order=desc&sort=activity&site=stackoverflow";
        for(int i=85;i<allData.size();i++){
            String url=urlPrefix+allData.get(i).getQuestion_id()+urlSuffix;
            GetAnswerData.getUrlData(url,i);
        }
        return true;

    }

    public void deleteAll() {
        threadRepository.deleteAll();
    }
    public HashMap<String,Integer> getAllTheTags(){
        TagsUtil tagsUtil=new TagsUtil();
        HashMap<String,Integer>checkTags=new HashMap<>();
        for(ThreadsData threadsData:getAllThreadData()){
            String tags=threadsData.getTags();
            if(tags!=null){
                String[]tagArray=TagsUtil.getTagsArray(tags);
                for(String tag:tagArray){
                    if(checkTags.containsKey(tag)){
                        checkTags.put(tag,checkTags.get(tag)+1);
                    }else{
                        checkTags.put(tag,1);
                    }
                }
            }
        }
        return checkTags;
    }
    public List<ThreadsData>getInterestingData(){
        List<ThreadsData>allData=getAllThreadData();
        List<ThreadsData>interestingData=new ArrayList<>();
        for(int i=0;i<allData.size();i++){
            for(int j=0;j<TagsUtil.interestingTags.length;j++){
                if(allData.get(i).getTags().contains("\""+TagsUtil.interestingTags[j]+"\"")){
                    interestingData.add(allData.get(i));
                    break;
                }
            }
        }
        return interestingData;
    }
    public HashMap<String,Long> getInterestingDataViewCount(){
        HashMap<String,Long>interestingDataViewCount=new HashMap<>();
        for(int i=0;i<TagsUtil.interestingTags.length;i++){
            interestingDataViewCount.put(TagsUtil.interestingTags[i],0L);
        }
        for(ThreadsData threadsData:getInterestingData()){
            String tags=threadsData.getTags();
            if(tags!=null){
                String[]tagArray=TagsUtil.getTagsArray(tags);
                for(String tag:tagArray){
                    if(interestingDataViewCount.containsKey(tag)){
                        interestingDataViewCount.put(tag,interestingDataViewCount.get(tag)+threadsData.getView_count());
                    }
                }
            }
        }
        return interestingDataViewCount;
    }
    public NumCountObject[]getInterestingTagsCount(){
        HashMap<String,Integer> tags=getAllTheTags();
        NumCountObject[] ans=new NumCountObject[TagsUtil.interestingTags.length];
        for(int i=0;i<TagsUtil.interestingTags.length;i++){
            ans[i] = new NumCountObject(TagsUtil.interestingTags[i], tags.get(TagsUtil.interestingTags[i]));
        }
        return ans;
    }
    public HashMap<String,Long>getInterestingDataAverageViewCount(){
        HashMap<String,Long>getInterestingDataAverageViewCount=new HashMap<>();
        HashMap<String,Long>interestingDataViewCount=getInterestingDataViewCount();
        NumCountObject[]interestingTagsCount=getInterestingTagsCount();
        for(int i=0;i<interestingTagsCount.length;i++){
            getInterestingDataAverageViewCount.put(interestingTagsCount[i].content.toString()
                    ,interestingDataViewCount.get(interestingTagsCount[i].content.toString())/
                            interestingTagsCount[i].num);
        }
        return getInterestingDataAverageViewCount;
    }




}
