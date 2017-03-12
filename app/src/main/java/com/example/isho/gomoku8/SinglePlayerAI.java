package com.example.isho.gomoku8;

import android.util.Log;
import java.util.Random;

/**
 * Created by Jason on 2/24/2017.
 */


public class SinglePlayerAI{

    int[] lastAImove = new int[2];

    int playerRow;
    int playerCol;
    int boardSize = GomokuLogic.size;

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
        boolean first = true;
        int[] move = nearWin();
        if (move == null)
            move = regularMove();
        lastAImove = move;

        return move;
    }

    // put a piece next to the adjacent player slot
    private int[] offense(){
        return null;
    }

    // return a possible move if player move was not a near win
    private int[] regularMove(){
        Random rn = new Random();
        int[] move = new int[2];
        int i = 0;
        int j = 0;
//        int row = this.playerRow;
//        int col = this.playerCol;
        // for now place a piece around player's last move
        while (isDisabled(i, j) == true) {
            i = rn.nextInt(9);
            j = rn.nextInt(9);
//            Log.d("DEBUG regular move", "i " +i+" j "+j);
        }
        move[0] = i;
        move[1] = j;

        return move;
    }


    // if player is near win return the position else return null;
    // Need to check at least two steps ahead. If player already has 3 in road.
    private int[] nearWin() {
        int[] move = new int[2];
        int row = this.playerRow;
        int col = this.playerCol;
        move[0] = row;
        move[1] = col;
//        Log.d("DEBUG EDGE CHECK", "isEdge was " + isEdge(row, col));
        if (checkCol(row, col)){
            if (isEdge(row, col) != null){
                move = isEdge(row, col);
            }
            else if (GomokuLogic.boardMatrix[row][col+1] == 0) {
                move[1] = col + 1;
            } else if (GomokuLogic.boardMatrix[row][col+1] == 1
                            && GomokuLogic.boardMatrix[row][col-1] == 1) {
                // find left most open move
                int j = col;
                while (GomokuLogic.boardMatrix[row][j] != 0) {
                    j--;
                }
                move[1] = j;
            }
            else
                move[1] = col - 1;
        }
        else if (checkRow(row, col)) {
            if (isEdge(row, col) != null){
                move = isEdge(row, col);
            }
            else if (GomokuLogic.boardMatrix[row+1][col] == 0) {
                move[0] = row + 1;
            } else if (GomokuLogic.boardMatrix[row+1][col] == 1
                    && GomokuLogic.boardMatrix[row-1][col] == 1) {
                // find upper most move
                int i = row;
                while (GomokuLogic.boardMatrix[i][col] != 0) {
                    i--;
                }
                move[0] = i;
            }
            else
                move[0] = row - 1;
        }
        else if (checkDiagonalRight(row, col)) {
            if (diagonalRightEdgeBlock(row, col) != null){
                move = diagonalRightEdgeBlock(row, col);
//                Log.d("DEBUG diagonal check", " edge block");
            }
            else if (GomokuLogic.boardMatrix[row+1][col+1] == 0) {
                move[0] = row + 1;
                move[1] = col + 1;
            } else if (GomokuLogic.boardMatrix[row+1][col+1] == 1
                    && GomokuLogic.boardMatrix[row-1][col-1] == 1) {
                // find bottom left most move
                int i = row;
                int j = col;
                while (GomokuLogic.boardMatrix[i][j] != 0) {
                    i--;
                    j--;
                }
                move[0] = i;
                move[1] = j;
            }
            else {
                move[0] = row - 1;
                move[1] = col - 1;
            }
        }
        else if (checkDiagonalLeft(row, col)) {
            if (diagonalLeftEdgeBlock(row, col) != null){
                move = diagonalLeftEdgeBlock(row, col);
            }
            else if (GomokuLogic.boardMatrix[row-1][col+1] == 0) {
                move[0] = row - 1;
                move[1] = col + 1;
            } else if (GomokuLogic.boardMatrix[row-1][col+1] == 1
                    && GomokuLogic.boardMatrix[row+1][col-1] == 1) {
                // find bottom left most move
                int i = row;
                int j = col;
                while (GomokuLogic.boardMatrix[i][j] != 0) {
                    i++;
                    j--;
                }
                move[0] = i;
                move[1] = j;
            }
            else {
                move[0] = row + 1;
                move[1] = col - 1;
            }
        }
        else
            return null;
//        GomokuLogic.boardMatrix[row][col+1] = 0;

        lastAImove[0] = row;
        lastAImove[1] = col;

        return move;

    }

    private boolean checkDiagonalLeft(int row, int col) {
        boolean ret = false;
        int i = row, j = col;
        int pieces = -1; // -1 because it starts at players current piece

        // check diagonally left to right
        while (i > 0 ) {
            if (j >= this.boardSize)
                break;
//            Log.d("DEBUG: Left to right", " i: " + i + " j: " + j);
            if (GomokuLogic.boardMatrix[i][j] == 1) {
//            Log.d("diagonal win check", " peices of white: " + pieces);
                pieces++;
            }
            i--;
            j++;
        }
        i = row; j = col;
        // check diagonally right to left
        while (j > 0) {
            if (i >= this.boardSize)
                break;
//            Log.d("DEBUG: Right to Left", " i: " + i + " j: " + j);
            if (GomokuLogic.boardMatrix[i][j] == 1) {
//                Log.d("diagonal win check", " peices of white: " + pieces);
                pieces++;
            }
            i++;
            j--;
        }
        if (pieces >= 3) {
            ret = true;
//            Log.d("diagonal win check", " peices of white: " + pieces);
        }
        return ret;
    }

    private boolean checkDiagonalRight(int row, int col) {
        boolean ret = false;
        int i = row, j = col;
        int pieces = -1; // -1 because it starts at players current piece

        // check forward
        while (i <= this.boardSize-1 && j <= this.boardSize-1) {
//            Log.d("DEBUG: Right to Left", " i: " + i + " j: " + j);
            if (GomokuLogic.boardMatrix[i][j] == 1) {
//                Log.d("diagonal win check", " peices of white: " + pieces);
                pieces++;
            }
            i++;
            j++;
//            Log.d("diagonal F win check", " peices of white: " + pieces);

        }
        i = row; j = col;
        while (i >= 0 && j >= 0) {
//            Log.d("DEBUG: Left to Right", " i: " + i + " j: " + j);
            if (GomokuLogic.boardMatrix[i][j] == 1) {
//            Log.d("diagonal win check", " peices of white: " + pieces);
                pieces++;
            }
            i--;
            j--;
        }
        if (pieces >= 3) {
            ret = true;
//            Log.d("diagonal win check", " peices of white: " + pieces);
        }
        return ret;
    }

    private boolean checkRow(int row, int col) {
        boolean ret = false;
        int pieces = 0;
//        Log.d("col win check", " peices of white: " + pieces);
        for (int i = 0; i < this.boardSize; i++){
            if (GomokuLogic.boardMatrix[i][col] == 1){
                pieces++;
            }
            if (pieces >= 3) {
                ret = true;
            }
        }
//        GomokuLogic.boardMatrix[row][col] = original;
        return ret;
    }

    private boolean checkCol(int row, int col) {
        boolean ret = false;
        int pieces = 0;
//        Log.d("rowCheck", " peices of white: " + pieces);
        for (int i = 0; i < this.boardSize; i++){
            if (GomokuLogic.boardMatrix[row][i] == 1){
                pieces++;
            }
            if (pieces >= 3) {
                ret = true;
//                Log.d("rowCheck", " peices of white: " + pieces);
            }
        }

//        GomokuLogic.boardMatrix[row][col] = original;
        return ret;
    }

    // check if adjacent pieces are wins
    // need to check two moves ahead
    // *** deprecated ***
    public boolean isWin(int row, int col) {
//        Log.d("isWin check", " Row " + row + " Col " + col);
        int original = GomokuLogic.boardMatrix[row][col];
        boolean ret = false;
        // check for edges

        if (isDisabled(row, col) == false) {
            GomokuLogic.boardMatrix[row][col] = 1;
            GomokuLogic.turn = 1;
//            Log.d("DEBUG STATE", " isDisabled " + GomokuLogic.isWin(row, col));
            if (GomokuLogic.isWin(row, col) == 1) {
                ret = true;
            }
        }
//        Log.d("iswin check", "isWin  is : " + ret);
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
    private int[] isEdge(int row, int col) {
        int[] moves = new int[2];
        moves[0] = row;
        moves[1] = col;

        if (row-1 <= 0) {
            int i = 0;
            while (GomokuLogic.boardMatrix[i][col] != 0) {
                i++;
            }
            moves[0] = i;
        }
        else if (row+1 >= this.boardSize-1) {
            int i = this.boardSize-1;
            while (GomokuLogic.boardMatrix[i][col] != 0) {
                i--;
            }
            moves[0] = i;
        }
        else if (col-1 <= 0) {
            int j = 0;
            while (GomokuLogic.boardMatrix[row][j] != 0) {
                j++;
            }
            moves[1] = j;
        }
        else if (col+1 >= this.boardSize-1) {
            int j = this.boardSize-1;
            while (GomokuLogic.boardMatrix[row][j] != 0) {
                j--;
            }
            moves[1] = j;
        }
        else
            return null;
        return moves;
    }
    private int[] diagonalRightEdgeBlock(int row, int col) {
        int[] moves = new int[2];
        moves[0] = row;
        moves[1] = col;
        // right to left
        if (col+1 >= this.boardSize-1 ) {
            int i = row;
            int j = col;
            while (GomokuLogic.boardMatrix[i][j] != 0) {
                i--;
                j--;
            }
            moves[0] = i;
            moves[1] = j;
        } else if (row-1 < 0) {
            int i = this.boardSize - 1;
            int j = this.boardSize - 1;
            while (GomokuLogic.boardMatrix[i][j] != 0) {
                i++;
                j++;
            }
            moves[0] = i;
            moves[1] = j;
        } else
            return null;
        return moves;
    }

    private int[] diagonalLeftEdgeBlock(int row, int col){
        int[] moves = new int[2];
        moves[0] = row;
        moves[1] = col;
        // right to left
        if (row+1 >= this.boardSize-1 ) {
//        Log.d("DEBUG diagonal check", "condition " + col);
            int i = row;
            int j = col;
            while (GomokuLogic.boardMatrix[i][j] != 0) {
                i--;
                j++;
            }
            moves[0] = i;
            moves[1] = j;
        } else if (col-1 <= 0) {
            int i = row;
            int j = col;
            while (GomokuLogic.boardMatrix[i][j] != 0) {
                i--;
                j++;
            }
            moves[0] = i;
            moves[1] = j;
        } else
            return null;
        return moves;
    }

}
