package com.example.isho.gomoku8;

/**
 * Created by isho on 2/26/17.
 */

public interface OnlineMoveProcessor {
    void processOnlineMove(int row, int col, int win);
    void showConnectionStart();
    void handleProblem(int type);//1 for Player left, 2 for Server Error

}
