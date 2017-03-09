package com.example.isho.gomoku8;

import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        /*
        int screenWidth = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        bGrid.measure(screenWidth, screenHeight);
        int layoutWidth = bGrid.getMeasuredWidth();
        bGrid.setLayoutParams(new LinearLayout.LayoutParams(layoutWidth, layoutWidth));
        */
        if(size==10){
            lsize = 85; // do not change
            bGrid.setBackgroundResource(R.drawable.grid10);
            whitePieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.white); //30
            blackPieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.black);
        }
        else if(size==15){
            lsize = 58;
            bGrid.setBackgroundResource(R.drawable.grid15);
            whitePieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.white20); //20
            blackPieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.black20);
        }
        else { //20x20
            lsize = 44; //dpToPX(300/20)
            bGrid.setBackgroundResource(R.drawable.grid20);
            whitePieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.white14); //10?
            blackPieceImage = Icon.createWithResource(getApplicationContext(),R.drawable.black14);
        }

        // Create board and dynamically create buttons for each space
        GomokuLogic.clearBoard(size,isFreeStyle);
        boardView = (RelativeLayout) findViewById(R.id.boardView);
        final TextView playerTurn = (TextView) findViewById(R.id.currentPlayerTurn);
        final ImageView playerTurnPiece = (ImageView) findViewById(R.id.currentPlayerImage);
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
                            playerTurn.setText(R.string.Player2);
                            playerTurnPiece.setImageResource(R.drawable.black);
                        }
                        else{
                            image = blackPieceImage;
                            playerTurn.setText(R.string.Player1);
                            playerTurnPiece.setImageResource(R.drawable.white);
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
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return (int)((dp * displayMetrics.density) + 0.5);
    }
    public int pxToDP(int px) {
        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
        return (int)((px/displayMetrics.density) + 0.5);

    }

}