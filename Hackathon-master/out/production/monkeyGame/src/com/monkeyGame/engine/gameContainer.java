package com.monkeyGame.engine;

import com.monkeyGame.engine.gfx.Font;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class gameContainer implements Runnable {

    private Thread thread;
    private Window window;
    private Renderer renderer;
    private Input input;
    private Game game;
    private Font font;

    public Window getWindow() {
        return window;
    }



    private boolean running=false;
    private final double UPDATE_CAP=1.0/60.0;
    private int width=400, height=300;
    private float scale=2;
    private String title = "Noob Suck v1.0";

    public float getScale() {
        return scale;
    }
    public void setScale(float scale) {
        this.scale = scale;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    public gameContainer(Game game){
        this.game=game;
    }

    public void start(){
        window = new Window(this);
        window.getCanvas().setFocusable(true);

        renderer = new Renderer(this);
        input = new Input(this);

        thread = new Thread(this);
        thread.run();
    }


    public void stop(){

    }

    public void run(){

        running=true;

        boolean render=false;
        double firstTime=0;
        double lastTime= System.nanoTime()/1000000000.0;
        double passedTime=0;
        double unprocessedTime=0;

        double frameTime=0;
        int frames=0;
        int fps=0;

        //run once \/
        renderer.incrementCameraX(0);
        renderer.incrementCameraY(0);
        System.out.println(renderer.getCameraX());
        System.out.println(renderer.getCameraY());
        //run once /\
        while(running){

            render=false;

            firstTime= System.nanoTime()/1000000000.0;
            passedTime= firstTime-lastTime;
            lastTime=firstTime;

            unprocessedTime+=passedTime;
            frameTime+=passedTime;

            while(unprocessedTime>=UPDATE_CAP){
                unprocessedTime-=UPDATE_CAP;
                render=true;

                game.update(this, (float)UPDATE_CAP*60);
               // System.out.println("update");
                //TD: update game
                input.update();
                if(frameTime>=1.0){
                    frameTime=0;
                    fps=frames;
                    frames=0;

                    //System.out.println("fps: "+fps);
                    //input.update();

                }
            }
            if(render){
                renderer.clear();
                game.render(this, renderer, (float)UPDATE_CAP*60);
                renderer.drawText2("FPS: "+fps, 200, 200,0xff00ffff, true);
                window.update();
                frames++;
            }
            else{
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        dispose();
    }

    public Input getInput() {
        return input;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    private void dispose(){

    }
}
