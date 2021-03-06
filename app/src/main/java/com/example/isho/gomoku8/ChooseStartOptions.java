package com.example.isho.gomoku8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.util.Log;

public class ChooseStartOptions extends AppCompatActivity {

    String gameStyle;
    int boardSize = 10;
    int playerSize = 2;
    int numRounds = 1;
    Intent intent;
    String msg = "*******Log******* ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_start_options);
    }
    public void NewOnlineGame(View view){
        Intent intent = new Intent(this, OnlineBoard.class);
        Bundle bundle = new Bundle();
        intent.putExtra("gameStyle","freeStyle");
        bundle.putInt("boardSize",this.boardSize);
        bundle.putInt("playerSize",this.playerSize);
        intent.putExtras(bundle);
        //        Log.d(msg, this.gameStyle + this.boardSize + this.playerSize);
        startActivity(intent);
    }

    public void NewGame(View view){
        RadioGroup styleID = (RadioGroup)findViewById(R.id.modeRadio);
        int style = styleID.getCheckedRadioButtonId();
        RadioButton radioStyle = (RadioButton) findViewById(style);
        if(radioStyle.getText().equals("Freestyle")) {
            this.gameStyle = "Freestyle";
        }
        else
            this.gameStyle = "Standard";

        RadioGroup sizeID = (RadioGroup)findViewById(R.id.boardRadio);
        int board = sizeID.getCheckedRadioButtonId();
        RadioButton radioSize = (RadioButton) findViewById(board);
        if (radioSize.getText().equals("15x15")) {
            this.boardSize = 15;
        }
        else if(radioSize.getText().equals("20x20")){
            this.boardSize = 20;
        }
        else
            this.boardSize = 10;

        RadioGroup playerID = (RadioGroup)findViewById(R.id.PlayersRadio);
        int player = playerID.getCheckedRadioButtonId();
        RadioButton radioPlayers = (RadioButton) findViewById(player);
        if (radioPlayers.getText().equals("Two")){
            this.playerSize = 2;
        }
        else
            this.playerSize = 1;
        RadioGroup RoundID = (RadioGroup)findViewById(R.id.RoundsRadio);
        int rounds = RoundID.getCheckedRadioButtonId();
        RadioButton radioRound = (RadioButton) findViewById(rounds);
        if(radioRound.getText().equals("1")){
            this.numRounds = 1;
        }
        if(radioRound.getText().equals("3")){
            this.numRounds = 3;
        }
        if(radioRound.getText().equals("5")){
            this.numRounds = 5;
        }
        Intent intent = new Intent(this, BoardScreen.class);
        Bundle bundle = new Bundle();
        intent.putExtra("gameStyle",this.gameStyle);
        bundle.putInt("boardSize",this.boardSize);
        bundle.putInt("playerSize",this.playerSize);
        bundle.putInt("numRounds",this.numRounds);
        intent.putExtras(bundle);
        //        Log.d(msg, this.gameStyle + this.boardSize + this.playerSize);
        startActivity(intent);



    }
}
