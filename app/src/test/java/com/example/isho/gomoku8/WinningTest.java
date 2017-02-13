package com.example.isho.gomoku8;

/**
 * Created by isho on 2/12/17.
 */
import org.junit.Before;
import org.junit.Test;
import java.util.regex.Pattern;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WinningTest{
    @Before
    public void Settup(){
        GomokuLogic.clearBoard(10);

    }
    @Test
    public void PlacePieceTest(){
        GomokuLogic.clearBoard(10);
        GomokuLogic.testPiece(5,6);
        assertEquals(1,GomokuLogic.boardMatrix[5][6]);
    }
    @Test
    public void ColumnWin(){
        GomokuLogic.testPiece(0,0);
        GomokuLogic.testPiece(0,1);
        GomokuLogic.testPiece(0,2);
        GomokuLogic.testPiece(0,3);
        GomokuLogic.testPiece(0,4);
        assertEquals(1,GomokuLogic.boardMatrix[0][0]);
        assertEquals(1,GomokuLogic.boardMatrix[0][1]);
        assertEquals(1,GomokuLogic.boardMatrix[0][2]);
        assertEquals(1,GomokuLogic.boardMatrix[0][3]);
        assertEquals(1,GomokuLogic.boardMatrix[0][4]);
        int winner =GomokuLogic.checkWin();
        assertEquals(1,winner);
    }
    @Test
    public void ColumnLose(){
        GomokuLogic.turn =-1;
        GomokuLogic.testPiece(2,2);
        GomokuLogic.testPiece(2,3);
        GomokuLogic.testPiece(2,4);
        GomokuLogic.testPiece(2,5);
        GomokuLogic.testPiece(2,6);
        assertEquals(-1,GomokuLogic.boardMatrix[2][2]);
        assertEquals(-1,GomokuLogic.boardMatrix[2][3]);
        assertEquals(-1,GomokuLogic.boardMatrix[2][4]);
        assertEquals(-1,GomokuLogic.boardMatrix[2][5]);
        assertEquals(-1,GomokuLogic.boardMatrix[2][6]);
        int winner =GomokuLogic.checkWin();
        assertEquals(-1,winner);
    }
    @Test
    public void ColumnNeither(){
        GomokuLogic.turn =-1;
        GomokuLogic.testPiece(2,2);
        GomokuLogic.testPiece(2,3);
        GomokuLogic.turn =1;
        GomokuLogic.testPiece(2,4);
        GomokuLogic.turn =-1;
        GomokuLogic.testPiece(2,5);
        GomokuLogic.testPiece(2,6);
        GomokuLogic.testPiece(2,7);
        assertEquals(-1,GomokuLogic.boardMatrix[2][2]);
        assertEquals(-1,GomokuLogic.boardMatrix[2][3]);
        assertEquals(1,GomokuLogic.boardMatrix[2][4]);
        assertEquals(-1,GomokuLogic.boardMatrix[2][5]);
        assertEquals(-1,GomokuLogic.boardMatrix[2][6]);
        int winner =GomokuLogic.checkWin();
        assertEquals(0,winner);
    }
    @Test
    public void Rows(){
        GomokuLogic.clearBoard(10);
        GomokuLogic.testPiece(2,3);
        GomokuLogic.testPiece(2,4);
        GomokuLogic.testPiece(2,5);
        GomokuLogic.testPiece(2,6);
        GomokuLogic.testPiece(2,7);
        int winner =GomokuLogic.checkWin();
        assertEquals(1,winner);
    }

}
