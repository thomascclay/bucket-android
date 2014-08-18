package com.bt.bucket;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends Activity {

    Button addPhraseBtn, submitPhrasesBtn;
    ArrayList<String> phrases = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        adapter = new ArrayAdapter<String>(this, R.)

    }


    public void addPhrases(View v) {
//        listItems.add()
    }
}
