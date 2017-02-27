package com.example.isho.gomoku8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Random;

/**
 * Created by Jason on 2/24/2017.
 */

public class SinglePlayerAI extends AppCompatActivity{
    int[][] boardMatrix;
    int boardSize;

    public int[] aiMove(int playerI, int playerJ) {
        // int[0] is i and int[1] is j
        int[] moves = new int[2];
        Random rn = new Random();

        return moves;
    }

    // if generated mvoe is already taken
    public boolean isDisabled(int i, int j) {
        if (GomokuLogic.boardMatrix[i][j] != 0)
            return true;
        return false;
    }

    public boolean nearWin(int pi, int pj) {
        int isWin;
        int [][] boardClone = GomokuLogic.boardMatrix.clone();

        // place a test piece
        // check GokomuLogic.IsWin
        // reset the test pieces. To normal
        GomokuLogic.placePiece(pi, pj++);  // need to check edges
        if (GomokuLogic.isWin(pi, pj++) == 1) {
            GomokuLogic.boardMatrix[pi][pj++] = 0;
            return true;
        }

        GomokuLogic.placePiece(pi++, pj);
        if(GomokuLogic.isWin(pi++, pj) == 1){

        }

        return false;

    }

}
