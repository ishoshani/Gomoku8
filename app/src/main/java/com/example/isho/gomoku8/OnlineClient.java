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
    public static boolean inGame;
    public static  boolean isFirst;
    public static boolean isMyTurn;
    public Integer NextRow;
    public Integer NextCol;
    public static boolean NextMoveReady;
    public OnlineClient(OnMoveReceived listener){
        this.onMoveReceived = listener;
    }
    public static  void SendQuit() throws IOException{
        Log.i("online","writing RAGEQUIT");
        out.writeObject(new GamePacket("RAGEQUIT"));
        inGame = false;
    }
    public static void SendEnd(int i, int j )throws IOException{
        Log.i("online","writing Move");
        out.writeObject(new GamePacket("GAMEOVER",i,j));
        inGame = false;
    }
    public static void SendMove(int i, int j)throws IOException{
        Log.i("online","writing Move");
        out.writeObject(new GamePacket("MOVE",i,j));
        NextMoveReady = true;
    }
    public void OpenConnection() {
        try {
            InetAddress servAddress = InetAddress.getByName(ServerIP);
            echoSocket = new Socket(servAddress, portnum);
            out = new ObjectOutputStream(echoSocket.getOutputStream());
            in = new ObjectInputStream(echoSocket.getInputStream());
            echoSocket.setSoTimeout(3000);
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
                isFirst = true;
                isMyTurn = true;
                onMoveReceived.ConnectionComplete();
                while (!NextMoveReady) {
                    Thread.sleep(2000);
                }
                response = (GamePacket) in.readObject();
                NextMoveReady = false;

            } else {
                isMyTurn = false;
                isFirst = false;
                onMoveReceived.ConnectionComplete();
                while (!response.packetType.equals("YOURTURN") && !response.packetType.equals("GAMEOVER")) {
                    Log.i("online",response.packetType);
                    out.writeObject(new GamePacket("WAITINGFORTURN"));
                    response = (GamePacket) in.readObject();
                }
                onMoveReceived.moveReceived(response.row, response.col);

            }
            while (inGame) {
                if (response.packetType.equals("YOURTURN")) {
                    isMyTurn = true;
                    while (!NextMoveReady) {
                        Thread.sleep(2000);
                        if(!inGame){
                            Log.i("online","broke out");
                            break;
                        }


                    }
                    response = (GamePacket) in.readObject();
                    NextMoveReady = false;

                } else {
                    isMyTurn = false;
                    while (response.packetType.equals("KEEPALIVE")||response.packetType.equals("OTHERTURN")) {
                        Log.i("online",response.packetType);
                        Thread.sleep(2000);
                        out.writeObject(new GamePacket("WAITINGFORTURN"));
                        response = (GamePacket) in.readObject();
                        if(!inGame){
                            Log.i("online","broke out");
                            break;
                        }
                    }
                    if (response.packetType.equals("GAMEOVER")||response.packetType.equals("RAGEQUIT")) {
                        inGame = false;
                    }
                    if(response.packetType.equals("RAGEQUIT")){
                        onMoveReceived.onConnectionProblem(1);
                    }
                        onMoveReceived.moveReceived(response.row, response.col);


                }
            }
        }

        catch(UnknownHostException UH) {

        Log.e("online","C:Error:",UH);

        }catch(IOException IOE){
            Log.e("online","C:Error:",IOE);

        }catch(ClassNotFoundException CNF){
            Log.e("online","C:Error:",CNF);

        }catch (InterruptedException Interrupt){
            Log.e("online","C:Error:",Interrupt);

        }
    }

}
