package com.monkeyGame.game;

import com.monkeyGame.engine.Input;
import com.monkeyGame.engine.gfx.Image;
import com.monkeyGame.engine.gfx.ImageTile;

import java.util.ArrayList;

public class Player {
    private int shart;

    private int x, y;
    private double subX, subY;
    private double xvel = 0.0;
    private double yvel = 0.0;
    private ArrayList<Double> xvels = new ArrayList<>();
    private ArrayList<Double> yvels = new ArrayList<>();
    private boolean facingRight=true;

    private final int w=16;
    private final int h=24;

    private int atk=2, str=0;

    private Room currentRoom;

    private Hitbox tileCollisionHitbox = new Hitbox(14, 22, 0, 0);
    private ArrayList projectileList = new ArrayList<>();
    private ArrayList enemyList = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();

    private final ImageTile normal = new ImageTile("/resources/monkey1.png", 16, 24);
    private final Image test = new Image("/resources/0effortbullet.png");

    private int timer;
    private Input in;

    public Player(int X, int Y, Room room, int ticks){
        x=X;
        y=Y;
        currentRoom=room;
        xvels.add(0, 0.0);
        xvels.add(1, 0.0);
        yvels.add(0, 0.0);
        yvels.add(1, 0.0);
        timer=ticks;
    }

    public void update(Input input, ArrayList enemys){
        for(int i=0; i<projectileList.size(); i++){
            if(projectileList.get(i) instanceof Bullet){
                Bullet b = (Bullet)projectileList.get(i);
                b.update();
                if(b.getTimeout()>60 || b.isColliding(currentRoom.getHitboxes())){
                    projectileList.remove(i); i++;
                }
            }
        }
        movePlayer2();
        setInput(input);
        enemies = enemys;
    }

    public void movePlayer2(){ //collision detection is not efficient at all
        yvels.set(0, 0.5); //gravity

        xvel=0.0;
        yvel=0.0;

        for(int i=0; i<xvels.size(); i++){
            xvel+=xvels.get(i);
        }
        for(int i=0; i<yvels.size(); i++){
            yvel+=yvels.get(i);
        }

        subX+=xvel-(int)xvel;
        subY+=yvel-(int)yvel;

        //add 1 to velocity if subpixel position is >= 1.0
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

        //increment x,y by 1 pixel until hitbox collides with a tile
        if(xvel>=1.0) {
            for (int x = 1; x < (int) xvel + 1; x++) {
                tileCollisionHitbox.setPos(this.x + 1 + x, y + 1);
                if (tileCollisionHitbox.isColliding(currentRoom.getHitboxes())) {
                    break;
                }
                this.x += 1;
            }
        }
        if(xvel<=-1.0) {
            for (int x = -1; x > (int) xvel - 1; x--) {
                tileCollisionHitbox.setPos(this.x + 1 + x, y + 1);
                if (tileCollisionHitbox.isColliding(currentRoom.getHitboxes())) {
                    break;
                }
                this.x -= 1;
            }
        }
        if(yvel>=1.0) {
            for (int y = 1; y < (int) yvel + 1; y++) {
                tileCollisionHitbox.setPos(x + 1, this.y + 1 + y);
                if (tileCollisionHitbox.isColliding(currentRoom.getHitboxes())) {
                    break;
                }
                this.y += 1;
            }
        }
        if(yvel<=-1.0) {
            for (int y = -1; y > (int) yvel - 1; y--) {
                tileCollisionHitbox.setPos(x + 1, this.y + 1 + y);
                if (tileCollisionHitbox.isColliding(currentRoom.getHitboxes())) {
                    break;
                }
                this.y -= 1;
            }
        }

        tileCollisionHitbox.setPos(x+1,y+1);
    }


    public ImageTile getPlayerSprites(){
        return normal;
    }

    public void incX(int X){ x+=X; }
    public void incY(int Y){ y+=Y; }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public boolean getFacingRight() { return facingRight; }
    public void setFacingRight(boolean value) { facingRight=value; }

    public Hitbox getHitbox(int type){
        switch(type){
            case 0: return tileCollisionHitbox;
        }
        return tileCollisionHitbox;
    }

    public void setXvels(int index, double value){
        xvels.set(index, value);
    }
    public void setYvels(int index, double value){
        yvels.set(index, value);
    }

    public String getXvels(){
        double sum=0;
        String cheese = "";
        for(int i=0; i<xvels.size(); i++){
            cheese+=xvels.get(i)+" ";
            sum+=xvels.get(i);
        }
        return sum+" "+cheese;
    }

    public String getYvels(){
        double sum=0;
        String cheese = "";
        for(int i=0; i<yvels.size(); i++){
            cheese+=yvels.get(i)+" ";
            sum+=yvels.get(i);
        }
        return sum+" "+cheese;
    }

    public void setInput(Input input){
        in=input;
    }

    public void summonProjectile(int type){

        //idk how trig works help
        double ratio = (double)(in.getMouseY()-(y+h/2))/(in.getMouseX()-(x+w/2));
        double angle = Math.atan(ratio);
        if(in.getMouseX()<(x+w/2)){
            angle+=Math.PI;
        }

        switch(type) {
            case 0: projectileList.add(new Bullet(test, x+w/2, y+4, 3, angle, atk, str, timer, this)); break;

        }
    }

    public ArrayList getProjectileList(){
        return projectileList;
    }
    public ArrayList getEnemyList() {return enemies; }

    public ArrayList<Hitbox> getRoomHitboxes() { return currentRoom.getHitboxes(); }
}
