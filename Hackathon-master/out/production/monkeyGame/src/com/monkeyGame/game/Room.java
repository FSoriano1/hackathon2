package com.monkeyGame.game;

import java.util.ArrayList;

public class Room{

    private String lData;
    public Room(String compressedData){
        lData=compressedData;
    }
    private ArrayList<Tile> tileList = new ArrayList<>();
    private ArrayList<Hitbox> hitboxList = new ArrayList<>();

    public void loadRoom(){
        int[][] tiles = this.getUncompressedData2();
        for(int y=0; y<tiles.length; y++){
            for(int x=0; x<tiles[y].length; x++){
                if(tiles[y][x] != 0){
                    tileList.add(new Tile(8, 8, 8*x, 8*y, tiles[y][x]));
                    hitboxList.add(new Hitbox(8, 8, 8*x, 8*y));
                }
            }
        }
    }

    public int[][] getUncompressedData(){ //compressed String -> tileID 2d array
        String data = lData.substring(lData.indexOf("=")+1);
        int[][] level = new int[this.getWidth()][this.getHeight()];
        for(int y=0; y<this.getHeight(); y++){
            for(int x=0; x<this.getWidth(); x++){
                //System.out.println(y*this.getHeight()+x);
                level[x][y] = data.codePointAt(y*this.getHeight()+x)-161;
            }
        }
        return level;
    }

    public int[][] getUncompressedData2(){ //compressed String -> tileID 2d array
        String data = lData.substring(lData.indexOf("=")+1);
        int[][] level = new int[this.getHeight()][this.getWidth()];
        for(int y=0; y<this.getHeight(); y++){
            for(int x=0; x<this.getWidth(); x++){
                //System.out.println(y*this.getHeight()+x);
                level[y][x] = data.codePointAt(y*this.getWidth()+x)-161;
            }
        }
        return level;
    }

    public String toString(){ // compressed String -> list of tileID
        String data = lData.substring(lData.indexOf("=")+1);
        String cheese = "";
        int tile;
        for(int y=0; y<this.getHeight(); y++){
            for(int x=0; x<this.getWidth(); x++){
                tile = data.codePointAt(y*this.getHeight()+x)-161;
                if(tile>=0){ cheese+=Integer.toString(tile); }
                else if(tile<0){ cheese+="invalid"; }
                cheese += ",";
            }
        }
        return cheese;
    }

    public String getName(){ return lData.substring(lData.indexOf(":")+1,lData.indexOf(",")); }

    public int getWidth(){
        String data = lData.substring(lData.indexOf(",")+1,lData.indexOf("="));
        return Integer.parseInt(data.substring(data.indexOf(":")+1,data.indexOf(",")));
    }

    public int getHeight(){
        String data = lData.substring(lData.indexOf("g")+4,lData.indexOf("="));
        return Integer.parseInt(data.substring(0,data.indexOf(",")));
    }

    public ArrayList<Hitbox> getHitboxes(){
        return hitboxList;
    }

    public ArrayList<Tile> getTileList(){
        return tileList;
    }

    public ArrayList<Integer> getTileIds(){
        ArrayList<Integer> tiles = new ArrayList<>();
        for(int i=0; i<tileList.size(); i++){
            tiles.add(i, tileList.get(i).getId());
        }
        return tiles;
    }

    public String getlData() {
        return lData;
    }



    public static void main(String args[]){
        Room r = new Room("name:NOOB,width:3,height:2,data=¢¢¢£££");
        int[][] gloop = r.getUncompressedData2();
        String barf = "";

        for(int y=0; y<gloop.length; y++){
            for(int x=0; x<gloop[y].length; x++){
                barf+=gloop[y][x]+",";
            }
        }
        System.out.println(barf);
    }
}

