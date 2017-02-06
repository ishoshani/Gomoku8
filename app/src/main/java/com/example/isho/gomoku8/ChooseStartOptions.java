package com.example.isho.gomoku8;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ChooseStartOptions extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_start_options);
        Intent intent = getIntent();
    }
    public void NewGame(View view){
        Intent intent = new Intent(this, BoardScreen.class);
        intent.putExtra("size", 10);
        startActivity(intent);
    }
}
