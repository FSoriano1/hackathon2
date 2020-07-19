package com.monkeyGame.game;

import com.monkeyGame.engine.gfx.Image;

import java.util.ArrayList;

public class Projectile extends Hitbox{

public int w, h, x, y, imgOffX, imgOffY;
private int atk, str;
private Image image;

private Player owner=null;

    public Projectile(Image image, int X, int Y, int a, int s, int ticks){ //hitbox is w/h of image rn
       super(image.getW(), image.getH(), X, Y);
       atk=a; str=s;
    }

    public Projectile appendMovement(String property){
        return this;
    }










}
