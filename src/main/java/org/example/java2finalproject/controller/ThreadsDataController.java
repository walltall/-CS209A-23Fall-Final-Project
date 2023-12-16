package org.example.java2finalproject.controller;

import org.example.java2finalproject.service.ThreadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.java2finalproject.common.Result;

import java.io.IOException;

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

}
