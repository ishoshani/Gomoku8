package com.example.isho.gomoku8;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
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

import java.io.IOException;

public class OnlineBoard extends AppCompatActivity implements AsyncResponse,OnlineMoveProcessor {
    ImageButton[][] bArray;
    RelativeLayout boardView;
    LinearLayout bGrid;
    int size, lsize;
    String style;
    boolean isFreeStyle;
    Icon whitePieceImage, blackPieceImage;
    OnlineDialogFragment frag;
    int playerSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TCPtask connect = new TCPtask();
        connect.delegate = this;
        connect.execute();

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
        Snackbar.make(findViewById(android.R.id.content),"Looking For Game",Snackbar.LENGTH_SHORT).show();
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
        else {
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
                        if(OnlineClient.isMyTurn) {
                            Icon image;
                            GomokuHandler localHandler = new GomokuHandler();
                            localHandler.delegate = OnlineBoard.this;
                            localHandler.isOnline = true;
                            if (OnlineClient.isFirst) {
                                image = whitePieceImage;
                            } else {
                                image = blackPieceImage;
                            }
                            bArray[fi][fj].setImageIcon(image);
                            bArray[fi][fj].setEnabled(false);
                            localHandler.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, fi, fj);
                            Snackbar.make(findViewById(android.R.id.content),"Waiting For Opponents Turn",Snackbar.LENGTH_LONG).show();

                        }



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
        else if (winner == -1)
            player = "Player 2";
        else if (winner == -3)
            player = "tie";
        else if (winner == -5)
            player = "connection Error";
        else
            player = "wut";
        OnlineClient.inGame = false;

        showDialog(player);
    }

    // call this method to show dialog
    private void showDialog(String args) {
        FragmentManager fm = getSupportFragmentManager();
        frag = OnlineDialogFragment.newInstance("Winner: " + args);
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
    public void finishProcess(Integer output, int aiRow, int aiCol, int row, int col){
        if(playerSize == 1) {
            bArray[aiRow][aiCol].setEnabled(false);
            bArray[aiRow][aiCol].setImageIcon(blackPieceImage);
        }
        try {
            OnlineClient.SendMove(row, col);
        }catch(IOException e){
            endGame(-5);
        }

        if (output != 0) {
            Log.i("online","win Discovered");
            OnlineClient.inGame=false;
            endGame(output);
            }


    }
    public  void processOnlineMove(int row, int col, int winner) {
        bArray[row][col].setEnabled(false);

        if (!OnlineClient.isFirst) {
            bArray[row][col].setImageIcon(whitePieceImage);
        } else {
            bArray[row][col].setImageIcon(blackPieceImage);
        }
        if (winner != 0) {
            endGame(winner);
        }
        Snackbar.make(findViewById(android.R.id.content),"Your Turn",Snackbar.LENGTH_SHORT).show();

    }
    public void showConnectionStart(){
        if(OnlineClient.isFirst) {
            Snackbar.make(findViewById(android.R.id.content), "Found Game,Your Turn", Snackbar.LENGTH_LONG).show();
        }else{
            Snackbar.make(findViewById(android.R.id.content),"Found Game, Other Player's Turn",Snackbar.LENGTH_LONG).show();
        }
    }
    public void handleProblem(int type){
        if(type ==1){
            Snackbar.make(findViewById(android.R.id.content), "The Other Player Disconnected", Snackbar.LENGTH_LONG).show();
            endGame(-5);
        }
        if(type ==2){
            Snackbar.make(findViewById(android.R.id.content), "The Server had an Error", Snackbar.LENGTH_LONG).show();
            endGame(-5);
        }
    }
    public void handleError(){
        endGame(-5);
    }

    // Image sizing conversions
    public int dpToPX(int dp) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return (int)((dp * displayMetrics.density) + 0.5);
    }
    public int pxToDP(int px) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return (int)((px/displayMetrics.density) + 0.5);

    }

}