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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class BoardScreen extends AppCompatActivity implements AsyncResponse {
    ImageButton[][] bArray;
    RelativeLayout boardView;
    LinearLayout bGrid;
    int size, lsize, playerSize;
    String style;
    boolean isFreeStyle;
    Icon whitePieceImage, blackPieceImage;
    GameDialogFragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Import menu settings and game style
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        size = bundle.getInt("boardSize");
        style = bundle.getString("gameStyle","freestyle");
        playerSize = bundle.getInt("playerSize");
        if(style.equals("Standard")){
            isFreeStyle = false;
        }else{
            isFreeStyle = true;
        }

        // Dynamic board/piece sizing
        setContentView(R.layout.activity_board_screen);
        bGrid = new LinearLayout(getApplicationContext());
        bGrid = (LinearLayout) findViewById(R.id.boardGrid);
        if(size==10){
            lsize = 85; // do not change
            bGrid.setBackgroundResource(R.drawable.grid10);
            whitePieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.white); //30
            blackPieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.black);
        }
        else if(size==15){
            lsize = 58; // do not change
            bGrid.setBackgroundResource(R.drawable.grid15);
            whitePieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.white20); //20
            blackPieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.black20);
        }
        else { //20x20
            lsize = 42;
            bGrid.setBackgroundResource(R.drawable.grid20);
            whitePieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.white14); //10?
            blackPieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.black14);
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
                        GomokuHandler localHandler = new GomokuHandler();
                        localHandler.delegate = BoardScreen.this;
                        if(playerSize == 1) {
                            localHandler.isAI = true;
                        }
                        if(GomokuLogic.getTurn()>0) {
                            image = whitePieceImage;
                        }
                        else{
                            image = blackPieceImage;
                        }
                        bArray[fi][fj].setImageIcon(image);
                        bArray[fi][fj].setEnabled(false);
                        localHandler.execute(fi,fj);

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

    public void resetMatch() {
        GomokuLogic.clearBoard(size, isFreeStyle);
        for (int i = 0; i < size; i++) {
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
    public void finishProcess(Integer output, int aiRow, int aiCol, int row, int col){
        if(playerSize == 1) {
            bArray[aiRow][aiCol].setEnabled(false);
            bArray[aiRow][aiCol].setImageIcon(blackPieceImage);
        }
        if(output!=0){
            endGame(output);
        }
    }
    public void handleError(){
        endGame(-5);
    }


    // image sizing conversions
    public int dpToPX(int dp) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return (int)((dp * displayMetrics.density) + 0.5);
    }
    public int pxToDP(int px) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return (int)((px/displayMetrics.density) + 0.5);

    }

}