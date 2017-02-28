package com.example.isho.gomoku8;

import android.content.Intent;
import android.os.AsyncTask;

/**
 * Created by isho on 2/20/17.
 */

class GomokuHandler extends AsyncTask<Integer,Void,Integer> {
    public AsyncResponse delegate = null;
    public boolean isAI = false;
    int aiRow = 0, aiCol = 0 ;

    @Override
    protected Integer doInBackground(Integer... position){
        int row = position[0];
        int col = position[1];
        GomokuLogic.testPiece(row,col);
        GomokuLogic.turnsTaken++;
        Integer winner = GomokuLogic.isWin(row,col);
        GomokuLogic.turn*=-1;
        if (isAI && winner == 0) {
         //   SinglePlayerAI AI = new SinglePlayerAI();
         //   AI.aiMove(row, col);
            GomokuLogic.turnsTaken++;
            GomokuLogic.testPiece(row+1,col+1);
            winner = GomokuLogic.isWin(row+1, col+1);
            aiRow = row+1;
            aiCol = col+1;
            GomokuLogic.turn*=-1;
        }
        return winner;
    }
    @Override
    protected void onPostExecute(Integer result){

        delegate.finishProcess(result, aiRow, aiCol);
    }

}
