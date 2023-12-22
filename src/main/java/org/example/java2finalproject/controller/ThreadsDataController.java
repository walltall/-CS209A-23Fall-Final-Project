package org.example.java2finalproject.controller;

import org.example.java2finalproject.common.*;
import org.example.java2finalproject.entity.QuestionData;
import org.example.java2finalproject.service.AnswerDataService;
import org.example.java2finalproject.service.ThreadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Threads")
public class ThreadsDataController {
    @Autowired
    private ThreadDataService threadDataService;
    @Autowired
    private AnswerDataService answerDataService;
    @GetMapping("/importDataToDatabase")
    public Result importDataToDatabase() throws IOException {
        int count=threadDataService.loadData(14);
        return Result.success("已导入"+count+"条数据");
    }
    @GetMapping("/getThreadDataFromTheWeb")
    public Result getThreadData() {
        boolean t= threadDataService.getThreadsDataFromWeb(15,80);
        if(t) {
            return Result.success(t);
        }
        else return Result.fail(t);
    }
//

    @GetMapping("/getAllData")
    public Result getAllData() {
        return Result.success(threadDataService.getAllQuestionData());
    }
    @DeleteMapping("/deleteAllData")
    public Result deleteAllData() {
        threadDataService.deleteAll();
        return Result.success();
    }
    @GetMapping("/checkAllTags")
    public Result checkAllTags() {
        HashMap<String,Integer> tags=threadDataService.getAllTheTags();
        List<NumCountObject> ans=new ArrayList<>();
        //遍历tags
        int pos=0;
        for(String key:tags.keySet()){
            ans.add(new NumCountObject(key,tags.get(key)));
        }
        ans.sort((o1,o2)->{
            if(o1.num>o2.num){
                return -1;
            }else if(o1.num<o2.num){
                return 1;
            }else{
                return 0;
            }
        });
        return Result.success(ans);
    }
    @GetMapping("/checkInterestingTags")
    public Result checkInterestingTags() {
       NumCountObject[]ans=threadDataService.getInterestingTagsCount();
        return Result.success(ans);
    }
    @GetMapping("/checkInterestingData")
    public Result checkInterestingData() {
        List<QuestionData> ans=threadDataService.getInterestingData();
        return Result.success(ans);
    }
    @GetMapping("/checkInterestingDataViewCount")
    public Result checkInterestingDataViewCount() {
        HashMap<String,Long> ans=threadDataService.getInterestingDataViewCount();
        return Result.success(ans);
    }
    @GetMapping("/checkInterestingDataAverageViewCount")
    public Result checkInterestingDataAverageViewCount() {
        HashMap<String,Long> ans=threadDataService.getInterestingDataAverageViewCount();
        NumCountObject[]res=new NumCountObject[TagsUtil.interestingTags.length];
        for(int i=0;i<TagsUtil.interestingTags.length;i++){
            res[i]=new NumCountObject(TagsUtil.interestingTags[i],ans.get(TagsUtil.interestingTags[i]));
        }
        Arrays.sort(res);
        return Result.success(res);
    }
    @GetMapping("/answerRate")
    public Result answerRate() {
        List<QuestionData> questionData =threadDataService.getAllQuestionData();
        int count=0;
        for(int i = 0; i< questionData.size(); i++){
            if(questionData.get(i).isAnswered()){
                count++;
            }
        }
        double res=count/(double)questionData.size();
        res=res*100;
        return Result.success(res+"%");
    }
    @GetMapping("/checkInterestingDataAnswerRate")
    public Result checkInterestingDataAnswerRate() {
        List<QuestionData> questionData =threadDataService.getInterestingData();
        HashMap<String,Integer> interestingMap =new HashMap<>();
        HashMap<String,Double> resRate=new HashMap<>();
        for(int i=0;i< TagsUtil.interestingTags.length;i++){
            for(int j=0;j<questionData.size();j++){
                if(questionData.get(j).getTags().contains(TagsUtil.interestingTags[i])){
                    interestingMap.put(TagsUtil.interestingTags[i], interestingMap.getOrDefault(TagsUtil.interestingTags[i],0)+1);
                    if(questionData.get(j).isAnswered()){
                        resRate.put(TagsUtil.interestingTags[i],resRate.getOrDefault(TagsUtil.interestingTags[i],0.0)+1.0);
                    }
                }
            }
            //得到百分数并保留两位小数
            String key=TagsUtil.interestingTags[i];
            resRate.put(key,resRate.getOrDefault(key,0.0)*100/interestingMap.get(key));
            resRate.put(key,Double.parseDouble(String.format("%.2f",resRate.get(key))));
        }
        //将resRate中的百分数变为string，存在ans中
        PercentObject[]ans=new PercentObject[TagsUtil.interestingTags.length];
        for(int i=0;i<TagsUtil.interestingTags.length;i++){
            ans[i]=new PercentObject(TagsUtil.interestingTags[i],resRate.get(TagsUtil.interestingTags[i]));
        }
        Arrays.sort(ans);
        return Result.success(ans);
    }

    @GetMapping("/getDifferentErrorNumber")
    public Result getDifferentErrorNumber() {
        HashMap<String,Integer> res=threadDataService.getDifferentErrorNumber();
        NumCountObject[]ans=new NumCountObject[3];
        ans[0]=new NumCountObject(ErrorsClassify.SyntaxErrorName,res.get(ErrorsClassify.SyntaxErrorName));
        ans[1]=new NumCountObject(ErrorsClassify.ExceptionErrorName,res.get(ErrorsClassify.ExceptionErrorName));
        ans[2]=new NumCountObject(ErrorsClassify.FatalErrorName,res.get(ErrorsClassify.FatalErrorName));
        return Result.success(threadDataService.getDifferentErrorNumber());
    }
    @GetMapping("/getDifferentErrorViewCount")
    public Result getDifferentErrorViewCount() {
        HashMap<String,Long> res=threadDataService.getDifferentErrorViewCount();
        NumCountObject[]ans=new NumCountObject[3];
        ans[0]=new NumCountObject(ErrorsClassify.SyntaxErrorName,res.get(ErrorsClassify.SyntaxErrorName));
        ans[1]=new NumCountObject(ErrorsClassify.ExceptionErrorName,res.get(ErrorsClassify.ExceptionErrorName));
        ans[2]=new NumCountObject(ErrorsClassify.FatalErrorName,res.get(ErrorsClassify.FatalErrorName));
        return Result.success(threadDataService.getDifferentErrorViewCount());
    }

    @GetMapping("/getFatalErrorNumber")
    public Result getFatalErrorNumber() {
        long[][]ans=threadDataService.getAimedTopicRelationships(ErrorsClassify.FatalErrorArray);
        NumCountObject[]res=new NumCountObject[ans.length];
        for(int i=0;i<ans.length;i++){
            res[i]=new NumCountObject(ErrorsClassify.FatalErrorArray[i],ans[i][i]);//res[i]=ans[i][i]
        }
        Arrays.sort(res);
        return Result.success(res);
    }
    @GetMapping("/getExceptionNumber")
    public Result getExceptionNumber() {
        long[][]ans=threadDataService.getAimedTopicRelationships(ErrorsClassify.ExceptionArray);
        NumCountObject[]res=new NumCountObject[ans.length];
        for(int i=0;i<ans.length;i++){
            res[i]=new NumCountObject(ErrorsClassify.ExceptionArray[i],ans[i][i]);
        }
        Arrays.sort(res);
        return Result.success(res);
    }
    @GetMapping("/getUserParseNumber")
    public Result getUserParseNumber(@RequestParam String parse) {
        if(parse.isEmpty()){
            return Result.fail("输入不能为空！");
        }
        long[][]ans=threadDataService.getAimedTopicRelationships(new String[]{parse});
        HashMap<String,Long>res=new HashMap<>();
        res.put(parse,ans[0][0]);
        return Result.success(res);
    }

    @GetMapping("/getFatalErrorViewCount")
    public Result getFatalErrorViewCount() {
        long[][]ans=threadDataService.getAimedTopicRelationViewCount(ErrorsClassify.FatalErrorArray);
        NumCountObject[]res=new NumCountObject[ans.length];
        for(int i=0;i<ans.length;i++){
            res[i]=new NumCountObject(ErrorsClassify.FatalErrorArray[i],ans[i][i]);
        }
        Arrays.sort(res);
        return Result.success(res);
    }
    @GetMapping("/getExceptionViewCount")
    public Result getExceptionViewCount() {
        long[][]ans=threadDataService.getAimedTopicRelationViewCount(ErrorsClassify.ExceptionArray);
        NumCountObject[]res=new NumCountObject[ans.length];
        for(int i=0;i<ans.length;i++){
            res[i]=new NumCountObject(ErrorsClassify.ExceptionArray[i],ans[i][i]);
        }
        return Result.success(res);
    }
    @GetMapping("/getUserParseViewCount")
    public Result getUserParseViewCount(@RequestParam String parse) {
        if(parse.isEmpty()){
            return Result.fail("输入不能为空！");
        }
        long[][]ans=threadDataService.getAimedTopicRelationViewCount(new String[]{parse});
        HashMap<String,Long>res=new HashMap<>();
        res.put(parse,ans[0][0]);
        return Result.success(res);
    }
    @GetMapping("/getRelationTopic")
    public Result getRelationTopic(@RequestParam String parse) {
        if(parse.isEmpty()){
            return Result.fail("输入不能为空！");
        }
        String[]checkString=new String[TagsUtil.interestingTags.length+1];
        for(int i=0;i<TagsUtil.interestingTags.length;i++){
            checkString[i]=TagsUtil.interestingTags[i];
        }
        checkString[TagsUtil.interestingTags.length]=parse;
        long[][]ans=threadDataService.getAimedTopicRelationships(checkString);
        long[]relation=ans[TagsUtil.interestingTags.length];
        NumCountObject[]res=new NumCountObject[relation.length];
        for(int i=0;i<relation.length;i++){
            res[i]=new NumCountObject(checkString[i],relation[i]);
        }
        Arrays.sort(res);
        HashMap<String,Long>resMap=new HashMap<>();
        for(int i=0;i<res.length&&i<3;i++){
            resMap.put(res[i].content.toString(),res[i].num);
        }
        return Result.success(resMap);
    }

    @GetMapping("/getRelationViewCountTopic")
    public Result getRelationViewCountTopic(@RequestParam String parse) {
        if(parse.isEmpty()){
            return Result.fail("输入不能为空！");
        }
        String[]checkString=new String[TagsUtil.interestingTags.length+1];
        for(int i=0;i<TagsUtil.interestingTags.length;i++){
            checkString[i]=TagsUtil.interestingTags[i];
        }
        checkString[TagsUtil.interestingTags.length]=parse;
        long[][]ans=threadDataService.getAimedTopicRelationViewCount(checkString);
        long[]relation=ans[TagsUtil.interestingTags.length];
        NumCountObject[]res=new NumCountObject[relation.length];
        for(int i=0;i<relation.length;i++){
            res[i]=new NumCountObject(checkString[i],relation[i]);
        }
        Arrays.sort(res);
        HashMap<String,Long>resMap=new HashMap<>();
        for(int i=0;i<res.length&&i<3;i++){
            resMap.put(res[i].content.toString(),res[i].num);
        }
        return Result.success(resMap);
    }
    @GetMapping("/getInterestingTopicAnswerAndCommentsNum")
    public Result getInterestingTopicAnswerAndCommentsNum() {
        NumCountObject[]res=threadDataService.getInterestingTagsCommentsAndAnswerNum();
        Arrays.sort(res);
        return Result.success(res);
    }






}
