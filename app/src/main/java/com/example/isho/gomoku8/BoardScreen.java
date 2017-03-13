package com.example.isho.gomoku8;

import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.LogWriter;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class BoardScreen extends AppCompatActivity implements AsyncResponse {
    ImageButton[][] bArray;
    RelativeLayout boardView;
    LinearLayout bGrid;
    int size, lsize, playerSize, numRounds;
    int currentRound;
    int whiteWins;
    int blackWins;
    String style;
    boolean isFreeStyle = true;
    Icon whitePieceImage, blackPieceImage;
    TextView RoundCounter;
    TextView WinRecorder;
    GameDialogFragment frag;
    TextView playerTurn;
    ImageView playerTurnPiece;
    RoundEndFragmentDialog fragR;

    //Timer variables
    TextView p1timerView, p2timerView;
    long initTime, p1time, p2time, elapsedTime, minuteTime;
    boolean minuteTimer1, minuteTimer2;
    String p1timerText, p2timerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Import menu settings and game style
        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        size = bundle.getInt("boardSize");
        style = bundle.getString("gameStyle","freestyle");
        numRounds = bundle.getInt("numRounds");
        playerSize = bundle.getInt("playerSize");
        if(style.equals("Standard")){
            isFreeStyle = false;
        }



        setContentView(R.layout.activity_board_screen);


        //MultiRound win recorder and counter
        RoundCounter = (TextView)findViewById(R.id.RoundCounter);
        WinRecorder = (TextView)findViewById(R.id.WinRecorder);
        whiteWins=0;
        blackWins=0;
        currentRound =1;
        updateRecorder();
        bGrid = new LinearLayout(getApplicationContext());
        bGrid = (LinearLayout) findViewById(R.id.boardGrid);
        int layoutWidth = 335;

        // Dynamic board/piece sizing

        /*
        int screenWidth = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        bGrid.measure(screenWidth, screenHeight);
        int layoutWidth = bGrid.getMeasuredWidth();
        bGrid.setLayoutParams(new LinearLayout.LayoutParams(layoutWidth, layoutWidth));
        */
        if(size==10){
            lsize = (dpToPX(layoutWidth)/10)-6;
            bGrid.setBackgroundResource(R.drawable.grid10);
            whitePieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.white); //30
            blackPieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.black);
        }
        else if(size==15){
            lsize = dpToPX(layoutWidth)/15;
            bGrid.setBackgroundResource(R.drawable.grid15);
            whitePieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.white20); //20
            blackPieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.black20);
        }
        else { //20x20
            lsize = dpToPX(layoutWidth)/20;
            bGrid.setBackgroundResource(R.drawable.grid20);
            whitePieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.white14); //10?
            blackPieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.black14);
        }

        // Create board and dynamically create buttons for each space
        GomokuLogic.clearBoard(size,isFreeStyle);
        boardView = (RelativeLayout) findViewById(R.id.boardView);
        playerTurn = (TextView) findViewById(R.id.currentPlayerTurn);
        playerTurnPiece = (ImageView) findViewById(R.id.currentPlayerImage);
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
                            if(playerSize == 2) {
                                playerTurn.setText(R.string.Player2);
                                playerTurnPiece.setImageResource(R.drawable.black);
                            }
                            updateTimer();
                            resetTimer();
                        }
                        else{
                            image = blackPieceImage;
                            playerTurn.setText(R.string.Player1);
                            playerTurnPiece.setImageResource(R.drawable.white);

                            updateTimer();
                            resetTimer();
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

    // For updating the displayed player turn in AI mode

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
                elapsedTime = SystemClock.elapsedRealtime()
                        - ((Chronometer) findViewById(R.id.timer)).getBase();
                checkTimesUp(elapsedTime, player);
                p1timerView.setText("Player 1 : --:--");
            }
        } else {
            checkMinuteTime(player, p2time);
            if (!minuteTimer2) {
                updateTime(-1);
            } else {
                elapsedTime = SystemClock.elapsedRealtime()
                        - ((Chronometer) findViewById(R.id.timer)).getBase();
                checkTimesUp(elapsedTime, player);
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
            Log.i("time","someone is on minute timer");
            Log.i("time", "elapsedTime is "+time );
            if (time > minuteTime) {
                endGame(-1 * playerTurn);
            }
        }
    }

    public String formatTime(long elapsedTime) {
        int hours, minutes, seconds;
        String formattedTime;

        hours = (int) elapsedTime / 3600000;
        minutes = (int) (elapsedTime - hours * 3600000) / 60000;
        seconds = (int) (elapsedTime - hours * 3600000 - minutes * 60000) / 1000;
        formattedTime = String.format("%02d", minutes) + ":" + String.format("%02d", seconds);

        return formattedTime;

    }

    public void endGame(int winner) {
        String player;
        if (winner == 1){
            whiteWins++;
            player = "Player 1";
    }
        else if(winner ==-1) {
            blackWins++;
            player = "Player 2";
        }else{
            player = "tie";
        }
        if(blackWins>numRounds/2 || whiteWins>numRounds/2) {
            showFinalDialog(player);
        }
        else{
            showRoundDialog(player);
        }
    }

    // call this method to show dialog
    private void showFinalDialog(String args) {
        FragmentManager fm = getSupportFragmentManager();
        if(numRounds==1) {
            frag = GameDialogFragment.newInstance("Winner: " + args);
        }else{
            frag = GameDialogFragment.newInstance("Final Winner: " + args);
        }
        frag.show(fm, "activity_end_game_dialog");
    }
    private void showRoundDialog(String args){
        FragmentManager fm = getSupportFragmentManager();
        fragR = RoundEndFragmentDialog.newInstance("Round Winner: " + args);
        fragR.show(fm, "activity_end_game_dialog");
    }
    public void nextRound(){
        GomokuLogic.clearBoard(size, isFreeStyle);
        currentRound++;
        updateRecorder();
        resetTimer();
        minuteTimer1 = false;
        minuteTimer2 = false;
        p1time = 0;
        p2time = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                bArray[i][j].setImageIcon(null);
                bArray[i][j].setBackgroundColor(Color.TRANSPARENT);
                bArray[i][j].setEnabled(true);
            }
        }
        fragR.dismiss();
    }

    public void resetMatch() {
        GomokuLogic.clearBoard(size, isFreeStyle);
        whiteWins =0;
        blackWins =0;
        currentRound =1;
        updateRecorder();
        resetTimer();
        minuteTimer1 = false;
        minuteTimer2 = false;
        p1time = 0;
        p2time = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                bArray[i][j].setImageIcon(null);
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
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return (int)((dp * metrics.density)+0.5);
    }
    public int pxToDP(int px) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return (int)((px/metrics.density)+0.5);
    }
    public void updateRecorder(){
        String roundText = currentRound+" out of "+numRounds;
        String winText ="White  "+ whiteWins+" : "+blackWins+"  Black";
        RoundCounter.setText(roundText);
        WinRecorder.setText(winText);
    }

}