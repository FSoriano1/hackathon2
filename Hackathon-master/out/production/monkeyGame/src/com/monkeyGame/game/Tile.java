package com.monkeyGame.game;

public class Tile {

    private int width, height, x, y;
    private int id;

    public Tile(int Width, int Height, int X, int Y, int tileType){
        width=Width;
        height=Height;
        x=X;
        y=Y;
        id=tileType;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getId() { return id; }
}
