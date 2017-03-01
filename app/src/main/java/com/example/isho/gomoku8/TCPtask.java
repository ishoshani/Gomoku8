package com.example.isho.gomoku8;

import android.os.AsyncTask;

/**
 * Created by isho on 2/26/17.
 */

class TCPtask extends AsyncTask<Integer,Integer,OnlineClient>{
    OnlineClient online;
    OnlineMoveProcessor delegate;

    public OnlineClient doInBackground(Integer ... move){
        online = new OnlineClient(new OnMoveReceived() {
            @Override
            public void moveReceived(int row, int col) {
                int winner;
                if(OnlineClient.isFirst) {
                    winner = GomokuLogic.placePieceforPlayer(row, col, -1);
                }else{
                    winner = GomokuLogic.placePieceforPlayer(row, col, 1);
                }
                publishProgress(row,col,winner);
            }
        });
        online.OpenConnection();
        return null;
    }
    protected void onProgressUpdate(Integer...received){
        super.onProgressUpdate(received);
        delegate.processOnlineMove(received[0],received[1],received[2]);

    }

}
