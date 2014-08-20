package com.bt.bucket.http;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
* @author Thomas Clay (TC022403)
* @since 0.0
*/
public class HttpPostAsyncTask extends AsyncTask<String, String, String> {

    Context context;

    public HttpPostAsyncTask(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpClient httpclient = new DefaultHttpClient();
        Log.v("Post", "do in bacground params: " + params.toString());
        HttpPost httppost = new HttpPost(params[0]);
        try {
            List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
            for (int i = 1; i < params.length; i++) {
                nameValuePairs.add(new BasicNameValuePair("phrase" + i, params[i]));
            }
            if(params.length > 1) httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            String postEntity = HttpHelper.postToString(httppost);
            Log.v("Post", "sending [" + postEntity + "] to httpclinet");
            HttpResponse response = httpclient.execute(httppost);
            String responseString = HttpHelper.responseToString(response);
            Log.v("Post", "got response back: [" + responseString + "]");
            return response.toString();
        } catch (ClientProtocolException e) {
            Log.getStackTraceString(e);
            // TODO process exception
        } catch (IOException e) {
            Log.getStackTraceString(e);
            // TODO process exception
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        Toast.makeText(context, result == null ? "null" : result, Toast.LENGTH_LONG).show();
    }


}
