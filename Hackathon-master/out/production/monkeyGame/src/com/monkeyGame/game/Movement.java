package com.monkeyGame.game;

public class Movement{

    private int x,y;
    private double subX, subY;

    private int pattern;
    private int time;
    private double speed;

    public Movement(int type, int timer, double velocity){
        pattern=type;
        time=timer;
        speed=velocity;
    }

}
