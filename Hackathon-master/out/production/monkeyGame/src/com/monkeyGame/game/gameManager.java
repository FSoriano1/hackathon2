package com.monkeyGame.game;

import com.monkeyGame.engine.Game;
import com.monkeyGame.engine.Input;
import com.monkeyGame.engine.Renderer;
import com.monkeyGame.engine.gameContainer;
import com.monkeyGame.engine.gfx.Image;
import com.monkeyGame.engine.gfx.ImageTile;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class gameManager extends Game {

    private Image image;
    private Image image2;
    private Image tile1;
    private ImageTile imageTile1;

    public int ticks=0;

    DataManager data = new DataManager();

    private Room currentLevel = new Room("name:NOOB,width:10,height:10,data=¢¢¢¢¢¢¢¢¢¢££££££££££££££££££££¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤");
    private Room testLevel = new Room(data.readFile("resources\\test.txt", 3));
    private int[][] currentLevelArray = currentLevel.getUncompressedData2();
    private int[][] testLevelArray = testLevel.getUncompressedData2();

    private ArrayList<Enemy> enemies = new ArrayList<>();

    Builder b = null;

    Player p = new Player(150,200, testLevel, ticks);

    private boolean onStart=true;

    public gameManager(){
        image = new Image("/resources/niko.png");
        image2 = new Image("/resources/niko_transparentTest.png");
        tile1 = new Image("/resources/noob.png");
        imageTile1 = new ImageTile("/resources/niko_transparentTest.png", 24, 96);
        int noob = 0;
    }

    @Override
    public void update(gameContainer gc, float dt) {
        Input in = gc.getInput();
        if(onStart){
            onStart=false;
          //  currentLevel.loadRoom();
           testLevel.loadRoom();
            gc.getRenderer().incrementCameraX(0);
            gc.getRenderer().incrementCameraY(0);
            enemies.add(new EnemyGoomba(new Image("/resources/pog.png"), 250, 100, 0.5, 3, 3, 10, p));
            enemies.add(new EnemyGjoopler(new Image("/resources/pog.png"), 100, 150, 2, 2, 10, 0.2));
        }

        //-----DEBUG CONTROLS-----\\
        if(in.isKeyDown(KeyEvent.VK_M)){
            enemies.add(new EnemyGoomba(new Image("/resources/pog.png"), 100, 100, 0.5, 3, 3, 10, p));
        }
        //-----BUILDER CONTROLS-----\\
        if(in.isKey(KeyEvent.VK_SHIFT) && in.isKey(KeyEvent.VK_Q) && b==null){ /*initialize Builder*/b = new Builder(); }
        if(b!=null){
            if(!(in.isKey(KeyEvent.VK_SHIFT))) {
                if (in.isKeyDown(KeyEvent.VK_A)) { b.incX(false); }
                if (in.isKeyDown(KeyEvent.VK_D)) { b.incX(true); }
                if (in.isKeyDown(KeyEvent.VK_W)) { b.incY(false); }
                if (in.isKeyDown(KeyEvent.VK_S)) { b.incY(true); }
            }
            if(in.isKeyDown(KeyEvent.VK_J)){b.setTile(1);}
            if(in.isKeyDown(KeyEvent.VK_K)){b.setTile(2);}
            if(in.isKeyDown(KeyEvent.VK_L)){b.setTile(3);}
            if (in.isKey(KeyEvent.VK_DELETE)){ b.setTile(0); }
            if(in.isKeyDown(KeyEvent.VK_1)){b.setRedPos();}
            if(in.isKeyDown(KeyEvent.VK_2)){b.setBluePos();}
            if(in.isKey(KeyEvent.VK_SHIFT)) {
                if (in.isKeyDown(KeyEvent.VK_P)) {
                    data.writeToFile("resources\\test.txt", b.getCompressedData());
                }
                if (in.isKey(KeyEvent.VK_J)){ b.setManyTiles(1); }
                if (in.isKey(KeyEvent.VK_K)){ b.setManyTiles(2); }
                if (in.isKey(KeyEvent.VK_L)){ b.setManyTiles(3); }
                if (in.isKey(KeyEvent.VK_DELETE)){ b.setManyTiles(0); }
                if (in.isKey(KeyEvent.VK_A)) { b.incX(false); }
                if (in.isKey(KeyEvent.VK_D)) { b.incX(true); }
                if (in.isKey(KeyEvent.VK_W)) { b.incY(false); }
                if (in.isKey(KeyEvent.VK_S)) { b.incY(true); }
            }
        }
        //-----CONTROLS-----\\
        if(in.isKey(KeyEvent.VK_A) || !in.isKey(KeyEvent.VK_D)){ p.setXvels(1, -2); p.setFacingRight(false); }
        if(in.isKey(KeyEvent.VK_D) || !in.isKey(KeyEvent.VK_A)){ p.setXvels(1, 2); p.setFacingRight(true); }
        if(!in.isKey(KeyEvent.VK_A) && !in.isKey(KeyEvent.VK_D)){ p.setXvels(1, 0.0); }

        if(in.isKey(KeyEvent.VK_W) || !in.isKey(KeyEvent.VK_S)){ p.setYvels(1, -2); }
        if(in.isKey(KeyEvent.VK_S) || !in.isKey(KeyEvent.VK_W)){ p.setYvels(1, 2); }
        if(!in.isKey(KeyEvent.VK_W) && !in.isKey(KeyEvent.VK_S)){ p.setYvels(1, 0.0); }

        if(in.isKeyDown(MouseEvent.BUTTON1)){ p.summonProjectile(0); }

        p.update(in, enemies);

        for(int i=0; i<enemies.size(); i++){
            enemies.get(i).update();
            if(enemies.get(i).getHp()<1){
                enemies.remove(i);
                i++;
            }
        }



        /*
        if(gc.getInput().isKey(KeyEvent.VK_LEFT)){ gc.getRenderer().incrementCameraX(-3); }
        if(gc.getInput().isKey(KeyEvent.VK_RIGHT)){ gc.getRenderer().incrementCameraX(3); }
        if(gc.getInput().isKey(KeyEvent.VK_DOWN)){ gc.getRenderer().incrementCameraY(3); }
        if(gc.getInput().isKey(KeyEvent.VK_UP)){ gc.getRenderer().incrementCameraY(-3); }
        */

        ticks+=dt;
    }

    @Override
    public void render(gameContainer gc, Renderer r, float dt) {
        if(b==null) {
            r.drawLevel(testLevelArray);
           // r.drawLevel(currentLevelArray);
        }
        else{
            r.drawLevel(b.getTileIds());
        }
        /* NIKO
        r.drawImage(image2, 0, 160, false);
        r.drawImage(image2, gc.getInput().getMouseX()-48, gc.getInput().getMouseY()-48, true);
         */
       r.drawText2("TICKS: "+ticks, 100, 0,0xff00ffff, true);
        r.drawText2("TICKS*4 "+(int)(ticks/4), 100, 10,0xff00ffff, true);
        r.drawText2("TICKS/4%2 "+(int)(ticks/4)%2, 100, 20,0xff00ffff, true);
       r.drawText2("X: "+p.getX(), 200, 100, 0xff00ffff, true);
       r.drawText2("Y: "+p.getY(), 200, 110, 0xff00ffff, true);
        r.drawText2("MOUSEX: "+gc.getInput().getMouseX(), 200, 120, 0xff00ffff, true);
        r.drawText2("MOUSEY: "+gc.getInput().getMouseY(), 200, 130, 0xff00ffff, true);
        r.drawText2("D: "+Boolean.toString(gc.getInput().isKey(KeyEvent.VK_D)).toUpperCase(), 200, 140, 0xff00ffff, true);
       r.drawPlayer(p, (int)p.getX(), (int)p.getY(), false, gc.getInput(), ticks);
/*
       for(int i=0; i<enemyList.size(); i++){
           EnemyGoomba e = (EnemyGoomba)enemyList.get(i);
           r.drawImage(e.getSprite(), e.getX(), e.getY(), false);

       } */
       for(int i=0; i<enemies.size(); i++){
           Enemy e = enemies.get(i);
           r.drawImage(e.getSprite(), e.getX(), e.getY(), false);
       }
       if(b!=null) {
           r.renderBuilder(b);
       }
        /*
       for(int i=0; i<testLevel2.getHitboxes().size(); i++){
           r.renderHitbox(testLevel2.getHitboxes().get(i));
       } */
       //r.renderHitbox(p.getHitbox(0));

    }

    public int[][] getCurrentLevelArray(){
        return currentLevelArray;
    }
    public void setLevel(Room roomData){
        currentLevel=roomData;
    }


    public static void main(String args[]){
        gameContainer gc = new gameContainer(new gameManager());
        gc.start();
    }
}
