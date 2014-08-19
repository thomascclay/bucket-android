package com.bt.bucket;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
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
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    Button addPhraseBtn, submitPhrasesBtn;
    EditText phrasesText;
    ListView phrasesListView;
    ArrayList<String> phrasesArray = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        addPhraseBtn = (Button) findViewById(R.id.btn_add);
        submitPhrasesBtn = (Button) findViewById(R.id.btn_submit);
        phrasesText = (EditText) findViewById(R.id.edt_phrase);
        phrasesListView = (ListView) findViewById(R.id.lst_phrases);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, phrasesArray);
        phrasesListView.setAdapter(adapter);

        addPhraseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPhrases(view);
            }
        });

        phrasesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                phrasesText.setText((String)adapterView.getItemAtPosition(i));
                phrasesArray.remove(i);
                adapter.notifyDataSetChanged();
            }
        });

        submitPhrasesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitAction();
            }
        });
    }


    public void addPhrases(View v) {
        phrasesArray.add(phrasesText.getText().toString());
        phrasesText.getText().clear();
        adapter.notifyDataSetChanged();
    }

    public void requestAction() {
        RequestTask task = new RequestTask();
        task.execute("http://10.0.2.2:4567");
    }

    public void submitAction() {
        ArrayList<String> params = (ArrayList<String>) phrasesArray.clone();
        params.add(0, "http://10.0.2.2:4567/submit/z");
        PostTask task = new PostTask();
        task.execute(params.toArray(new String[params.size()]));
    }

    /**
     * @author Thomas Clay (TC022403)
     * @since X.X TODO
     */
    class RequestTask extends AsyncTask<String, String, String> {

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
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    responseString = out.toString();
                    Log.v("Request", "request returned [" + responseString + "]");
                } else{
                    Log.e("Request", "request failed: " + response.getStatusLine());
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (ClientProtocolException e) {
                Log.getStackTraceString(e);
                //TODO Handle problems..
            } catch (IOException e) {
                Log.getStackTraceString(e);
                //TODO Handle problems..
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }


    class PostTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            Log.v("Post", "do in bacground params: " + params.toString());
            HttpPost httppost = new HttpPost(params[0]);
            OutputStream out = new ByteArrayOutputStream();
            try {
                List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
                for (int i = 1; i < params.length; i++) {
                    nameValuePairs.add(new BasicNameValuePair("phrase" + i, params[i]));
                }

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httppost.getEntity().writeTo(out);
                String postEntity = out.toString();
                out.close(); out = new ByteArrayOutputStream();
                Log.v("Post", "sending [" + postEntity + "] to httpclinet");
                HttpResponse response = httpclient.execute(httppost);
                response.getEntity().writeTo(out);
                String responseString = out.toString();
                out.close();
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
            Toast.makeText(MainActivity.this, result == null ? "null" : result, Toast.LENGTH_LONG).show();
        }


    }


}
