package org.example.java2finalproject.controller;

import org.example.java2finalproject.common.NumCountObject;
import org.example.java2finalproject.common.TagsUtil;
import org.example.java2finalproject.entity.ThreadsData;
import org.example.java2finalproject.service.ThreadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.java2finalproject.common.Result;

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
    @GetMapping("/importDataToDatabase")
    public Result importDataToDatabase() throws IOException {
        int count=threadDataService.loadData();
        return Result.success("已导入"+count+"条数据");
    }
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
        HashMap<String,Integer> tags=threadDataService.getAllTheTags();
        NumCountObject[] ans=new NumCountObject[TagsUtil.interestingTags.length];
        for(int i=0;i<TagsUtil.interestingTags.length;i++){
            if(!tags.containsKey(TagsUtil.interestingTags[i])){
                System.out.println("标签"+TagsUtil.interestingTags[i]+"不存在");
            }else {
                ans[i] = new NumCountObject(TagsUtil.interestingTags[i], tags.get(TagsUtil.interestingTags[i]));
            }
        }
        return Result.success(ans);
    }
    @GetMapping("/checkInterestingData")
    public Result checkInterestingData() {
        List<ThreadsData> ans=threadDataService.getInterestingData();
        return Result.success(ans);
    }

}
