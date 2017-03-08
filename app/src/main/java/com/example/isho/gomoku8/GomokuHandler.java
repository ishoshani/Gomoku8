package com.example.isho.gomoku8;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by isho on 2/20/17.
 */

class GomokuHandler extends AsyncTask<Integer,Void,Integer> {
    public AsyncResponse delegate = null;
    public boolean isAI = false;
    public boolean isOnline = false;

    int aiRow = 0, aiCol = 0 ;

    @Override
    protected Integer doInBackground(Integer... position){
        int row = position[0];
        int col = position[1];
        Integer winner=0;
        if(isOnline){
            if(OnlineClient.isFirst){
                GomokuLogic.placePieceforPlayer(row,col,1);
            }else{
                GomokuLogic.placePieceforPlayer(row,col,-1);
            }
        }else {
            winner = GomokuLogic.placePiece(row, col);
        }
        SinglePlayerAI AI = new SinglePlayerAI(row, col);
        if (isAI && winner == 0) {
            //AI.aiMove(row, col);
            AI.setPlayerMove(row, col);
            Log.d("GomokuHandler Debug", "Player move was " + row + " " + col);
            int[] aiMoves = AI.aiMove();
            aiRow = aiMoves[0];
            aiCol = aiMoves[1];
            Log.d("GomokuHandler Debug", "genereated AI move was " + aiRow + " " + aiCol);
            winner = GomokuLogic.placePiece(aiRow, aiCol);
        }
        return winner;
    }
    @Override
    protected void onPostExecute(Integer result){
        delegate.finishProcess(result, aiRow, aiCol);
    }

}
