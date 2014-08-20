package com.bt.bucket;

/**
 * @author Thomas Clay (TC022403)
 * @since X.X TODO
 */
public class Configuration {

    public static String BASE_URL = "http://10.0.2.2:4567";

    public static String bucketId = "z";

    public static String getSubmissionUrl() {
        return BASE_URL+"/submit/"+bucketId;
    }

    public static String getStartUrl() {
        return BASE_URL+"/startgame/"+bucketId;
    }



}
