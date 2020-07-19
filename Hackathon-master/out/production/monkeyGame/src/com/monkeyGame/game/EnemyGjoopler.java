package com.monkeyGame.game;

import com.monkeyGame.engine.gfx.Image;

public class EnemyGjoopler extends Enemy implements Enemy.Update {

    public void update() {
        move();
    }

    private double vel;

    public EnemyGjoopler(Image image, int X, int Y, int damage, int strength, int health, double speed) {
        super(image, X, Y, damage, strength, health);
        vel = speed;
    }

    public void move() {
        x += (int) vel;
        y += (int) vel;
        subX += vel - (int) vel;
        subY += vel - (int) vel;
        if (subX >= 1.0) { x++;subX -= 1.0; }
        else if (subX <= -1.0) { x--;subX += 1.0; }
        if (subY >= 1.0) { y++;subY -= 1.0; }
        else if (subY <= -1.0) { y--;subY += 1.0; }
    }
}
