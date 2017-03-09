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

    //Timer p1timer;
    //Timer p2timer;
    //View timerView;
    TextView p1timerView;
    TextView p2timerView;

    TextView timertest;

    Chronometer p1timer;
    Chronometer p2timer;
    Chronometer gametime;
    int playerTurn;
    //boolean p2;
    long initTime;
    long p1time;
    long p2time;
    long elapsedTime1;
    long elapsedTime2;
    long minuteTime;
    boolean minuteTimer1;
    boolean minuteTimer2;

    String p1timerText;
    String p2timerText;
    //String p1timerString;
    //String p2timerString;
    long p1timerValue;
    long p2timerValue;
    //Long.parseLong("<string>", 10) //base 10

    //boolean p1set;
    //boolean p2set;
    //long deltap1time;
    //long deltap2time;


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

        //p1timer.isCountDown();


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

        //timertest = (TextView) findViewById(R.id.timetester);

        //p1timer = new Timer(getApplicationContext());
        //p1timer = (Timer) findViewById(R.id.timer1);
        //p2timer = (Timer) findViewById(R.id.timer2);

        //p1timer = (Chronometer) findViewById(R.id.timer1);
        //p2timer = (Chronometer) findViewById(R.id.timer2);

        //p1timer.setBase(initTime); //SystemClock.elapsedRealtime());
        //p2timer.setBase(initTime);

        //p1timer.setBase(p1time);
        //p2timer.setBase(p2time);

        //timerView = findViewById(R.id.timer1);
        //p1timer.resume();
        //p1timer.setCurrentTimeMillis(p1time);

        //p1timer.setBase(SystemClock.uptimeMillis());
        //p1timer.setText("10:00");
        //p2set = p2timer.setBase(SystemClock.setCurrentTimeMillis(600000));
        //p2timer.setText("10:00");
        //p1timer.setBase(p1time);
        //p2timer.setBase(p2time);

        //p1timer.start(); // Chronometer timer

        //DOES NOT WORK
        //p1timer.setText("10:00");
        //p2timer.setText("10:00");

        //p1timer.setCountDown(true);

        //p1timer.start();
        //beginTimer();
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
                        //p1timer.resume();
                        if(GomokuLogic.getTurn()>0) {
                            image = whitePieceImage;
                            //p1timer.stopTimer();
                            //p2timer.startTimer(p2time);

                            /*
                            p1timer.stop();
                            p1timerText = p1timer.getText().toString();
                            p1timerValue = Long.parseLong(p1timerText, 10);

                            p2timer.start();
                            */

                            //p1time = (p1timer.getBase()/1000) - p1time;

                            //resetTime(p2timer);

                            //SINGLE TIMER
                            keepTime(1);
                            resetTimer();


                            //p2timer.setBase(p2time);
                            //p2timer.start();

                            /*
                            deltap1time = p1timer.getBase() - p1time;// SystemClock.elapsedRealtime();
                            p1time += deltap1time;
                            //checkTime(p2time);
                            //p2timer.setBase(SystemClock.elapsedRealtime());
                            //p2timer.setBase(p2time - p1time);
                            */


                            /*
                            p1timer.pause();
                            //p1timer.setBase(SystemClock.elapsedRealtime());
                            if (p2) {
                                playerTurn = 2;
                                //timerView = findViewById(R.id.timer2);
                                p2timer.resume();
                            }
                            */

                        }
                        else{
                            image = blackPieceImage;

                            /*
                            p2timer.stop();
                            p2timerText = p2timer.getText().toString();
                            p2timerValue = Long.parseLong(p2timerText, 10);

                            p1timer.start();
                            */

                            //p2timer.stopTimer();
                            //p1timer.startTimer(p1time);


                            //p2timer.stop();
                            //p2time = (p2timer.getBase()/1000) - p2time;

                            //resetTime(p1timer);

                            // SINGLE TIMER
                            keepTime(1);
                            resetTimer();


                            //p1timer.setBase(p1time);
                            //p1timer.start();


                            /*
                            deltap2time = p2timer.getBase() - p2time; //SystemClock.elapsedRealtime();
                            p2time += deltap2time;
                            //checkTime(p1time);
                            //p1timer.setCurrentTimeMillis(p1time);
                            //p1timer.setBase(p1time);
                            //p1timer.setBase(p1time - p2time);

                            resetTime(p1timer);
                            //p1timer.setBase(p1time - SystemClock.uptimeMillis()); //SystemClock.elapsedRealtime());
                            //p1timer.start();
                            */
                            /*
                            if (p2) {
                                p2timer.pause();
                                //p2timer.setBase(SystemClock.elapsedRealtime());
                            }
                            playerTurn = 1;
                            //timerView = findViewById(R.id.timer1);
                            p1timer.resume();
                            */
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
        ///////if()
        /*
        if(p1timer.isActivated()) {
            p1timer.stop();
        }
        if(p2timer.isActivated()) {
            p2timer.stop();
        }
        */
    }

    public void resetTimer() {
        ((Chronometer) findViewById(R.id.timer)).setBase(SystemClock.elapsedRealtime());
        ((Chronometer) findViewById(R.id.timer)).start();
    }

/*
    public void resetTimer(int playerTurn) {

        if(playerTurn > 0) {

            elapsedTime1 = SystemClock.uptimeMillis() -
                    ((Chronometer) findViewById(R.id.timer)).getBase();
            p1time += elapsedTime1;

            ((Chronometer) findViewById(R.id.timer)).setBase(p1time);
            ((Chronometer) findViewById(R.id.timer)).start();

            timertest.setText(String.valueOf(p1time));
            Log.d("p1time", String.valueOf(p1time));

        } else {

            elapsedTime2 = SystemClock.uptimeMillis() -
                    ((Chronometer) findViewById(R.id.timer)).getBase();
            p2time += elapsedTime2;

            ((Chronometer) findViewById(R.id.timer)).setBase(p2time);
            ((Chronometer) findViewById(R.id.timer)).start();

            timertest.setText(String.valueOf(p2time));
            Log.d("p1time", String.valueOf(p2time));

        }
    }
    */

/*
    public void resetTimer1() {
        ((Chronometer) findViewById(R.id.timer1)).setBase(SystemClock.elapsedRealtime());
        ((Chronometer) findViewById(R.id.timer1)).start();
    }

    public void resetTimer2() {
        ((Chronometer) findViewById(R.id.timer2)).setBase(SystemClock.elapsedRealtime());
        ((Chronometer) findViewById(R.id.timer2)).start();
    }
*/


    public void keepTime(int playerTurn) {
        if (playerTurn > 0) {
            p1time = ((Chronometer) findViewById(R.id.timer)).getBase();
            p1time /= 1000;
            p1time += p1timerValue;

            //p1timerView = (TextView) findViewById(R.id.timer1);
            //p1timerView.setText("Player 1: " + Long.toString(p1time));

            //p1timerText = p1timer.getText().toString();

            //p1timerString.toString();
            //p1timerValue = Long.parseLong(p1timerText, 10);

            //p1time += p1timerValue;

            //timertest.setText(String.valueOf(p1time));
            //Log.d("p1time", String.valueOf(p1time));
            //System.out.println(p1time);
        } else {
            p2time = ((Chronometer) findViewById(R.id.timer)).getBase();
            p2time /= 1000;
            p2time += p2timerValue;

            //p2timerView = (TextView) findViewById(R.id.timer2);
            //p2timerView.setText("Player 2: " + Long.toString(p2time));

            //p2timerText = ((Chronometer) findViewById(R.id.timer2)).getText().toString();

            //p2timerValue = Long.parseLong(p2timerText, 10);

            //p2time += p1timerValue;

            //System.out.println(p2time);
            //timertest.setText(String.valueOf(p2time));
            //Log.d("p2time", String.valueOf(p2time));
        }
    }

    /*
    p1timerText = p1timer.getText().toString();
    p1timerValue = Long.parseLong(p1timerText, 10);

    public void keepTime(int playerTurn) {
        if(playerTurn > 0) {
            elapsedTime = SystemClock.e    public void keepTime(int playerTurn) {
        if(playerTurn > 0) {
            elapsedTime = SystemClock.elapsedRealtime() -
                    ((Chronometer) findViewById(R.id.timer)).getBase();
            p1time += elapsedTime;

            timertest.setText(String.valueOf(p1time));
            Log.d("p1time", String.valueOf(p1time));
            //System.out.println(p1time);
        } else {
            elapsedTime = SystemClock.elapsedRealtime() -
                    ((Chronometer) findViewById(R.id.timer)).getBase();
            p2time += elapsedTime;

            //System.out.println(p2time);
            timertest.setText(String.valueOf(p2time));
            Log.d("p2time", String.valueOf(p2time));
        }
    }lapsedRealtime() -
                    ((Chronometer) findViewById(R.id.timer)).getBase();
            p1time += elapsedTime;

            timertest.setText(String.valueOf(p1time));
            Log.d("p1time", String.valueOf(p1time));
            //System.out.println(p1time);
        } else {
            elapsedTime = SystemClock.elapsedRealtime() -
                    ((Chronometer) findViewById(R.id.timer)).getBase();
            p2time += elapsedTime;

            //System.out.println(p2time);
            timertest.setText(String.valueOf(p2time));
            Log.d("p2time", String.valueOf(p2time));
        }
    }
    */

/*
    public long checkTime(long playerTime) {
        System.out.println("checkTime value: " + playerTime);
        long timeSet;
        int otherPlayer;

        timeSet = 0;
        otherPlayer = GomokuLogic.getTurn() * -1;
        if (minuteTimer) {
            if (playerTime == 0) {
                // store win
                // Player.addWin(otherPlayer)

                // player loses game
                endGame(otherPlayer);
            }
            timeSet = minuteTime;
        }
        //if (playerTime > 0) {
        //    timeReset = playerTime;
        //}
        if (!minuteTimer) {
            timeSet = playerTime;
        }

        return timeSet;
    }
*/

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