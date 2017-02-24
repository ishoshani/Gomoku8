package com.example.isho.gomoku8;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.graphics.Color;

/**
 * Created by isho on 2/15/17.
 */

public class BoardPrototype {
    private static ImageButton [][] blankBoard;
    private static int lastSize;
    public static ImageButton[][] getBoard(int size, RelativeLayout boardView, final Context appContext, BoardScreen thisAct){

        final BoardScreen activity = thisAct;
        blankBoard = new ImageButton[size][size];
        for (int i =0; i<size; i++){
            for(int j = 0; j<size; j++){
                final int fi = i;
                final int fj = j;
                blankBoard[i][j]= new ImageButton(appContext);
                blankBoard[i][j].setId(View.generateViewId());
                blankBoard[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Icon image;
                        if(GomokuLogic.getTurn()>0) {
                            image = Icon.createWithResource(appContext,R.drawable.white);
                        }
                        else{
                            image = Icon.createWithResource(appContext,R.drawable.black);
                        }
                        blankBoard[fi][fj].setImageIcon(image);
                        blankBoard[fi][fj].setEnabled(false);
                        GomokuLogic.placePiece(fi,fj);
                        int winner = GomokuLogic.isWin(fi,fj);
                        if(winner != 0){
                            activity.endGame(winner);
                        }

                    }
                });
                blankBoard[i][j].setBackgroundColor(Color.TRANSPARENT);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
                if(i==0) {
                    params.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);
                }else{
                    params.addRule(RelativeLayout.BELOW,blankBoard[i-1][j].getId());
                }
                if(j==0){
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
                }else{
                    params.addRule(RelativeLayout.RIGHT_OF,blankBoard[i][j-1].getId());
                }
              //  boardView.setBackgroundColor(Color.TRANSPARENT);
                boardView.addView(blankBoard[i][j]);

            }
        }
        lastSize = size;
        return blankBoard.clone();
    }
    static public void addArrayToView(ImageButton[][] bArray, RelativeLayout boardView){
        for (int i = 0; i < lastSize; i++) {
            for (int j = 0; j < lastSize; j++) {
                boardView.addView(bArray[i][j]);
            }
        }
    }
}
