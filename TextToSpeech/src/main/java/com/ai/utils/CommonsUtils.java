package com.ai.utils;

/**
 * 常见工具类
 */
public class CommonsUtils {

    public static final long HALF_HOUR_TIME = 30 * 60 * 1000;

    public static final long ONE_QUARTER_TIME = 15 * 60 * 1000;

    /**
     * 获取时间戳
     * @return
     */
    public static String getRandomFileNameByTime(){
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     *
     * @param timeStamp
     * @param time
     * @return
     */
    public static boolean isExpired(Long timeStamp, Long time) {
        return System.currentTimeMillis() - timeStamp > time;
    }
}
