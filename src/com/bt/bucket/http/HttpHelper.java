package com.bt.bucket.http;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas Clay (TC022403)
 * @since 0.1
 */
public class HttpHelper {

    public static String entityToString(HttpEntity entity){
        if (entity == null) return "null";
        OutputStream out = new ByteArrayOutputStream();
        try {
            entity.writeTo(out);
            String s = out.toString();
            out.close();
            return s;
        } catch (IOException e) {
            //TODO
        }
        return null;
    }

    public static String responseToString(HttpResponse response) {
        return entityToString(response.getEntity());
    }

    public static String postToString(HttpPost post){
        return entityToString(post.getEntity());
    }


}
