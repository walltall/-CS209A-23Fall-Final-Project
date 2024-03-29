package org.example.java2finalproject;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetUrlData {
    public static boolean getUrlData(String url,int num) {
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Encoding", "gzip");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                byte[] responseData = IoUtil.readBytes(inputStream);
                byte[] unzippedData = ZipUtil.unGzip(responseData);
                String jsonStr = new String(unzippedData, StandardCharsets.UTF_8);
                JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
                // 将解压缩后的JSON数据保存为文件
                String filePath = "ThreadsDataSource/data_"+num+".json";
                FileUtil.writeBytes(unzippedData, new File(filePath));
                System.out.println("JSON数据已保存至：" + filePath);
                return true;
            }else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}