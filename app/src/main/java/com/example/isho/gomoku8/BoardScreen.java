package com.example.isho.gomoku8;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class BoardScreen extends AppCompatActivity {
    ImageButton[][] bArray;
    RelativeLayout boardView;
    int size;
    String style;
    boolean isFreeStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        size = bundle.getInt("boardSize");
        style = bundle.getString("gameStyle","freestyle");
        if(style.equals("Standard")){
            isFreeStyle = false;
        }else{
            isFreeStyle = true;
        }
        GomokuLogic.clearBoard(size,isFreeStyle);
        setContentView(R.layout.activity_board_screen);
        boardView = (RelativeLayout) findViewById(R.id.boardView);
        bArray = new ImageButton[size][size];
        for (int i =0; i<size; i++){
            for(int j = 0; j<size; j++){
                final int fi = i;
                final int fj = j;
                bArray[i][j]= new ImageButton(getApplicationContext());
                bArray[i][j].setId(View.generateViewId());
                bArray[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Icon image;
                        if(GomokuLogic.getTurn()>0) {
                            image = Icon.createWithResource(getApplicationContext(),R.drawable.white);
                        }
                        else{
                            image = Icon.createWithResource(getApplicationContext(),R.drawable.black);
                        }
                        bArray[fi][fj].setImageIcon(image);
                        bArray[fi][fj].setEnabled(false);
                        GomokuLogic.placePiece(fi,fj);
                        int winner = GomokuLogic.isWin(fi,fj);
                        if(winner != 0){
                            BoardScreen.this.endGame(winner);
                        }

                    }
                });
                Icon image = Icon.createWithResource(getApplicationContext(),R.drawable.cross);
                bArray[i][j].setImageIcon(image);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
                if(i==0) {
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);
                }else{
                    params.addRule(RelativeLayout.BELOW,bArray[i-1][j].getId());
                }
                if(j==0){
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
                }else{
                    params.addRule(RelativeLayout.RIGHT_OF,bArray[i][j-1].getId());
                }
                boardView.addView(bArray[i][j],params);
 //               boardView.setBackgroundColor(Color.TRANSPARENT);

            }
        }
    }
    public void endGame(int winner){

    }




}
