package com.example.isho.gomoku8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Random;

/**
 * Created by Jason on 2/24/2017.
 */

public class SinglePlayerAI{
    // static list of previous moves
    static int[] lastAImove = new int[2];
    int playerRow;
    int playerCol;

    public SinglePlayerAI(int playerRow, int playerCol) {
        this.setPlayerMove(playerRow, playerCol);
    }

    public void setPlayerMove(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
    }
    // input: current player move
    // return: a array of moves, [0] is row, [1] is col;
    public int[] aiMove() {
        int[] move = nearWin();
        if (move == null)
            move = regularMove();
        return move;
    }
    public int[] blockMove() {
        return null;
    }

    // return a possible move if player move was not a near win
    private int[] regularMove(){
        Random rn = new Random();
        int[] move = new int[2];
//        int row = this.playerRow;
//        int col = this.playerCol;
        // for now place a piece around player's last move
//        while (isDisabled(row, col) == false) {
            int row = rn.nextInt(9);
            int col = rn.nextInt(9);
//        }
        move[0] = row;
        move[1] = col;

        return move;
    }

    // if player is near win return the position else return null;
    // Need to check at least two steps ahead. If player already has 3 in road.
    //
    private int[] nearWin() {
        int[] move = new int[2];
        int row = this.playerRow;
        int col = this.playerCol;
        move[0] = row;
        move[1] = col;
//        // check adjacent position for possible wins
//        if (checkColRight(row, col)) {
//            Log.d("DEBUG WIN", "col win right detected");
//            move[1] += 1;
//        }
        if (checkCol(row, col)){
            Log.d("DEBUG WIN", "col win right detected");
            if (GomokuLogic.boardMatrix[row][col+1] == 0) {
                Log.d("DEBUG WIN", "isDisabled " + isDisabled(row, col + 1));
                move[1] = col + 1;
            }
            else
                move[1] = col - 1;
        }
        else if (checkRow(row, col)) {
            Log.d("DEBUG WIN", "row win right detected");
            if (GomokuLogic.boardMatrix[row+1][col] == 0) {
                Log.d("DEBUG WIN", " isDisabled " + isDisabled(row, col + 1));
                move[0] = row + 1;
            }
            else
                move[0] = row - 1;
        }
        else
            return null;
//        GomokuLogic.boardMatrix[row][col+1] = 0;


        lastAImove[0] = row;
        lastAImove[1] = col;

        return move;

    }

    private boolean checkDiagonalFoward(int row, int col) {
        int boardSize = GomokuLogic.size;
        boolean ret = false;
        int pieces = 0;
        Log.d("col win check", " peices of white: " + pieces);
        for (int i = row; i < GomokuLogic.size; i++) {
            for (int j = col; j < GomokuLogic.size; j++) {
                if (GomokuLogic.boardMatrix[i][col] == 1) {
                    pieces++;
                }

                if (pieces >= 3) {
                    ret = true;
                    Log.d("col win check", " peices of white: " + pieces);
                }
            }
        }
//        GomokuLogic.boardMatrix[row][col] = original;
        return ret;
    }

    private boolean checkDiagonalBack(int row, int col) {
        return true;
    }

    private boolean checkRow(int row, int col) {
        boolean ret = false;
        int pieces = 0;
        Log.d("col win check", " peices of white: " + pieces);
        for (int i = 0; i < GomokuLogic.size; i++){
            if (GomokuLogic.boardMatrix[i][col] == 1){
                pieces++;
            }

            if (pieces >= 3) {
                ret = true;
                Log.d("col win check", " peices of white: " + pieces);
            }
        }

//        GomokuLogic.boardMatrix[row][col] = original;
        return ret;
    }

    private boolean checkCol(int row, int col) {
        boolean ret = false;
//        int original = 0;
//        int resetRow = row;
//        int resetCol = col;
//
//        if (isDisabled(row,col+1) &&
//                isDisabled(row, col-1)) {
//            return false;
//            GomokuLogic.boardMatrix[row][col+1] = 1;
//            original = GomokuLogic.boardMatrix[row][col+1];
//        }
//        else if (isDisabled(row, col+1)){
//
//        }
        int pieces = 0;
        Log.d("rowCheck", " peices of white: " + pieces);
        for (int i = 0; i < GomokuLogic.size; i++){
            if (GomokuLogic.boardMatrix[row][i] == 1){
                pieces++;
            }
            if (pieces >= 3) {
                ret = true;
                Log.d("rowCheck", " peices of white: " + pieces);
            }
        }

//        GomokuLogic.boardMatrix[row][col] = original;
        return ret;
    }

    private boolean checkColRight(int row, int col) {
        boolean ret = false;
        GomokuLogic.boardMatrix[row][col+1] = 1;

        if (isWin(row, col+2)) {
            Log.d("DEBUG WIN", "col win detected");
            ret = true;
        }
        GomokuLogic.boardMatrix[row][col+1] = 0;

        return ret;
    }
    // check if adjacent pieces are wins
    // need to check two moves ahead
    public boolean isWin(int row, int col) {
        Log.d("isWin check", " Row " + row + " Col " + col);
        int original = GomokuLogic.boardMatrix[row][col];
        boolean ret = false;
        // check for edges

        if (isDisabled(row, col) == false) {
            GomokuLogic.boardMatrix[row][col] = 1;
            GomokuLogic.turn = 1;
            Log.d("DEBUG STATE", " isDisabled " + GomokuLogic.isWin(row, col));
            if (GomokuLogic.isWin(row, col) == 1) {
                ret = true;
            }

        }
        Log.d("iswin check", "isWin  is : " + ret);
        GomokuLogic.boardMatrix[row][col] = original; // reset the board position
        GomokuLogic.turn *=-1; // reset the turn
        return ret;
    }

    // check if a position is already taken
    private boolean isDisabled(int row, int col) {
        if (GomokuLogic.boardMatrix[row][col] != 0)
            return true;
        return false;
    }

    // check for out of bound
    private boolean isEdge(int playerRow, int playerCol) { return false;
    }

}
