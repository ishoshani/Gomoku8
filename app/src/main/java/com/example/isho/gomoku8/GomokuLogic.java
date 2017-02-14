package com.example.isho.gomoku8;

import android.provider.Settings;
import android.util.Log;

/**
 * Created by Jason on 2/12/2017.
 */

public class GomokuLogic {
    static int[][] boardMatrix;
    static int size;
    static int turn;//goes between 1 and -1 two decide between players
    public GomokuLogic(int n) {
        boardMatrix = new int[n][n];
        size = n;
        turn = 1;
    }
    static public void clearBoard(int n){
        turn =1 ;
        size = n;
        boardMatrix = new int[n][n];
        for (int i=0; i<size; i++){
            for(int j=0;j<size;j++){
                boardMatrix[i][j]=0;
            }
        }
    }
    static public int getTurn(){
        return turn;
    }
    static public void testPiece(int i, int j){
        boardMatrix[i][j]= turn;
    }
    static public void placePiece(int i, int j){
        Log.d("gameLogic","placed piece at "+i+" and "+j);

        boardMatrix[i][j]= turn;
        turn *= -1;
    }
    /*
    goes over board to check for a win. return 1 for white win, -1 for black win, return 0 for no win. 3 for draw.
     */
    private static int checkColumns(){
        for (int i = 0; i < size ; i++) {
            int inARow = 0;
            for(int j = 1; j < size ; j++){
                if (boardMatrix[i][j-1] == 1) {
                    if(boardMatrix[i][j]==1){
                        inARow++;
                        if(inARow>3){return 1;}
                    }else{
                        inARow =0;
                    }
                }else{
                    inARow = 0;
                }
            }
        }
        for (int i = 0; i < size ; i++) {
            int inARow = 0;
            for(int j = 1; j < size ; j++){
                if (boardMatrix[i][j-1] == -1) {
                    if(boardMatrix[i][j]==-1){
                        inARow++;
                        if(inARow>3){return -1;}
                    }else{
                        inARow=0;
                    }
                }else{
                    inARow =0;
                }
            }
        }

        return 0;
    }
    private static int checkRows(){
        for (int j = 0; j < size ; j++) {
            int inARow = 0;
            for (int i = 1; i < size; i++) {
                if (boardMatrix[i - 1][j] == 1) {
                    if (boardMatrix[i][j] == 1) {
                        inARow++;
                        if (inARow > 3) {
                            return 1;
                        }
                    } else {
                        inARow = 0;
                    }

                } else {
                    inARow = 0;
                }
            }
        }
        for (int j = 0; j < size ; j++) {
            int inARow = 0;
            for(int i = 1; i < size ; i++){
                if (boardMatrix[i-1][j] == -1) {
                    if(boardMatrix[i][j]==-1){
                        inARow++;
                        if(inARow>3){return -1;}
                    }else{
                        inARow =0;
                    }
                }else{
                    inARow = 0;
                }
            }
        }

        return 0;
    }
    static public int checkForwardDiagonal(int row, int col){
        int inArow=0;
        for(int i = 1; i<(size-row-col); i++){
            if(boardMatrix[row+i-1][col+i-1]==1){
                if(boardMatrix[row+i][col+i]==1){
                    inArow++;
                            if(inArow>3){return 1;}
                }else {
                inArow =0;
                }
            }else{
                inArow=0;
            }
        }
        inArow =0;
        for(int i = 1; i<(size-row-col-1); i++){
            if(boardMatrix[row+i-1][col+i-1]==-1){
                if(boardMatrix[row+i][col+i]==-1){
                    inArow++;
                    if(inArow>3){return -1;}
                }else {
                    inArow =0;
                }
            }else{
                inArow=0;
            }
        }
    return 0;
    }
    static public int checkBackDiagonal(int row, int col){
        int inArow=0;
        for(int i = 1; i<(col+1-row); i++){
            if(boardMatrix[row+i-1][col-i+1]==1){
                if(boardMatrix[row+i][col-i]==1){
                    inArow++;
                    if(inArow>3){return 1;}
                }else {
                    inArow =0;
                }
            }else{
                inArow=0;
            }
        }
        inArow =0;
        for(int i = 1; i<(col+1-row); i++){
            if(boardMatrix[row+i-1][col-i+1]==-1){
                if(boardMatrix[row+i][col-i]==-1){
                    inArow++;
                    if(inArow>3){return -1;}
                }else {
                    inArow =0;
                }
            }else{
                inArow=0;
            }
        }
        return 0;
    }
    static public int checkAllDiagonals(){
        int win =checkForwardDiagonal(0,0);
        if(win!=0){return win;}
        win = checkBackDiagonal(0,size-1);
        if(win!=0){return win;}

        for (int i = 1; i < size; i++) {
            win=checkForwardDiagonal(0,i);
            if(win!=0){return win;}
            win =checkForwardDiagonal(i,0);
            if(win!=0){return win;}
            win = checkBackDiagonal(i,size-1);
            if(win!=0){return win;}
            win =checkBackDiagonal(0,size-1-i);
            if(win!=0){return win;}
        }
        return 0;
    }
    static public int checkWin(){
        int ColsWin = checkColumns();
        if(ColsWin!=0){
            return ColsWin;
        }
        int RowsWin = checkRows();
        if(RowsWin != 0){
            return RowsWin;
        }
        return 0;
    }

}
