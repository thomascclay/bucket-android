package com.bt.bucket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.bt.bucket.http.HttpPostAsyncTask;

public class IntroActivity extends Activity {



    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
    }

    public void joinGameButtonAction(View v) {
        getJoinGameDialog().show();
    }

    public void createGameButtonAction(View v) {

        //TODO get next game number
        startActivity(new Intent(this, AddPhrasesActivity.class));
    }

    public AlertDialog getJoinGameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogView = getLayoutInflater().inflate(R.layout.join_game_dialog, null);
        builder.setTitle(R.string.join_game)
               .setView(dialogView)
               .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       EditText editText = (EditText) dialogView.findViewById(R.id.edt_game_num);
                       Editable t = editText.getText();
                       String gameCode = t.toString();
                       joinGame(gameCode);
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.cancel();
                   }
               });
        return builder.create();
    }

    private void joinGame(String gameCode) {

        //TODO confirm game exists
        boolean success = GameManager.joinGame(gameCode);
        if(success){
            Toast.makeText(this, "Successfuly joined game " + gameCode, Toast.LENGTH_LONG).show();
        }

    }





}
