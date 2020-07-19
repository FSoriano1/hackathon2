package com.monkeyGame.engine;

public abstract class Game {
    public abstract void update(gameContainer gc, float dt);
    public abstract void render(gameContainer gc, Renderer r, float dt);


}
