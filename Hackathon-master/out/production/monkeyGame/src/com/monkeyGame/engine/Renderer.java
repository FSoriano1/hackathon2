package com.monkeyGame.engine;

import com.monkeyGame.engine.gfx.Font;
import com.monkeyGame.engine.gfx.Image;
import com.monkeyGame.engine.gfx.ImageTile;

import java.awt.event.KeyEvent;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

public class Renderer {
    private int pW, pH, cameraX=0, cameraY=0;
    private int[] p;

    private Font font = Font.TEST;
    private Font font2 = Font.STANDARD;

    private ImageTile tileSet1 = new ImageTile("/resources/noob.png", 16, 16);
    private ImageTile tileSet2 = new ImageTile("/resources/tileset_t1.png", 8, 8);

    private ImageTile markers = new ImageTile("/resources/marker.png", 8, 8);

    public Renderer(gameContainer gc){
        pW=gc.getWidth();
        pH=gc.getHeight();
        cameraX=0;
        cameraY=0;
        p=((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    public void incrementCameraX(int incX){ cameraX+=incX; }
    public void incrementCameraY(int incY){ cameraY+=incY; }

    public void clear(){
        for(int i=0; i<p.length; i++){
            // p[i]+=i; drugs
            p[i]=0;
        }
    }
   
    public void setPixel(int x, int y, int value){
        if((x<0 || x>=pW || y<0 || y>=pH) || ((x+y*pW)<0) || value == 0xffff00ff){
            return;
        }
        p[x+y*pW] = value;
    }

    public void drawText2(String text, int offX, int offY, int color, boolean locked){

        if(!locked){ //only adjust offset if camera position is not to be considered
            offX-=cameraX;
            offY-=cameraY;
        }

        int offset = 0; //offset each character draw by x pixels based on length of previous characters

        for(int i=0; i<text.length(); i++){
            int unicode = text.codePointAt(i)-32;
            for(int y=0; y<font.getFontImage().getH(); y++){
                for(int x=0; x<font.getWidths()[unicode]; x++){
                    if(font.getFontImage().getP()[(x+font.getOffsets()[unicode])+y*font.getFontImage().getW()]==0xffffffff){ //if pixel is white
                        setPixel(x+offX+offset, y+offY, color);
                    }
                }
            }
            offset+=font.getWidths()[unicode];
        }
    }
    public void drawImage(Image image, int offX, int offY, boolean locked){
        if(!locked){ //only adjust offset if camera position is not to be considered
            offX-=cameraX;
            offY-=cameraY;
        }
        if(offX<-image.getW()){return;}
        if(offY<-image.getH()){return;}
        if(offX>=pW){return;}
        if(offY>=pH){return;}

        int newX=0, newY=0;
        int newW=image.getW();
        int newH=image.getH();

        //clipping rendering if partially off screen
        if(offX<0){ newX -= offX; }
        if(offY<0){ newY -= offY; }
        if(newW+offX > pW){ newW -= newW+offX-pW; }
        if(newH+offY>pH){ newH -= newH+offY-pH; }

        for(int y=newY; y<newH; y++){
            for(int x=newX; x<newW; x++){
                setPixel(x+offX, y+offY, image.getP()[x+y*image.getW()]);
            }
        }
    }

    public void drawLevel(int[][] tiles){
        for(int y=0; y<tiles.length; y++){
            for(int x=0; x<tiles[y].length; x++){
                if(tiles[y][x]!=0){
                drawImageTile(tileSet2, (x*tileSet2.getTileW()), (y*tileSet2.getTileH()), tiles[y][x], 0, false);
                }
            }
        }
    }

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY, boolean locked) throws ArrayIndexOutOfBoundsException{

        if(!locked){ //only adjust offset if camera position is not to be considered
            offX-=cameraX;
            offY-=cameraY;
        }

        //disable rendering if off screen
        if(offX < -image.getTileW()){return;}
        if(offY < -image.getTileH()){return;}
        if(offX >= pW){return;}
        if(offY >= pH){return;}

        int newX=0, newY=0;
        int newW=image.getTileW();
        int newH=image.getTileH();

        //clipping rendering if partially off screen
        if(offX<0){ newX -= offX; }
        if(offY<0){ newY -= offY; }
        if(newW+offX > pW){ newW -= newW+offX-pW; }
        if(newH+offY>pH){ newH -= newH+offY-pH; }

        for(int y=newY; y<newH; y++){
            for(int x=newX; x<newW; x++){
                try {
                    setPixel(x + offX, y + offY, image.getP()[(x + tileX * image.getTileW()) + (y + tileY * image.getTileH()) * image.getW()]);
                } catch (Exception ArrayIndexOutOfBoundsException){} //Trying to render p.length+1?
            }
        }
    }

    public void drawPlayer(Player p, int x, int y, boolean locked, Input in, float ticks){
        if(p.getFacingRight()) {
            if(in.isKey(KeyEvent.VK_D)){
               if((int)(ticks/8)%2==0){
                   drawImageTile(p.getPlayerSprites(), x, y, 1, 0, false);
               }
               else{
                   drawImageTile(p.getPlayerSprites(), x, y, 2, 0, false);
               }
            }
            else {
                drawImageTile(p.getPlayerSprites(), x, y, 0, 0, false); //8 is half of width, 24 is height
            }
            }
        else{
            if(in.isKey(KeyEvent.VK_A)){
                if((int)(ticks/8)%2==0){
                    drawImageTile(p.getPlayerSprites(), x, y, 4, 0, false);
                }
                else{
                    drawImageTile(p.getPlayerSprites(), x, y, 5, 0, false);
                }
            }
            else {
                drawImageTile(p.getPlayerSprites(), x, y, 3, 0, false);
            }
        }

        ArrayList projectiles = p.getProjectileList();
        for(int i=0; i<projectiles.size(); i++){
            if(projectiles.get(i) instanceof Bullet){
                Bullet b = (Bullet)projectiles.get(i);
                drawImage(b.getSprite(), b.getX(), b.getY(), false);
            }
        }
    }

    public void renderHitbox(Hitbox box){
        for(int y=0; y<box.getH(); y++){
            for(int x=0; x<box.getW(); x++){
                setPixel(box.getX()-cameraX+x, box.getY()-cameraY+y, 0xffff00f);
            }
        }
    }

    public void renderBuilder(Builder b){
        drawImageTile(markers, b.getX()-cameraX, b.getY()-cameraY, 0, 0, true);
        drawImageTile(markers, b.getRedX()-cameraX, b.getRedY()-cameraY, 1, 0, true);
        drawImageTile(markers, b.getBlueX()-cameraX, b.getBlueY()-cameraY, 2, 0, true);
        /*
        drawImageTile(tileSet2, 8, 8, b.getTile1(), 0, true);
        drawImageTile(tileSet2, 16, 8, b.getTile2(), 0, true);
        drawImageTile(tileSet2, 24, 8, b.getTile3(), 0, true);
         */
    }

    public int getCameraX() { return cameraX; }
    public int getCameraY() { return cameraY; }
}
