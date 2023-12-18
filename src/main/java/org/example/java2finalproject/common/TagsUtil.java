package org.example.java2finalproject.common;

import java.util.Set;

public class TagsUtil {
    public static String[] interestingTags=
            {"java","spring","spring-boot","spring-data-jpa","jpa","generics","tomcat",
            "maven","jvm","javafx","serialization","hibernate","jar"};
    public static String[] getTagsArray(String tags) {
        //去掉开头的[与结尾的],并除去空格
        if (tags.startsWith("[") && tags.endsWith("]")) {
            tags = tags.substring(1, tags.length() - 1);
            tags=tags.replaceAll("\"", "");
            tags = tags.replaceAll(" ", "");
            String[] tagsArray = tags.split(",");
            return tagsArray;
        } else {
            return null;
        }

    }
}
