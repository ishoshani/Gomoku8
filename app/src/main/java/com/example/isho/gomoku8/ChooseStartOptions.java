package com.example.isho.gomoku8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ToggleButton;

public class ChooseStartOptions extends AppCompatActivity {

    String gameStyle;
    int broadSize = 10;
    int playerSize = 2;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_start_options);
        ToggleButton state = (ToggleButton)findViewById(R.id.toggleButton3);
        if (state.isChecked()) {
            this.gameStyle = "FreeStyle";
        }
        else
            this.gameStyle = "Standard";
    }
    public void NewGame(View view){
        Intent intent = new Intent(this, BoardScreen.class);
        intent.putExtra("gameStlye",this.gameStyle);
        intent.putExtra("size", 10);
        startActivity(intent);
    }
}
