package com.example.isho.gomoku8;

import android.content.Intent;
import android.os.AsyncTask;

/**
 * Created by isho on 2/20/17.
 */

class GomokuHandler extends AsyncTask<Integer,Void,Integer> {
    public AsyncResponse delegate = null;
    @Override
    protected Integer doInBackground(Integer... position){
        int row = position[0];
        int col = position[1];
        GomokuLogic.testPiece(row,col);
        GomokuLogic.turnsTaken++;
        Integer winner = GomokuLogic.isWin(row,col);
        GomokuLogic.turn*=-1;
        return winner;
    }
    @Override
    protected void onPostExecute(Integer result){
        delegate.finishProcess(result);
    }

}
