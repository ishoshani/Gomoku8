package com.example.isho.gomoku8;

import java.net.InetAddress;
import java.net.*;
import java.io.*;
import java.net.UnknownHostException;

/**
 * Created by isho on 2/26/17.
 */
import android.util.Log;
public class OnlineClient {
    private String ServerIP="104.236.186.60";
    private int portnum = 19;
    Socket echoSocket;
    private OnMoveReceived onMoveReceived;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    public  boolean isOpenConnection;
    public  boolean inGame;
    public static boolean isMyTurn;
    public Integer NextRow;
    public Integer NextCol;
    public static boolean NextMoveReady;
    public OnlineClient(OnMoveReceived listener){
        this.onMoveReceived = listener;
    }
    public static void SendMove(int i, int j)throws IOException{
        out.writeObject(new GamePacket("MOVE",i,j));
        NextMoveReady = true;
    }


    public void OpenConnection() {
        try {
            InetAddress servAddress = InetAddress.getByName(ServerIP);
            echoSocket = new Socket(servAddress, portnum);
            out = new ObjectOutputStream(echoSocket.getOutputStream());
            in = new ObjectInputStream(echoSocket.getInputStream());
            out.writeObject(new GamePacket("OPENCONNECTION"));//Handshake
            out.flush();
            GamePacket confirmation = (GamePacket) in.readObject();
            if (confirmation != null) {
                isOpenConnection = true;
            }
            out.writeObject(new GamePacket("FINDGAME"));
            GamePacket response = (GamePacket) in.readObject();
            while (response.packetType.equals("KEEPALIVE") || response.packetType.equals("WAITINGFORGAME")) {
                out.writeObject(new GamePacket("WAITINGFORGAME"));
                response = (GamePacket) in.readObject();
            }
            inGame = true;
            out.writeObject(new GamePacket("BEGINPLAY"));
            response = (GamePacket) in.readObject();
            if (response.packetType.equals("YOURTURN")) {
                while (!NextMoveReady) {
                    Log.i("online","waiting for move");
                }
                response = (GamePacket) in.readObject();
                NextMoveReady = false;

            } else {
                while (!response.packetType.equals("YOURTURN") && !response.packetType.equals("GAMEOVER")) {
                    out.writeObject(new GamePacket("WAITINGFORTURN"));
                    response = (GamePacket) in.readObject();
                }
                NextRow = response.row;
                NextCol = response.col;
                onMoveReceived.moveReceived(NextRow, NextCol);

            }
            while (inGame) {
                if (response.packetType.equals("YOURTURN")) {
                    while (!NextMoveReady) {
                        Log.i("online","waiting for move");

                    }
                    response = (GamePacket) in.readObject();
                    NextMoveReady = false;

                } else {
                    while (!response.packetType.equals("YOURTURN") && !response.packetType.equals("GAMEOVER")) {
                        out.writeObject(new GamePacket("WAITINGFORTURN"));
                        response = (GamePacket) in.readObject();
                    }
                    if (response.packetType.equals("GAMEOVER")) {
                        NextRow = response.row;
                        NextCol = response.col;
                        onMoveReceived.moveReceived(NextRow, NextCol);
                        inGame = false;
                    }

                }
            }
        }

        catch(UnknownHostException UH) {

        Log.e("online","C:Error:",UH);

        }catch(IOException IOE){
            Log.e("online","C:Error:",IOE);

        }catch(ClassNotFoundException CNF){
            Log.e("online","C:Error:",CNF);

        }
    }

}
