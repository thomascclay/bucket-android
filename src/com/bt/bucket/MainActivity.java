package com.bt.bucket;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

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
    }


    public void addPhrases(View v) {
        phrasesArray.add(phrasesText.getText().toString());
        phrasesText.getText().clear();
        adapter.notifyDataSetChanged();
    }

}
