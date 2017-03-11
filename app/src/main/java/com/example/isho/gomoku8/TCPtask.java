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
                publishProgress(1,row,col,winner);
            }
            @Override
            public void ConnectionComplete(){
                publishProgress(2);
            }
            public void onConnectionProblem(int type){
                publishProgress(3,type);
            }
        }

        );
        online.OpenConnection();
        return null;
    }
    protected void onProgressUpdate(Integer...received){
        super.onProgressUpdate(received);
        if(received[0]==1) {
            delegate.processOnlineMove(received[1], received[2], received[3]);
        }
        if(received[0]==2){
            delegate.showConnectionStart();
        }
        if(received[0]==3){
            delegate.handleProblem(1);
        }

    }

}
