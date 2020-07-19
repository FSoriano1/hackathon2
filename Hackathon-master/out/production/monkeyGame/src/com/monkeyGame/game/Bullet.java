package com.monkeyGame.game;

import com.monkeyGame.engine.gfx.Image;

import java.util.ArrayList;

//static path and speed projectile
public class Bullet extends Hitbox {

    private int imgOffX, imgOffY;
    private double subX, subY;
    private int atk, str;
    private double vel;
    private double theta;
    int timer;
    int timeout=0;

    private Image sprite;

    private Player p;
    private ArrayList hitEnemies = new ArrayList();
    private ArrayList<Enemy> hitEnemyList = new ArrayList<>();

    public Bullet(Image image, int x, int y, double spd, double angle, int dmg, int pow, int ticks, Player player){
        super(image.getW(), image.getH(), x, y);
        vel=spd;
        theta=angle;
        atk=dmg;
        str=pow;
        timer=ticks;
        sprite=image;
        p=player;
    }

    public void update(){
        move();
        timeout++;
        checkHit();
    }
    public void move(){
        //set x,y velocity
        int xvel = (int)(Math.cos(theta)*vel);
        int yvel = (int)(Math.sin(theta)*vel);
        //set subpixel position to decimal portion of velocity
        subX+=(Math.cos(theta)*vel)-xvel;
        subY+=(Math.sin(theta)*vel)-yvel;

        if(subX>=1.0){
            xvel++;
            subX-=1.0;
        }
        else if(subX<=-1.0){
            xvel--;
            subX+=1.0;
        }
        if(subY>=1.0){
            yvel++;
            subY-=1.0;
        }
        else if(subY<=-1.0){
            yvel--;
            subY+=1.0;
        }
        x+=xvel;
        y+=yvel;
    }

    public void checkHit(){
        for(int i=0; i<p.getEnemyList().size(); i++){
            Enemy enemy = (Enemy)(p.getEnemyList().get(i));
            if(isColliding(enemy) && !hitEnemyList.contains(enemy)){
                System.out.println("shart");
                enemy.damageSelf(atk);
                hitEnemyList.add(enemy);
            }
        }
    }


    public Image getSprite() {
        return sprite;
    }
    public int getTimeout(){
        return timeout;
    }

    public int getAtk() { return atk; }
    public int getStr() { return str; }
}
