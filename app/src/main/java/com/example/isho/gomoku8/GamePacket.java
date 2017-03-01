package com.example.isho.gomoku8;

/**
 * Created by isho on 2/26/17.
 */

import java.io.*;

public class GamePacket implements Serializable{
    String packetType;
    String packetMessage;
    Integer row;
    Integer col;
    Integer gameID;
    public GamePacket(String type, Integer row, Integer col ,Integer gameID){
        packetType=type;
        this.row = row;
        this.col = col;
        this.gameID = gameID;
    }
    public GamePacket(String type, Integer row, Integer col){
        packetType=type;
        this.row = row;
        this.col = col;
    }
    public GamePacket(String type, String message){
        packetType=type;
        packetMessage=message;
        gameID=0;
    }
    public GamePacket(String type){
        packetType=type;
        packetMessage=null;
        gameID=0;
    }
    public String toString(){
        return packetType+": "+packetMessage;
    }
}
