package com.example.isho.gomoku8;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
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
    LinearLayout bGrid;
    int size, lsize;
    String style;
    boolean isFreeStyle;
    Icon wPieceID, bPieceID;
    GameDialogFragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isFreeStyle = false;


        // Import menu settings and game style
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        size = bundle.getInt("boardSize");
        style = bundle.getString("gameStyle","freestyle");
        if(style.equals("Standard")) {
            isFreeStyle = false;
        }

        // Dynamic board/piece sizing
        setContentView(R.layout.activity_board_screen);
        bGrid = new LinearLayout(getApplicationContext());
        bGrid = (LinearLayout) findViewById(R.id.boardGrid);
        if(size==10){
            lsize = 85; // do not change
            bGrid.setBackgroundResource(R.drawable.grid10);
            wPieceID = Icon.createWithResource(getApplicationContext(),R.drawable.white); //30
            bPieceID = Icon.createWithResource(getApplicationContext(),R.drawable.black);
        }
        else if(size==15){
            lsize = 58; // do not change
            bGrid.setBackgroundResource(R.drawable.grid15);
            wPieceID = Icon.createWithResource(getApplicationContext(),R.drawable.white20); //20
            bPieceID = Icon.createWithResource(getApplicationContext(),R.drawable.black20);
        }
        else {
            lsize = 42;
            bGrid.setBackgroundResource(R.drawable.grid20);
            wPieceID = Icon.createWithResource(getApplicationContext(),R.drawable.white14); //10?
            bPieceID = Icon.createWithResource(getApplicationContext(),R.drawable.black14);
        }

        // Create board and dynamically create buttons for each space
        GomokuLogic.clearBoard(size,isFreeStyle);
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
                            image = wPieceID;
                        }
                        else{
                            image = bPieceID;
                        }
                        bArray[fi][fj].setImageIcon(image);
                        bArray[fi][fj].setEnabled(false);
                        GomokuLogic.testPiece(fi,fj);
                        GomokuLogic.turnsTaken++;
                        int winner = GomokuLogic.isWin(fi,fj);
                        if(winner != 0){
                            BoardScreen.this.endGame(winner);
                        }
                        GomokuLogic.turn*=-1;

                    }
                });

                // Board layout parameters
                bArray[i][j].setBackgroundColor(Color.TRANSPARENT);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(lsize,lsize);
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
            }
        }
    }
    public void endGame(int winner) {
        String player;
        if (winner == 1)
            player = "Player 1";
        else
            player = "Player 2";

        showDialog(player);
    }

    // call this method to show dialog
    private void showDialog(String args) {
        FragmentManager fm = getSupportFragmentManager();
        frag = GameDialogFragment.newInstance("Winner: " + args);
        frag.show(fm, "activity_end_game_dialog");
    }

    public void resetMatch(){
        GomokuLogic.clearBoard(size,isFreeStyle);
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size; j++) {
                bArray[i][j].setBackgroundColor(Color.TRANSPARENT);
                bArray[i][j].setEnabled(true);
            }
        }
        frag.dismiss();
    }
    public void returnToMenu(){
        finishActivity(0);
    }


}
/*
 setContentView(R.layout.activity_board_screen);

        // Image/background sizing for chosen board size
        // currently not working as intended
  //      bGrid = (LinearLayout) findViewById(R.id.boardGrid);
        if(size==10){
            lsize = 85; // do not change
         //   bGrid.setBackgroundResource(R.drawable.grid10);
            wPieceID = R.drawable.white; //30
            bPieceID = R.drawable.black;
        }
        else if(size==15){
            lsize = 58; // do not change
        //    bGrid.setBackgroundResource(R.drawable.grid15);
            wPieceID = R.drawable.white20; //20
            bPieceID = R.drawable.black20;
        }
        else if(size==20){
            lsize = 35;
        //    bGrid.setBackgroundResource(R.drawable.grid20);
            wPieceID = R.drawable.white14; //10?
            bPieceID = R.drawable.black14;
        }
 */