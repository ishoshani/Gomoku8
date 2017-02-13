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
    Intent intent;
    String msg = "*******Log******* ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_start_options);
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

        Intent intent = new Intent(this, BoardScreen.class);
        Bundle bundle = new Bundle();
        intent.putExtra("gameStyle",this.gameStyle);
        bundle.putInt("boardSize",this.boardSize);
        bundle.putInt("playerSize",this.playerSize);
        intent.putExtras(bundle);
        //        Log.d(msg, this.gameStyle + this.broadSize + this.playerSize);
        startActivity(intent);



    }
}
