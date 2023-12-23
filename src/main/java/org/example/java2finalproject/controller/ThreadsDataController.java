package org.example.java2finalproject.controller;

import org.apache.log4j.Logger;
import org.example.java2finalproject.common.*;
import org.example.java2finalproject.entity.QuestionData;
import org.example.java2finalproject.service.AnswerDataService;
import org.example.java2finalproject.service.ThreadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/Threads")
public class ThreadsDataController {
    @Autowired
    private ThreadDataService threadDataService;
    @Autowired
    private AnswerDataService answerDataService;
    private final Logger logger=Logger.getLogger(ThreadsDataController.class);
    @GetMapping("/importDataToDatabase")
    public Result importDataToDatabase(){
        try {
            int count = threadDataService.loadData(14);
            logger.info("已导入" + count + "条数据");
            return Result.success("已导入" + count + "条数据");
        }catch (IOException e) {
            logger.error("导入数据失败");
            return Result.fail("导入数据失败");
        }
    }
    @GetMapping("/getThreadDataFromTheWeb")
    public Result getThreadData() {

        boolean t= threadDataService.getThreadsDataFromWeb(15,80);
        if(t) {
            logger.info("获取数据成功");
            return Result.success(t);
        }
        else{
            logger.error("获取数据失败");
            return Result.fail(t);
        }
    }
//

    @GetMapping("/getAllData")
    public Result getAllData() {
        try {
            logger.info("用户查询所有的问题信息");
            return Result.success(threadDataService.getAllQuestionData());
        }catch (Exception e){
            logger.error("用户查询所有的问题信息失败");
            return Result.fail(null);
        }
    }
    @DeleteMapping("/deleteAllData")
    public Result deleteAllData() {
        logger.warn("删除了所有的信息");
        threadDataService.deleteAll();
        return Result.success("删除了所有的信息");
    }
    @GetMapping("/checkAllTags")
    public Result checkAllTags() {
        HashMap<String,Integer> tags=threadDataService.getAllTheTags();
        if(tags.size()==0||tags==null){
            logger.warn("数据空，目前无数据");
            return Result.fail("数据空，目前无数据");
        }
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
        logger.info("用户查询所有的标签");
        return Result.success(ans);
    }

    @GetMapping("/checkInterestingTags")
    public Result checkInterestingTags() {
       NumCountObject[]ans=threadDataService.getInterestingTagsCount();
       Arrays.sort(ans);
       logger.info("用户查询所有的有趣标签");
        return Result.success(ans);
    }
    @GetMapping("/checkInterestingData")
    public Result checkInterestingData() {
        List<QuestionData> ans=threadDataService.getInterestingData();
        logger.info("用户查询所有的有趣问题");
        return Result.success(ans);
    }
    @GetMapping("/checkInterestingDataViewCount")
    public Result checkInterestingDataViewCount() {
        HashMap<String,Long> ans=threadDataService.getInterestingDataViewCount();
        NumCountObject[]res=new NumCountObject[TagsUtil.interestingTags.length];
        for(int i=0;i<TagsUtil.interestingTags.length;i++){
            res[i]=new NumCountObject(TagsUtil.interestingTags[i],ans.get(TagsUtil.interestingTags[i]));
        }
        Arrays.sort(res);
        logger.info("用户查询关注的数据的总浏览量");
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
        logger.info("用户查询关注的数据的平均浏览量");
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
        logger.info("用户查询问题中有回答的比例");
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
        logger.info("用户查询关注的数据中有回答的比例");
        return Result.success(ans);
    }

    @GetMapping("/getDifferentErrorNumber")
    public Result getDifferentErrorNumber() {
        HashMap<String,Integer> res=threadDataService.getDifferentErrorNumber();
        NumCountObject[]ans=new NumCountObject[3];
        ans[0]=new NumCountObject(ErrorsClassify.SyntaxErrorName,res.get(ErrorsClassify.SyntaxErrorName));
        ans[1]=new NumCountObject(ErrorsClassify.ExceptionErrorName,res.get(ErrorsClassify.ExceptionErrorName));
        ans[2]=new NumCountObject(ErrorsClassify.FatalErrorName,res.get(ErrorsClassify.FatalErrorName));
        logger.info("用户查询不同类型的错误的数量");
        return Result.success(threadDataService.getDifferentErrorNumber());
    }
    @GetMapping("/getDifferentErrorViewCount")
    public Result getDifferentErrorViewCount() {
        HashMap<String,Long> res=threadDataService.getDifferentErrorViewCount();
        NumCountObject[]ans=new NumCountObject[3];
        ans[0]=new NumCountObject(ErrorsClassify.SyntaxErrorName,res.get(ErrorsClassify.SyntaxErrorName));
        ans[1]=new NumCountObject(ErrorsClassify.ExceptionErrorName,res.get(ErrorsClassify.ExceptionErrorName));
        ans[2]=new NumCountObject(ErrorsClassify.FatalErrorName,res.get(ErrorsClassify.FatalErrorName));
        logger.info("用户查询不同类型的错误的浏览量");
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
        logger.info("用户查询致命错误的数量");
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
        logger.info("用户查询异常错误的数量");
        return Result.success(res);
    }
    @GetMapping("/getUserParseNumber")
    public Result getUserParseNumber(@RequestParam String phrase) {
        if(phrase.isEmpty()){
            logger.warn("用户输入的phrase为空！");
            return Result.fail("输入不能为空！");
        }
        long[][]ans=threadDataService.getAimedTopicRelationships(new String[]{phrase});
        HashMap<String,Long>res=new HashMap<>();
        res.put(phrase,ans[0][0]);
        logger.info("用户查询"+ phrase +"的数量");
        return Result.success(res);
    }

    @GetMapping("/getFatalErrorViewCount")
    public Result getFatalErrorViewCount() {
        try {
            long[][] ans = threadDataService.getAimedTopicRelationViewCount(ErrorsClassify.FatalErrorArray);
            NumCountObject[] res = new NumCountObject[ans.length];
            for (int i = 0; i < ans.length; i++) {
                res[i] = new NumCountObject(ErrorsClassify.FatalErrorArray[i], ans[i][i]);
            }
            Arrays.sort(res);
            logger.info("用户查询致命错误的浏览量");
            return Result.success(res);
        }catch (Exception e){
            logger.error("用户查询致命错误的浏览量失败");
            return Result.fail();
        }
    }
    @GetMapping("/getExceptionViewCount")
    public Result getExceptionViewCount() {
        try {
            long[][] ans = threadDataService.getAimedTopicRelationViewCount(ErrorsClassify.ExceptionArray);
            NumCountObject[] res = new NumCountObject[ans.length];
            for (int i = 0; i < ans.length; i++) {
                res[i] = new NumCountObject(ErrorsClassify.ExceptionArray[i], ans[i][i]);
            }
            Arrays.sort(res);
            logger.info("用户查询异常错误的浏览量");
            return Result.success(res);
        }catch (Exception e){
            logger.error("用户查询异常错误的浏览量失败");
            return Result.fail();
        }
    }
    @GetMapping("/getUserParseViewCount")
    public Result getUserParseViewCount(@RequestParam String parse) {
        try {
            if (parse.isEmpty()) {
                return Result.fail("输入不能为空！");
            }
            long[][] ans = threadDataService.getAimedTopicRelationViewCount(new String[]{parse});
            HashMap<String, Long> res = new HashMap<>();
            res.put(parse, ans[0][0]);
            logger.info("用户查询" + parse + "的浏览量");
            return Result.success(res);
        }catch (Exception e){
            logger.error("用户查询"+ parse +"的浏览量失败");
            return Result.fail();
        }
    }
    @GetMapping("/getRelationTopic")
    public Result getRelationTopic(@RequestParam String parse) {
        try {
            if (parse.isEmpty()) {
                logger.warn("用户输入的phrase为空！");
                return Result.fail("输入不能为空！");
            }
            String[] checkString = new String[TagsUtil.interestingTags.length + 1];
            for (int i = 0; i < TagsUtil.interestingTags.length; i++) {
                checkString[i] = TagsUtil.interestingTags[i];
            }
            checkString[TagsUtil.interestingTags.length] = parse;
            long[][] ans = threadDataService.getAimedTopicRelationships(checkString);
            long[] relation = ans[TagsUtil.interestingTags.length];
            NumCountObject[] res = new NumCountObject[relation.length];
            for (int i = 0; i < relation.length; i++) {
                res[i] = new NumCountObject(checkString[i], relation[i]);
            }
            Arrays.sort(res);
            HashMap<String, Long> resMap = new HashMap<>();
            for (int i = 0; i < res.length && i < 3; i++) {
                resMap.put(res[i].content.toString(), res[i].num);
            }
            logger.info("用户查询" + parse + "的相关话题");
            return Result.success(resMap);
        }catch (Exception e){
            logger.error("用户查询"+ parse +"的相关话题失败");
            return Result.fail();
        }
    }

    @GetMapping("/getRelationViewCountTopic")
    public Result getRelationViewCountTopic(@RequestParam String parse) {
        try {
            if (parse.isEmpty()) {
                logger.warn("用户输入的phrase为空！");
                return Result.fail("输入不能为空！");
            }
            String[] checkString = new String[TagsUtil.interestingTags.length + 1];
            for (int i = 0; i < TagsUtil.interestingTags.length; i++) {
                checkString[i] = TagsUtil.interestingTags[i];
            }
            checkString[TagsUtil.interestingTags.length] = parse;
            long[][] ans = threadDataService.getAimedTopicRelationViewCount(checkString);
            long[] relation = ans[TagsUtil.interestingTags.length];
            NumCountObject[] res = new NumCountObject[relation.length];
            for (int i = 0; i < relation.length; i++) {
                res[i] = new NumCountObject(checkString[i], relation[i]);
            }
            Arrays.sort(res);
            HashMap<String, Long> resMap = new HashMap<>();
            for (int i = 0; i < res.length && i < 3; i++) {
                resMap.put(res[i].content.toString(), res[i].num);
            }
            logger.info("用户查询" + parse + "的相关话题");
            return Result.success(resMap);
        }catch (Exception e){
            logger.error("用户查询"+ parse +"的相关话题失败");
            return Result.fail();
        }
    }
    @GetMapping("/getInterestingTopicAnswerAndCommentsNum")
    public Result getInterestingTopicAnswerAndCommentsNum() {
        try {
            NumCountObject[] res = threadDataService.getInterestingTagsCommentsAndAnswerNum();
            Arrays.sort(res);
            logger.info("用户查询有趣话题的回答和评论数量");
            return Result.success(res);
        }catch (Exception e){
            logger.error("用户查询有趣话题的回答和评论数量失败");
            return Result.fail();
        }
    }
    @GetMapping("/getAllExceptionKindTopic")
    public Result getAllExceptionKindTopic() {
        try {
            HashSet<String> res = threadDataService.getAllAimTopicKind(ErrorsClassify.ExceptionName);
            logger.info("用户查询所有exception类别的相关话题");
            return Result.success(res);
        }catch (Exception e){
            logger.error("用户查询所有exception类别的相关话题失败");
            return Result.fail();
        }
    }

    @GetMapping("/getAllErrorKindTopic")
    public Result getAllErrorKindTopic() {
        try {
            HashSet<String> res = threadDataService.getAllAimTopicKind(ErrorsClassify.ErrorName);
            logger.info("用户查询所有error类别的相关话题");
            return Result.success(res);
        }catch (Exception e){
            logger.error("用户查询所有error类别的相关话题失败");
            return Result.fail();
        }
    }







}
