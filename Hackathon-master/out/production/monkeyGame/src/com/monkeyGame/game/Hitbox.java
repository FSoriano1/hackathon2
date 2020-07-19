package com.monkeyGame.game;

import java.util.ArrayList;

public class Hitbox {
    public int w, h, x, y;

    private int type;

    public Hitbox(int width, int height, int X, int Y){
        w=width; h=height; x=X; y=Y;
    }



    public void setPos(int X, int Y){
        x=X;
        y=Y;
    }
    public void incX(int X){
        x+=X;
    }
    public void incY(int Y){
        y+=Y;
    }
    public boolean isColliding(Hitbox other){
        //width and height must be even
        //this center's x = x+width/2
        //this center's y = y+height/2
        // check to see if distance between the centers are less than
        // obj1 width/2 + obj2 width/2 and also obj1 height/2 + obj2 height/2
        if(Math.abs(x+w/2-(other.getX()+other.getW()/2))<((w+other.getW())/2)
        && Math.abs(y+h/2-(other.getY()+other.getH()/2))<((h+other.getH())/2)){
            return true;
        }
        return false;
    }

    public boolean isColliding(ArrayList<Hitbox> others){
        for(int i=0; i<others.size(); i++){
            if(others.get(i).isColliding(this)){
                return true;
            }
        }
        return false;
    }

    public int getW() { return w; }
    public int getH() { return h; }
    public int getX() { return x; }
    public int getY() { return y; }
}
