package com.monkeyGame.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Builder {

    private int x,y;
    private Room room;
    private int tile1=1;
    private int tile2=2;
    private int tile3=3;
    private int redX=0, redY=0, blueX=0, blueY=0;

    //room data\\
    private int[][] tileIds;
    private String name;
    private int width;
    private int height;

    public Builder(){ //screen is 100 wide, 75 tall
        Scanner in = new Scanner(System.in);
        System.out.println("Enter room name");
        name = in.nextLine();
        System.out.println("Enter room width");
        width = Integer.parseInt(in.nextLine());
        System.out.println("Enter room height");
        height = Integer.parseInt(in.nextLine());
        tileIds = new int[height][width];
        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                tileIds[y][x]=1;
            }
        }

    }

    public void setTile(int held){
        switch(held){
            case 0: tileIds[y/8][x/8] = 0; break;
            case 1: tileIds[y/8][x/8] = tile1; break;
            case 2: tileIds[y/8][x/8] = tile2; break;
            case 3: tileIds[y/8][x/8] = tile3; break;
        }
    }
    public void setManyTiles(int held){
        if(redX<=blueX && redY<=blueY){
            for(int y=redY; y<=blueY; y+=8){
                for(int x=redX; x<=blueX; x+=8){
                    switch(held){
                        case 0: tileIds[y/8][x/8] = 0; break;
                        case 1: tileIds[y/8][x/8] = tile1; break;
                        case 2: tileIds[y/8][x/8] = tile2; break;
                        case 3: tileIds[y/8][x/8] = tile3; break;
                    }
                }
            }
        }
    }

    public int[][] getTileIds(){ return tileIds; }
    public int getTile1() { return tile1; }
    public int getTile2() { return tile2; }
    public int getTile3() { return tile3; }

    public int getX() { return x; }
    public int getY() { return y; }

    public int getRedX() { return redX; }
    public int getRedY() { return redY; }
    public int getBlueX() { return blueX; }
    public int getBlueY() { return blueY; }

    public void setRedPos(){ redX=x; redY=y;}
    public void setBluePos(){ blueX=x; blueY=y; }

    public void incX(boolean right){
        if(right){
            if(!(x/8==width-1)){
                x+=8;
            }
        }
        else if(x!=0){
            x-=8;
        }
    }
    public void incY(boolean down){
        if(down){
            if(!(y/8==height-1)){
                y+=8;
            }
        }
        else if(y!=0){
            y-=8;
        }
    }

    public String getCompressedData(){
        String cheese = "";
        cheese+="name:"+name+",width:"+width+",height:"+height+",data=";
        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                cheese+=Character.toString((char)(tileIds[y][x]+161)); //convert int id to character
            }
        }
        Room cheeseRoom = new Room(cheese);
        if(!(arrayToString(cheeseRoom.getUncompressedData2()).equals(arrayToString(tileIds)))){
            System.out.println(cheese);
            System.out.println(cheeseRoom.getlData());
            System.out.println(arrayToString(cheeseRoom.getUncompressedData2()));
            System.out.println(arrayToString(tileIds));
            cheese = "An error has occurred in saving tile data.";
        }
        return cheese;


    }

    public String arrayToString(int[][] array){ //debug method
        String cheese = "";
        for(int y=0; y<array.length; y++){
            for(int x=0; x<array[y].length; x++){
                cheese+=array[y][x]+",";
            }
        }
        return cheese;
    }

}


