package com.example.isho.gomoku8;

import android.provider.Settings;
import android.util.Log;

/**
 * Created by Jason on 2/12/2017.
 */

public class GomokuLogic {
    static int[][] boardMatrix;
    static int size;
    static boolean freestyle;
    static int turn;//goes between 1 and -1 two decide between players

    public GomokuLogic(int n) {
        boardMatrix = new int[n][n];
        size = n;
        turn = 1;
    }

    static public void clearBoard(int n) {
        freestyle = true;
        turn = 1;
        size = n;
        boardMatrix = new int[n][n];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardMatrix[i][j] = 0;
            }
        }
    }

    static public int getTurn() {
        return turn;
    }

    static public void testPiece(int i, int j) {
        boardMatrix[i][j] = turn;
    }

    static public void placePiece(int i, int j) {
        Log.d("gameLogic", "placed piece at " + i + " and " + j);

        boardMatrix[i][j] = turn;
        turn *= -1;
    }

    /*
     Rethink. Is this the winning move? NOT is this a winning board state
     */
    private static int isColWin(int i, int j) {
        int toWin = 4;
        int checkI = i;
        int checkJ = j;
        boolean closed = false;
        checkJ++;
        while (checkJ < size && boardMatrix[checkI][checkJ] == turn && toWin > 0) {
            toWin--;
            checkJ++;
        }
        if (checkJ == size) {
            closed = true;
        } else if (boardMatrix[checkI][checkJ] == -1) {
            closed = true;
        }
        checkI = i;
        checkJ = j;
        checkJ--;
        while (checkJ >= 0 && boardMatrix[checkI][checkJ] == turn && toWin > 0) {
            toWin--;
            checkJ--;
        }
        if (checkJ > -1 && boardMatrix[checkI][checkJ] != -1) {
            closed = false;
        }
        if (!closed && toWin == 0) {
            return turn;
        }
        return 0;
    }

    private static int isRowWin(int i, int j) {
        int toWin = 4;
        int checkI = i;
        int checkJ = j;
        boolean closed = false;
        checkI++;
        while (checkI < size && boardMatrix[checkI][checkJ] == turn && toWin > 0) {
            toWin--;
            checkI++;
        }
        if (checkI == size) {
            closed = true;
        } else if (boardMatrix[checkI][checkJ] == -1) {
            closed = true;
        }
        checkI = i;
        checkJ = j;
        checkI--;
        while (checkI >= 0 && boardMatrix[checkI][checkJ] == turn && toWin > 0) {
            toWin--;
            checkI--;
        }
        if (checkI > -1 && boardMatrix[checkI][checkJ] != -1) {
            closed = false;
        }
        if (!closed && toWin == 0) {
            return turn;
        }
        return 0;
    }

    private static int isForwardDagWin(int i, int j) {
        int toWin = 4;
        int checkI = i;
        int checkJ = j;
        boolean closed = false;
        checkI++;
        checkJ++;
        while (checkI < size && checkJ < size && boardMatrix[checkI][checkJ] == turn && toWin > 0) {
            toWin--;
            checkI++;
            checkJ++;
        }
        if (checkI == size) {
            closed = true;
        } else if (checkJ == size) {
            closed = true;
        } else if (boardMatrix[checkI][checkJ] == -1) {
            closed = true;
        }
        checkI = i;
        checkJ = j;
        checkI--;
        checkJ--;
        while (checkI >= 0 && checkJ >= 0 && boardMatrix[checkI][checkJ] == turn && toWin > 0) {
            toWin--;
            checkI--;
            checkJ--;
        }
        if ((checkI >= 0 && checkJ >= 0) && boardMatrix[checkI][checkJ] != -1) {
            closed = false;
        }
        if (!closed && toWin == 0) {
            return turn;
        }
        return 0;
    }

    private static int isBackDagWin(int i, int j) {
        int toWin = 4;
        int checkI = i;
        int checkJ = j;
        boolean closed = false;
        checkI--;
        checkJ++;
        while (checkI >= 0 && checkJ < size && boardMatrix[checkI][checkJ] == turn && toWin > 0) {
            toWin--;
            checkI--;
            checkJ++;
        }
        if (checkI == -1) {
            closed = true;
        } else if (checkJ == size) {
            closed = true;
        } else if (boardMatrix[checkI][checkJ] == -1) {
            closed = true;
        }
        checkI = i;
        checkJ = j;
        checkI++;
        checkJ--;
        while (checkI < size && checkJ >= 0 && boardMatrix[checkI][checkJ] == turn && toWin > 0) {
            toWin--;
            checkI++;
            checkJ--;
        }
        if ((checkI < size && checkJ >= 0) && boardMatrix[checkI][checkJ] != -1) {
            closed = false;
        }
        if (!closed && toWin == 0) {
            return turn;
        }
        return 0;
    }

    public static int isWin(int i, int j) {
        int win = isColWin(i, j);
        if (win != 0) {
            return win;
        }
        win = isRowWin(i, j);
        if (win != 0) {
            return win;
        }
        win = isForwardDagWin(i, j);
        if (win != 0) {
            return win;
        }
        win = isBackDagWin(i, j);
        if (win != 0) {
            return win;
        }


        return 0;
    }



    /*
    goes over board to check for a win. return 1 for white win, -1 for black win, return 0 for no win. 3 for draw.
     */
}