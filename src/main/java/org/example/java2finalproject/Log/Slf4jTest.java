package org.example.java2finalproject.Log;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Slf4jTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(Slf4jTest.class);
    @Test
    public void testQuick(){
        // 日志输出
        LOGGER.error("error");
        LOGGER.warn("warning");
        LOGGER.info("info");    // 默认的日志级别信息
        LOGGER.debug("debug");
        LOGGER.trace("trace");  // 追踪信息

        // 使用占位符输出日志信息
        String name = "java_log";
        Integer age = 18;
        LOGGER.info("用户：{}，{}", name, age);

        // 将系统的异常信息输出
        try {
            int i = 1 / 0;
        } catch (Exception e){
            // e.printStackTrace();
            LOGGER.error("出现异常：", e);
        }
    }


}
