package com.monkeyGame.game;

import com.monkeyGame.engine.gfx.Image;

import java.util.ArrayList;

public abstract class Enemy extends Hitbox{

    public abstract void update();

    public Image sprite;

    public int x, y;
    public double subX=0.0, subY=0.0;
    public int dmg, str;
    public int hp;

    public Enemy(Image image, int X, int Y, int damage, int strength, int health){
        super(image.getW(), image.getH(), X, Y);
        sprite=image;
        x=X;
        y=Y;
        dmg=damage;
        str=strength;
        hp=health;
    }

    public Image getSprite() { return sprite; }
    public int getHp() { return hp; }

    public void incX(int value){ x+=value; }
    public void incY(int value){ y+=value; }
    /*
    public void moveWithTileCollision(int incrementX, int incrementY, ArrayList<Hitbox> hitboxes){
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
    } */

    @Override
    public int getX() { return x; }

    @Override
    public int getY() { return y; }

    public void damageSelf(int atk){
        hp-=atk;
    }
    interface Update { void update(); }
}
