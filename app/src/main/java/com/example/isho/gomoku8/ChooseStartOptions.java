package com.example.isho.gomoku8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ToggleButton;
import android.view.View;
import android.util.Log;

public class ChooseStartOptions extends AppCompatActivity {

    String gameStyle = "Standard";
    int broadSize = 10;
    int playerSize = 1;
    Intent intent;
    String msg = "*******Log******* ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_start_options);
    }

    public void NewGame(View view){
        ToggleButton style = (ToggleButton)findViewById(R.id.toggleButton3);
        ToggleButton size  = (ToggleButton)findViewById(R.id.toggleButton2);
        ToggleButton player = (ToggleButton)findViewById(R.id.toggleButton);

        if(style.isChecked()) {
            this.gameStyle = "FreeStyle";
        }

        if (size.isChecked()) {
            this.broadSize = 20;
        }

        if (player.isChecked()) {
            this.playerSize = 2;
        }

//        Log.d(msg, this.gameStyle);

        Intent intent = new Intent(this, BoardScreen.class);
        Bundle bundle = new Bundle();
        bundle.putString("gameStyle", this.gameStyle);
        bundle.putInt("boardSize",this.broadSize);
        bundle.putInt("playerSize",this.playerSize);
        intent.putExtras(bundle);
        //        Log.d(msg, this.gameStyle + this.broadSize + this.playerSize);
        startActivity(intent);
    }

}
