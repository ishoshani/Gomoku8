package com.example.isho.gomoku8;

/**
 * Created by isho on 2/20/17.
 */

public interface AsyncResponse {
    void finishProcess(Integer output, int aiRow, int aiCol, int row, int col);
    void handleError();
}
