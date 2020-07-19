package com.monkeyGame.engine;

import com.monkeyGame.engine.gfx.Font;
import com.monkeyGame.engine.gfx.Image;
import com.monkeyGame.engine.gfx.ImageTile;
import com.monkeyGame.game.Flashcard;

import java.awt.event.KeyEvent;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

public class Renderer {
    private int pW, pH;
    private int[] p;

    private Font font = Font.TEST;

    private Image cardSprite = new Image("/resources/card_thing.png");
    private Image arrow = new Image("/resources/arrow.png");

    public Renderer(gameContainer gc){
        pW=gc.getWidth();
        pH=gc.getHeight();
        p=((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

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

    public void drawText(String text, int offX, int offY, int color, int scale){

        int offset = 0; //offset each character draw by x pixels based on length of previous characters

        for(int i=0; i<text.length(); i++){
            int unicode = text.codePointAt(i)-32;
            for(int y=0; y<font.getFontImage().getH(); y++){
                for(int x=0; x<font.getWidths()[unicode]; x++){
                    for(int y2=0; y2<scale; y2++) {
                        for(int x2=0; x2<scale; x2++) {
                            if (font.getFontImage().getP()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getW()] == 0xffffffff) { //if pixel is white
                                setPixel(x*scale + offX + offset*scale + x2, y*scale + offY + y2, color);
                            }

                        }
                    }
                }
            }
            offset+=font.getWidths()[unicode];
        }
    }
    public void drawImage(Image image, int offX, int offY){

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

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) throws ArrayIndexOutOfBoundsException{

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

    public void renderCard(Flashcard card, int x, int y, boolean questionSelected){
        drawImage(cardSprite, x, y);

        if(questionSelected){
            drawImage(arrow, x+170, y+15);
        }
        else{
            drawImage(arrow, x+135, y+160);

        }
        //render question
        String text = card.getQuestion();
        int lines = (int)((text.length()-1)/14)+1;
        int num=lines;
        drawText("QUESTION:", x+10, y+15, 0xff312805, 4);
        for(int i=0; i<lines; i++){
            if(i==0) {
                drawText(text.substring((num - 1) * 14), x + 10, y + 40+20*(num-1), 0xff5d5d5d, 3);
            }
            else{
                drawText(text.substring((num-1) * 14, (num) * 14), x + 10, y + 40+20*(num-1), 0xff5d5d5d, 3);
            }
           num--;
        }
        //render answer
        String text2 = card.getAnswer();
        int lines2 = (int)((text2.length()-1)/14)+1;
        int num2=lines2;
        drawText("ANSWER:", x+10, y+160, 0xffa94522, 4);
        for(int i=0; i<lines2; i++){
            if(i==0) {
                drawText(text2.substring((num2 - 1) * 14), x + 10, y + 185+20*(num2-1), 0xff5d5d5d, 3);
            }
            else{
                drawText(text2.substring((num2-1) * 14, (num2) * 14), x + 10, y + 185+20*(num2-1), 0xff5d5d5d, 3);
            }
            num2--;
        }
    }

}
