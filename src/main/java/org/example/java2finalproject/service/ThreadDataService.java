package org.example.java2finalproject.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.example.java2finalproject.common.NumCountObject;
import org.example.java2finalproject.common.ErrorsClassify;
import org.example.java2finalproject.common.TagsUtil;
import org.example.java2finalproject.dao.AnswerDataRepository;
import org.example.java2finalproject.dao.CommentDataRepository;
import org.example.java2finalproject.dao.QuestionDataRepository;
import org.example.java2finalproject.entity.AnswerData;
import org.example.java2finalproject.entity.CommentData;
import org.example.java2finalproject.entity.QuestionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.java2finalproject.GetUrlData;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ThreadDataService {
    @Autowired
    QuestionDataRepository questionDataRepository;
    @Autowired
    AnswerDataRepository answerDataRepository;
    @Autowired
    CommentDataRepository commentDataRepository;

    public Optional<QuestionData> getQuestionData(long question_Id) {
        return questionDataRepository.findById(question_Id);
    }
    public List<QuestionData> getAllQuestionData() {
        List<QuestionData> allData = questionDataRepository.findAll();
        allData.sort(Comparator.comparingLong(QuestionData::getQuestionId));
        return allData;
    }
    public QuestionData createQuestionData(QuestionData threadData) {
        return questionDataRepository.save(threadData);
    }
    public int loadData(int dataNum) throws IOException {
        String dataPathPrefix = "ThreadsDataSource/data_";
        String dataPathSuffix = ".json";
        int count=0;
        for(int i=1;i<=dataNum;i++){
            String dataPath = dataPathPrefix + i + dataPathSuffix;
            File file = new File(dataPath);
            if(file.exists()){
                String jsonData = new String(Files.readAllBytes(Paths.get(dataPath)));
                JSONObject jsonObject= JSONUtil.parseObj(jsonData);
                JSONArray itemsTopArray =jsonObject.getJSONArray("items");
                for(int j = 0; j< itemsTopArray.size(); j++){
                    JSONObject itemQuestion= itemsTopArray.getJSONObject(j);
                    count++;
                    questionDataRepository.save(new QuestionData(itemQuestion));
                    if(itemQuestion.get("answers")!=null){
                        JSONArray itemsAnswerArray =itemQuestion.getJSONArray("answers");
                        for(int k = 0; k< itemsAnswerArray.size(); k++){
                            JSONObject itemAnswer= itemsAnswerArray.getJSONObject(k);
                            answerDataRepository.save(new AnswerData(itemAnswer));
                            checkCommentsAndSave(itemAnswer);
                        }
                    }
                    checkCommentsAndSave(itemQuestion);
                }
            }
        }
        return count;
    }

    private void checkCommentsAndSave(JSONObject item) {
        if(item.get("comments")!=null){
            JSONArray itemsCommentArray = item.getJSONArray("comments");
            for(int l = 0; l< itemsCommentArray.size(); l++){
                JSONObject itemComment= itemsCommentArray.getJSONObject(l);
                commentDataRepository.save(new CommentData(itemComment));
            }
        }
    }


    public void deleteAll() {
        questionDataRepository.deleteAll();
    }
    public HashMap<String,Integer> getAllTheTags(){
        TagsUtil tagsUtil=new TagsUtil();
        HashMap<String,Integer>checkTags=new HashMap<>();
        for(QuestionData questionData : getAllQuestionData()){
            String tags= questionData.getTags();
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

    public List<QuestionData>getInterestingData(){
        List<QuestionData>allData= getAllQuestionData();
        List<QuestionData>interestingData=new ArrayList<>();
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
        for(QuestionData questionData :getInterestingData()){
            String tags= questionData.getTags();
            if(tags!=null){
                String[]tagArray=TagsUtil.getTagsArray(tags);
                for(String tag:tagArray){
                    if(interestingDataViewCount.containsKey(tag)){
                        interestingDataViewCount.put(tag,interestingDataViewCount.get(tag)+ questionData.getViewCount());
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
            if(tags.get(TagsUtil.interestingTags[i])!=null) {
                ans[i] = new NumCountObject(TagsUtil.interestingTags[i], tags.get(TagsUtil.interestingTags[i]));
            }
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

    public boolean getThreadsDataFromWeb(int pageNum,int pageSize){
        //先去网上爬数据
        ///2.3/questions?page=1&pagesize=80&fromdate=1672531200&todate=1701388800&order=desc&sort=activity&tagged=java&site=stackoverflow&filter=!9MyMg2q4M.IhkHGnmFKa3xqNMdX)5ZKbrlzn8GdMwwBDKb6BWXZRlcH
        String pagesizeParam="&pagesize=";
        String urlPrefix="https://api.stackexchange.com/2.3/questions?";
        String urlSuffix="&fromdate=1672531200&todate=1701388800&order=desc&sort=activity&tagged=java&site=stackoverflow&filter=!9MyMg2q4M.IhkHGnmFKa3xqNMdX)5ZKbrlzn8GdMwwBDKb6BWXZRlcH";

        pagesizeParam=pagesizeParam+pageSize;
        for(int i=1;i<=pageNum;i++){
            String pageParam="&page=";
            pageParam+=i;
            String url=urlPrefix+pageParam+pagesizeParam+urlSuffix;
            boolean result=GetUrlData.getUrlData(url,i);
            if(!result){
                return false;
            }
        }
        return true;
    }
    //用于判断一个ThreadData中，是否包含三大错误类别
    public HashMap<String,Boolean> errorsMatcher(QuestionData questionData){
        HashMap<String,Boolean>res=new HashMap<>();
        res.put(ErrorsClassify.SyntaxErrorName,false);
        res.put(ErrorsClassify.FatalErrorName,false);
        res.put(ErrorsClassify.ExceptionErrorName,false);
        ErrorsClassify errorsClassify=new ErrorsClassify();
        if(questionData.isAnswered()){
            List<AnswerData> answerDataList =answerDataRepository.findByQuestionId(questionData.getQuestionId());
            loop:for(int i = 0; i< answerDataList.size(); i++){
                List<CommentData> commentDataList =
                        commentDataRepository.findCommentDataByPostIdAndPostType
                                (answerDataList.get(i).getAnswerId(),AnswerData.type);
                for(int j = 0; j< commentDataList.size(); j++){
                    if(errorsClassify.CommentSyntaxErrorMatch(commentDataList.get(j))){
                        res.put(ErrorsClassify.SyntaxErrorName,true);
                    }
                    if(errorsClassify.CommentFatalErrorMatch(commentDataList.get(j))){
                        res.put(ErrorsClassify.FatalErrorName,true);
                    }
                    if(errorsClassify.CommentExceptionMatch(commentDataList.get(j))){
                        res.put(ErrorsClassify.ExceptionErrorName,true);
                    }
                }
                if(errorsClassify.AnswerSyntaxErrorMatch(answerDataList.get(i))){
                    res.put(ErrorsClassify.SyntaxErrorName,true);
                }
                if(errorsClassify.AnswerFatalErrorMatch(answerDataList.get(i))){
                    res.put(ErrorsClassify.FatalErrorName,true);
                }
                if(errorsClassify.AnswerExceptionMatch(answerDataList.get(i))){
                    res.put(ErrorsClassify.ExceptionErrorName,true);
                }
            }
        }
        List<CommentData> commentDataList =
                commentDataRepository.findCommentDataByPostIdAndPostType
                        (questionData.getQuestionId(),QuestionData.type);
        for(int j = 0; j< commentDataList.size(); j++){
            if(errorsClassify.CommentSyntaxErrorMatch(commentDataList.get(j))){
                res.put(ErrorsClassify.SyntaxErrorName,true);
            }
            if(errorsClassify.CommentFatalErrorMatch(commentDataList.get(j))){
                res.put(ErrorsClassify.FatalErrorName,true);
            }
            if(errorsClassify.CommentExceptionMatch(commentDataList.get(j))){
                res.put(ErrorsClassify.ExceptionErrorName,true);
            }
        }
        if(errorsClassify.QuestionSyntaxErrorMatch(questionData)){
            res.put(ErrorsClassify.SyntaxErrorName,true);
        }
        if(errorsClassify.QuestionFatalErrorMatch(questionData)){
            res.put(ErrorsClassify.FatalErrorName,true);
        }
        if(errorsClassify.QuestionExceptionMatch(questionData)){
            res.put(ErrorsClassify.ExceptionErrorName,true);
        }
        return res;
    }
    //用于查询所有的ThreadData内，包含的错误情况：
    public HashMap<String,Integer> getDifferentErrorNumber(){
        List<QuestionData> questionDataList= getAllQuestionData();
        HashMap<String,Integer> bugNumWithinCategory=new HashMap<>();
        bugNumWithinCategory.put(ErrorsClassify.SyntaxErrorName,0);
        bugNumWithinCategory.put(ErrorsClassify.FatalErrorName,0);
        bugNumWithinCategory.put(ErrorsClassify.ExceptionErrorName,0);
        for(int i=0;i<questionDataList.size();i++){
            HashMap<String,Boolean>errors=errorsMatcher(questionDataList.get(i));
            bugNumWithinCategory.put(ErrorsClassify.SyntaxErrorName,
                    bugNumWithinCategory.get(ErrorsClassify.SyntaxErrorName)+
                            (errors.get(ErrorsClassify.SyntaxErrorName)?1:0));
            bugNumWithinCategory.put(ErrorsClassify.FatalErrorName,
                    bugNumWithinCategory.get(ErrorsClassify.FatalErrorName)+
                            (errors.get(ErrorsClassify.FatalErrorName)?1:0));
            bugNumWithinCategory.put(ErrorsClassify.ExceptionErrorName,
                    bugNumWithinCategory.get(ErrorsClassify.ExceptionErrorName)+
                            (errors.get(ErrorsClassify.ExceptionErrorName)?1:0));
        }
        return bugNumWithinCategory;
    }


}
