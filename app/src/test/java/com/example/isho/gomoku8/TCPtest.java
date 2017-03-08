package com.example.isho.gomoku8;

/**
 * Created by isho on 2/26/17.
 */
import org.junit.Before;
        import org.junit.Test;
        import java.util.regex.Pattern;

        import static org.junit.Assert.assertArrayEquals;
        import static org.junit.Assert.assertEquals;
        import static org.junit.Assert.assertFalse;
        import static org.junit.Assert.assertTrue;


public class TCPtest {
    OnlineClient online = new OnlineClient(new OnMoveReceived() {
        @Override
        public void moveReceived(int row, int col) {
            GomokuLogic.testPiece(row,col);
        }

        @Override
        public void ConnectionComplete() {
            return;
        }
        @Override
        public void onConnectionProblem(int type){
            return;
        }
    });
    @Test
    public void ConnectionTest(){
        GomokuLogic.clearBoard(10);
        online.OpenConnection();
        assertEquals(true,online.isOpenConnection);


    }

}
