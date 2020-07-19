package com.monkeyGame.game;

import com.monkeyGame.engine.gfx.Image;

public class EnemyGoomba extends Enemy implements Enemy.Update {

    public void update() {
        move();
    }

    private double vel;
    private double velMultiplier=1.0;
    private boolean movingRight=true;


    private Player p;

    public EnemyGoomba(Image image, int X, int Y, double speed, int damage, int strength, int health, Player player){
        super(image, X, Y, damage, strength, health);
        vel=speed;
        p=player;
    }

    public void move(){
        double velProduct = vel*velMultiplier;
        if(x>p.getX()){
            if(movingRight){
                velMultiplier=1.0;
            }
            x-=(int)(velProduct);
            subX-=velProduct-(int)(velProduct);
            movingRight=false;
        }
        else if(x<p.getX()){
            if(!movingRight){
                velMultiplier=1.0;
            }
            x+=(int)(velProduct);
            subX+=velProduct-(int)(velProduct);
            movingRight=true;
        }
        if(subX>=1.0){ x++; subX-=1.0; }
        else if(subX<=-1.0){ x--; subX+=1.0; }
        if(subY>=1.0){ y++; subY-=1.0; }
        else if(subY<=-1.0){ y--; subY+=1.0;
        }
        velMultiplier*=1.003;
        if(velMultiplier>2.0){
            velMultiplier=2.0;
        }
    }

    public void move2(){
        double velProduct = vel*velMultiplier;
        if(x>p.getX()){
            if(movingRight){
                velMultiplier=1.0;
            }
            x-=(int)(velProduct);
            subX-=velProduct-(int)(velProduct);
            movingRight=false;
        }
        else if(x<p.getX()){
            if(!movingRight){
                velMultiplier=1.0;
            }
            x+=(int)(velProduct);
            subX+=velProduct-(int)(velProduct);
            movingRight=true;
        }
        if(subX>=1.0){ x++; subX-=1.0; }
        else if(subX<=-1.0){ x--; subX+=1.0; }
        if(subY>=1.0){ y++; subY-=1.0; }
        else if(subY<=-1.0){ y--; subY+=1.0;
        }
        velMultiplier*=1.003;
        if(velMultiplier>2.0){
            velMultiplier=2.0;
        }
    }

}
