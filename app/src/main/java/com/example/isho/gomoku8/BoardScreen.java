package com.example.isho.gomoku8;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.LogWriter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class BoardScreen extends AppCompatActivity implements AsyncResponse {
    ImageButton[][] bArray;
    RelativeLayout boardView;
    int size;
    String style;
    boolean isFreeStyle;
    Icon whitePieceImage;
    Icon blackPieceImage;
    GameDialogFragment frag;
    int playerSize;

    //Timer variables
    TextView p1timerView;
    TextView p2timerView;
    Chronometer p1timer;
    Chronometer p2timer;
    Chronometer gametime;
    //int playerTurn;
    long initTime;
    long p1time;
    long p2time;
    long elapsedTime;
    long minuteTime;
    boolean minuteTimer1;
    boolean minuteTimer2;
    String p1timerText;
    String p2timerText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        whitePieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.white);
        blackPieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.black);

        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        size = bundle.getInt("boardSize");
        style = bundle.getString("gameStyle","freestyle");
        playerSize = bundle.getInt("playerSize");
        if(style.equals("Standard")){
            isFreeStyle = false;
        }else{
            isFreeStyle = true;
        }

        GomokuLogic.clearBoard(size,isFreeStyle);
        setContentView(R.layout.activity_board_screen);
        boardView = (RelativeLayout) findViewById(R.id.boardView);
        bArray = new ImageButton[size][size];

        initTime = 600000;
        p1time = 0;
        p2time = 0;
        minuteTime = 60000;
        minuteTimer1 = false;
        minuteTimer2 = false;

        p1timerView = (TextView) findViewById(R.id.timer1);
        p2timerView = (TextView) findViewById(R.id.timer2);

        resetTimer();

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

                            updateTimer();
                            resetTimer();
                        }
                        else{
                            image = blackPieceImage;

                            updateTimer();
                            resetTimer();
                        }
                        bArray[fi][fj].setImageIcon(image);
                        bArray[fi][fj].setEnabled(false);
                        localHandler.execute(fi,fj);
                    }
                });
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

    public void resetTimer() {
        ((Chronometer) findViewById(R.id.timer)).setBase(SystemClock.elapsedRealtime());
        ((Chronometer) findViewById(R.id.timer)).start();
    }

    public void updateTime(int playerTurn) {

        if (playerTurn > 0) {
            elapsedTime = SystemClock.elapsedRealtime()
                    - ((Chronometer) findViewById(R.id.timer)).getBase();

            checkTimesUp(elapsedTime, playerTurn);

            p1time += elapsedTime;
            p1timerText = formatTime(p1time);
            p1timerView.setText("Player 1 : " + p1timerText);

        } else {
            elapsedTime = SystemClock.elapsedRealtime()
                    - ((Chronometer) findViewById(R.id.timer)).getBase();

            checkTimesUp(elapsedTime, playerTurn);

            p2time += elapsedTime;
            p2timerText = formatTime(p2time);
            p2timerView.setText("Player 2 : " + p2timerText);

        }
    }

    public void updateTimer() {
        int player = GomokuLogic.getTurn();

        if (player > 0) {
            checkMinuteTime(player, p1time);
            if (!minuteTimer1) {
                updateTime(1);
            } else {
                p1timerView.setText("Player 1 : --:--");
            }
        } else {
            checkMinuteTime(player, p2time);
            if (!minuteTimer2) {
                updateTime(-1);
            } else {
                p2timerView.setText("Player 2 : --:--");
            }
        }
    }

    public void checkMinuteTime(int playerTurn, long ms) {
        if (ms >= 6000) {
            if (playerTurn > 0) {
                minuteTimer1 = true;
            } else {
                minuteTimer2 = true;
            }
        }
    }

    public void checkTimesUp(long time, int playerTurn) {
        if (minuteTimer1 || minuteTimer2) {
            if (time > minuteTime) {
                endGame(-1 * playerTurn);
            }
        }
    }

    public String formatTime(long elapsedTime) {
        int hours;
        int minutes;
        int seconds;
        String min;
        String sec;
        String formattedTime;

        hours = (int) elapsedTime / 3600000;
        minutes = (int) (elapsedTime - hours * 3600000) / 60000;
        seconds = (int) (elapsedTime - hours * 3600000 - minutes * 60000) / 1000;
        formattedTime = String.format("%02d", minutes) + ":" + String.format("%02d", seconds);

        return formattedTime;

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
    public void finishProcess(Integer output, int aiRow, int aiCol){
        if(playerSize == 1) {
            bArray[aiRow][aiCol].setEnabled(false);
            bArray[aiRow][aiCol].setImageIcon(blackPieceImage);
        }
        if(output!=0){
            endGame(output);
        }
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