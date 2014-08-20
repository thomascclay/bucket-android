package com.bt.bucket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.bt.bucket.http.HttpPostAsyncTask;


import java.util.ArrayList;

public class IntroActivity extends Activity {

    Button addPhraseBtn;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
    }

    public void startGame(View v) {
        HttpPostAsyncTask startPost = new HttpPostAsyncTask(this);
        startPost.execute(Configuration.getStartUrl());
        startActivity(new Intent(this, AddPhrasesActivity.class));
    }






}
