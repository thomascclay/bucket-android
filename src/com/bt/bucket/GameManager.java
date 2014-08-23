package com.bt.bucket;

import android.app.Application;
import android.content.Context;
import com.bt.bucket.http.HttpPostAsyncTask;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Thomas Clay (TC022403)
 * @since X.X TODO
 */
public class GameManager extends Application {

    Context context = null;
    private static Map<Context, GameManager> instances = new HashMap<Context, GameManager>();

    public static GameManager getInstance(Context context) {
        if(!instances.containsKey(context)) {
            GameManager instance = new GameManager(context);
            instances.put(context, instance);
        }
        return instances.get(context);
    }


    private GameManager(Context context){
        this.context = context;
    }

    public static String BASE_URL = "http://10.0.2.2:4567";

    public static String bucketId = "0";

    public static String getSubmissionUrl() {
        return BASE_URL+"/submit/"+bucketId;
    }

    private static String getStartUrl() {
        return BASE_URL+"/startgame/"+bucketId;
    }

    public String createNewGame() {
        //TODO
        HttpPostAsyncTask startPost = new HttpPostAsyncTask(context);
        startPost.execute(GameManager.getStartUrl());
        getStartUrl();
        return "z";
    }

    public static boolean joinGame(String gameCode) {
        return true;
    }



}
