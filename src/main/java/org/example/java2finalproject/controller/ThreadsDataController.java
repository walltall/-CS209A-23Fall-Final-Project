package org.example.java2finalproject.controller;

import org.example.java2finalproject.common.NumCountObject;
import org.example.java2finalproject.entity.QuestionData;
import org.example.java2finalproject.service.AnswerDataService;
import org.example.java2finalproject.service.ThreadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.java2finalproject.common.Result;

import java.io.IOException;
import java.util.ArrayList;
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
        return Result.success(threadDataService.getAllThreadData());
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
        return Result.success(ans);
    }
    @GetMapping("/answerRate")
    public Result answerRate() {
        List<QuestionData> questionData =threadDataService.getAllThreadData();
        int count=0;
        for(int i = 0; i< questionData.size(); i++){
            if(questionData.get(i).is_answered()){
                count++;
            }
        }
        return Result.success(count);
    }


}
