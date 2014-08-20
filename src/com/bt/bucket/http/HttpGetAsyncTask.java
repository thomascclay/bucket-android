package com.bt.bucket.http;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * @author Thomas Clay (TC022403)
 * @since 0.0
 */
public class HttpGetAsyncTask extends AsyncTask<String, String, String> {
    Context context;
    public HttpGetAsyncTask(Context context){
        super();
        this.context = context;
    }

    @Override
    protected String doInBackground(String... uri) {
        Log.v("Request", "request started");
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            Log.v("Request", "about to execute request to ["+uri[0]+"]");
            response = httpclient.execute(new HttpGet(uri[0]));
            Log.v("Request", "got response back from uri ["+uri[0]+"]");
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                responseString = HttpHelper.responseToString(response);
                Log.v("Request", "request returned [" + responseString + "]");
            } else{
                Log.e("Request", "request failed: " + response.getStatusLine());
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            Log.getStackTraceString(e);
        } catch (IOException e) {
            Log.getStackTraceString(e);
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }
}
