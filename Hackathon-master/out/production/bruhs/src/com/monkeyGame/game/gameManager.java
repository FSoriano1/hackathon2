package com.monkeyGame.game;

import com.monkeyGame.engine.Game;
import com.monkeyGame.engine.Input;
import com.monkeyGame.engine.Renderer;
import com.monkeyGame.engine.gameContainer;
import com.monkeyGame.engine.gfx.Image;
import com.monkeyGame.engine.gfx.ImageTile;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

public class gameManager extends Game {

    public int ticks = 0;

    private String input = "";

    private boolean onStart = true;
    private DataManager data = new DataManager();

    private Image makerControls = new Image("/resources/controls.png");
    private Image arrow = new Image("/resources/arrow.png");

    private Flashcard currentCard = null;


    private boolean questionSelected = true;
    private boolean makingFlashcards = false;
    private int cardNumber = 0;

    private ArrayList<ArrayList<Flashcard>> studysets = new ArrayList<>();
    private int setNumber = 0;

    private boolean starting = false;
    private boolean ingame = false;
    private ArrayList<Flashcard> gameSet;
    private int problemNumber = 0;

    private int pogX = 20;
    private int pogY = 230;

    private int swordX;
    private int swordY;

    private int enemyX = 270;
    private int enemyY = 290;

    private int timer = 900;
    private int gtime = 0;

    private int swingTicks = 0;

    private boolean openingAnimation = true;
    private boolean hitAnimation = false;
    private boolean killAnimation = false;
    private boolean swingAnimation = false;
    private boolean endgame = false;
    private boolean gameover = false;

    private int enemyMaxHp = 5;
    private int enemyHp = 5;
    private boolean bossEncounter = false;
    private int kills=0;

    private int playerAtk = 5;
    private int playerLife = 1;
    private int level = 1;

    private int missTime=0;

    private boolean studySetSelected = true;

    private ImageTile bob = new ImageTile("/resources/bob.png", 128, 160);
    private ImageTile bobIdle = new ImageTile("/resources/bob_idle.png", 128, 160);
    private ImageTile bobSword = new ImageTile("/resources/bob_sword.png", 180, 200);
    private Image bobOw = new Image("/resources/get_rekt.png");


    private ImageTile enemy1 = new ImageTile("/resources/ugly.png", 128, 128);
    private ImageTile enemy2 = new ImageTile("/resources/colgate.png", 200, 200);
    private ImageTile boss = new ImageTile("/resources/really_ugly.png", 200, 200);
    private int bossSprite = 0;

    private ImageTile room = new ImageTile("/resources/chase_basement.png", 400, 300);
    private int roomSprite = 0;

    private ImageTile enemySprite = enemy1;

    public gameManager() {
    }


    @Override
    public void update(gameContainer gc, float dt) {
        Input in = gc.getInput();
        if (onStart) {
            onStart = false;
        }

        //-----DEBUG CONTROLS-----\\
        //-----CONTROLS-----\\


        //horrible coding \/
        if (in.isKeyDown(KeyEvent.VK_SHIFT) && !makingFlashcards && !starting && !ingame) {
            if (studysets.size() == 0) {
                studysets.add(new ArrayList<>());
                studysets.get(0).add(new Flashcard("", ""));
            }
            currentCard = studysets.get(0).get(0);
            makingFlashcards = true;
            setNumber = 0;
            if (questionSelected) {
                input = currentCard.getQuestion();
            } else {
                input = currentCard.getAnswer();
            }
        }
        if (makingFlashcards) {

            if (questionSelected) {
                currentCard.setQuestion(input);
            } else {
                currentCard.setAnswer(input);
            }
            if (in.isKeyDown(KeyEvent.VK_A)) {
                input += "A";
            }
            if (in.isKeyDown(KeyEvent.VK_B)) {
                input += "B";
            }
            if (in.isKeyDown(KeyEvent.VK_C)) {
                input += "C";
            }
            if (in.isKeyDown(KeyEvent.VK_D)) {
                input += "D";
            }
            if (in.isKeyDown(KeyEvent.VK_E)) {
                input += "E";
            }
            if (in.isKeyDown(KeyEvent.VK_F)) {
                input += "F";
            }
            if (in.isKeyDown(KeyEvent.VK_G)) {
                input += "G";
            }
            if (in.isKeyDown(KeyEvent.VK_H)) {
                input += "H";
            }
            if (in.isKeyDown(KeyEvent.VK_I)) {
                input += "I";
            }
            if (in.isKeyDown(KeyEvent.VK_J)) {
                input += "J";
            }
            if (in.isKeyDown(KeyEvent.VK_K)) {
                input += "K";
            }
            if (in.isKeyDown(KeyEvent.VK_L)) {
                input += "L";
            }
            if (in.isKeyDown(KeyEvent.VK_M)) {
                input += "M";
            }
            if (in.isKeyDown(KeyEvent.VK_N)) {
                input += "N";
            }
            if (in.isKeyDown(KeyEvent.VK_O)) {
                input += "O";
            }
            if (in.isKeyDown(KeyEvent.VK_P)) {
                input += "P";
            }
            if (in.isKeyDown(KeyEvent.VK_Q)) {
                input += "Q";
            }
            if (in.isKeyDown(KeyEvent.VK_R)) {
                input += "R";
            }
            if (in.isKeyDown(KeyEvent.VK_S)) {
                input += "S";
            }
            if (in.isKeyDown(KeyEvent.VK_T)) {
                input += "T";
            }
            if (in.isKeyDown(KeyEvent.VK_U)) {
                input += "U";
            }
            if (in.isKeyDown(KeyEvent.VK_V)) {
                input += "V";
            }
            if (in.isKeyDown(KeyEvent.VK_W)) {
                input += "W";
            }
            if (in.isKeyDown(KeyEvent.VK_X) && !in.isKey(KeyEvent.VK_SHIFT)) {
                input += "X";
            }
            if (in.isKeyDown(KeyEvent.VK_Y)) {
                input += "Y";
            }
            if (in.isKeyDown(KeyEvent.VK_Z)) {
                input += "Z";
            }
            if (in.isKeyDown(KeyEvent.VK_SPACE)) {
                input += " ";
            }
            if (in.isKeyDown(KeyEvent.VK_BACK_SPACE) && !input.equals("")) {
                input = input.substring(0, input.length() - 1);
            }

            if (input.length() > 80 && questionSelected) {
                input = input.substring(0, 80);
            } else if (input.length() > 60 && !questionSelected) {
                input = input.substring(0, 60);
            }

            if (in.isKeyDown(KeyEvent.VK_DOWN) && questionSelected) {
                questionSelected = false;
                input = currentCard.getAnswer();
            } else if (in.isKeyDown(KeyEvent.VK_UP) && !questionSelected) {
                questionSelected = true;
                input = currentCard.getQuestion();
            }


            if (in.isKey(KeyEvent.VK_SHIFT) && in.isKeyDown(KeyEvent.VK_X)) {
                input = "";
            }

            if (in.isKeyDown(KeyEvent.VK_RIGHT) && !in.isKey(KeyEvent.VK_SHIFT)) {
                studysets.get(setNumber).set(cardNumber, currentCard);
                cardNumber += 1;
                if (studysets.get(setNumber).size() <= cardNumber) {
                    studysets.get(setNumber).add(new Flashcard("", ""));
                }
                currentCard = studysets.get(setNumber).get(cardNumber);
                if (questionSelected) {
                    input = currentCard.getQuestion();
                } else {
                    input = currentCard.getAnswer();
                }
            }
            if (in.isKeyDown(KeyEvent.VK_LEFT) && !in.isKey(KeyEvent.VK_SHIFT) && cardNumber != 0) {
                studysets.get(setNumber).set(cardNumber, currentCard);
                cardNumber -= 1;
                currentCard = studysets.get(setNumber).get(cardNumber);
                if (questionSelected) {
                    input = currentCard.getQuestion();
                } else {
                    input = currentCard.getAnswer();
                }
            }
            if (in.isKey(KeyEvent.VK_SHIFT) && in.isKeyDown(KeyEvent.VK_RIGHT)) {
                cardNumber = 0;
                setNumber += 1;
                if (studysets.size() <= setNumber) {
                    studysets.add(new ArrayList<>());
                    studysets.get(setNumber).add(new Flashcard("", ""));
                }
                currentCard = studysets.get(setNumber).get(cardNumber);
                if (questionSelected) {
                    input = currentCard.getQuestion();
                } else {
                    input = currentCard.getAnswer();
                }
            }
            if (in.isKey(KeyEvent.VK_SHIFT) && in.isKeyDown(KeyEvent.VK_LEFT) && setNumber != 0) {
                cardNumber = 0;
                setNumber -= 1;
                currentCard = studysets.get(setNumber).get(cardNumber);
                if (questionSelected) {
                    input = currentCard.getQuestion();
                } else {
                    input = currentCard.getAnswer();
                }
            }
            if (in.isKeyDown(KeyEvent.VK_ESCAPE)) {
                studysets.get(setNumber).set(cardNumber, currentCard);
                cardNumber = 0;
                setNumber = 0;
                makingFlashcards = false;
            }
        }

        if (in.isKeyDown(KeyEvent.VK_B) && !makingFlashcards && !starting && !ingame) {
            starting = true;
        }
        if (starting) {
            if (in.isKeyDown(KeyEvent.VK_ESCAPE)) {
                starting = false;
            }
            if (in.isKeyDown(KeyEvent.VK_LEFT) && setNumber != 0 && studySetSelected) {
                setNumber -= 1;
            }
            if (in.isKeyDown(KeyEvent.VK_RIGHT) && setNumber < studysets.size() - 1 && studySetSelected) {
                setNumber += 1;
            }
            if(in.isKeyDown(KeyEvent.VK_LEFT) && level != 0 && !studySetSelected){
                level--;
            }
            if(in.isKeyDown(KeyEvent.VK_RIGHT) && !studySetSelected){
                level++;
            }
            if(in.isKeyDown(KeyEvent.VK_DOWN) && studySetSelected){
                studySetSelected=false;
            }
            if(in.isKeyDown(KeyEvent.VK_UP) && !studySetSelected){
                studySetSelected=true;
            }
            if (studysets.size() != 0 && in.isKeyDown(KeyEvent.VK_ENTER)) {
                starting = false;
                ingame = true;
                input = "";
                gameSet = studysets.get(setNumber);
                Collections.shuffle(gameSet);
                enemyMaxHp=5+2*(level-1);
                enemyHp=enemyMaxHp;
            }
        }

        if (ingame) {
            gtime++;
            if (gtime > 240 && openingAnimation) {
                openingAnimation = false;
            }
            if (openingAnimation && gtime % 2 == 0) {
                pogY--;
                enemyY--;
            }
            if (!openingAnimation && !hitAnimation && !killAnimation) {
                if (gtime % 4 == 0 && !bossEncounter) { enemyX--; }
                else if(gtime % 8 == 0 && bossEncounter){ enemyX--; }
                if(!bossEncounter) { enemyY = 155 + (int) (20 * (Math.sin(gtime * Math.PI / 40)));
                } else { enemyY = 155 + (int) (20 * (Math.sin(gtime * Math.PI / 80))); }
                if (in.isKeyDown(KeyEvent.VK_A)) {
                    input += "A";
                }
                if (in.isKeyDown(KeyEvent.VK_B)) {
                    input += "B";
                }
                if (in.isKeyDown(KeyEvent.VK_C)) {
                    input += "C";
                }
                if (in.isKeyDown(KeyEvent.VK_D)) {
                    input += "D";
                }
                if (in.isKeyDown(KeyEvent.VK_E)) {
                    input += "E";
                }
                if (in.isKeyDown(KeyEvent.VK_F)) {
                    input += "F";
                }
                if (in.isKeyDown(KeyEvent.VK_G)) {
                    input += "G";
                }
                if (in.isKeyDown(KeyEvent.VK_H)) {
                    input += "H";
                }
                if (in.isKeyDown(KeyEvent.VK_I)) {
                    input += "I";
                }
                if (in.isKeyDown(KeyEvent.VK_J)) {
                    input += "J";
                }
                if (in.isKeyDown(KeyEvent.VK_K)) {
                    input += "K";
                }
                if (in.isKeyDown(KeyEvent.VK_L)) {
                    input += "L";
                }
                if (in.isKeyDown(KeyEvent.VK_M)) {
                    input += "M";
                }
                if (in.isKeyDown(KeyEvent.VK_N)) {
                    input += "N";
                }
                if (in.isKeyDown(KeyEvent.VK_O)) {
                    input += "O";
                }
                if (in.isKeyDown(KeyEvent.VK_P)) {
                    input += "P";
                }
                if (in.isKeyDown(KeyEvent.VK_Q)) {
                    input += "Q";
                }
                if (in.isKeyDown(KeyEvent.VK_R)) {
                    input += "R";
                }
                if (in.isKeyDown(KeyEvent.VK_S)) {
                    input += "S";
                }
                if (in.isKeyDown(KeyEvent.VK_T)) {
                    input += "T";
                }
                if (in.isKeyDown(KeyEvent.VK_U)) {
                    input += "U";
                }
                if (in.isKeyDown(KeyEvent.VK_V)) {
                    input += "V";
                }
                if (in.isKeyDown(KeyEvent.VK_W)) {
                    input += "W";
                }
                if (in.isKeyDown(KeyEvent.VK_X) && !in.isKey(KeyEvent.VK_SHIFT)) {
                    input += "X";
                }
                if (in.isKeyDown(KeyEvent.VK_Y)) {
                    input += "Y";
                }
                if (in.isKeyDown(KeyEvent.VK_Z)) {
                    input += "Z";
                }
                if (in.isKeyDown(KeyEvent.VK_SPACE)) {
                    input += " ";
                }
                if (in.isKeyDown(KeyEvent.VK_BACK_SPACE) && !input.equals("")) {
                    input = input.substring(0, input.length() - 1);
                }
                if (input.length() > 80) {
                    input = input.substring(0, 80);
                }
                if (in.isKeyDown(KeyEvent.VK_ENTER) && timer != 900) { //hit enemy
                    if (input.equals(gameSet.get(problemNumber).getAnswer())) { //right answer
                        enemyHp-=playerAtk;
                        if(enemyHp<=0) {
                            timer = 900;
                            gtime = 0;
                            killAnimation = true;
                            swordX = 100;
                            swordY = 100;
                            enemyHp=enemyMaxHp;
                        }
                        else {
                            swingAnimation=true;
                            incProblemNum();
                            input="";
                        }
                    } else {
                        swingAnimation=true;
                        missTime=30;
                    }
                }
                if (timer > 0) {
                    timer -= 1;
                }
                if (timer == 0) {
                    hitAnimation=true;
                    playerLife--;
                    gtime=0;
                    timer=900;
                    if(playerLife<=0){
                        endgame=true;
                        gameover=true;
                        ingame=false;
                    }
                }
        }
            if (hitAnimation){
                if(!bossEncounter) { enemyX += 3; }
                else if(gtime%2==0){ enemyX+=3; }
                if(gtime>75){
                    hitAnimation=false;
                    incProblemNum();
                }
            }
            if(killAnimation){
                kills++;
                if(gtime<30){
                    swordX+=2;
                } else { swordX-=2; }
                enemyY+=3;
                if(gtime>60) {
                    killAnimation=false;
                    input="";
                    enemyX=270;
                    enemyY=230;
                    if(Math.random() >0.5 && level>1){
                        enemySprite = enemy2;
                    } else if (level>1){
                        enemySprite = enemy1;
                    }
                    if(bossEncounter){
                        bossEncounter=false;
                    }
                    if(kills%5==0){
                        bossEncounter=true;
                        enemyHp=3*enemyMaxHp;
                        timer+=900;
                        enemySprite=boss;
                    }

                    incProblemNum();
                }
            }
            if(swingAnimation){
                swingTicks++;
                if(swingTicks>60){ swingAnimation = false; swingTicks=0; }
            }
         }
        if (endgame){
            if(in.isKeyDown(KeyEvent.VK_ESCAPE)){
                endgame=false;
                playerLife=3;
                openingAnimation=true;
                pogX=20;
                pogY=230;
                problemNumber=0;
                enemyX=270;
                enemyY=290;
                timer=900;
                swingTicks=0;
                enemySprite=enemy1;
                kills=0;
                enemyHp=5;
                hitAnimation = false;
                killAnimation = false;
                swingAnimation = false;
                gtime=0;
                bossEncounter=false;
            }
        }
        ticks+=dt;
    }

    public void incProblemNum(){
        if(problemNumber+1>=gameSet.size()){
            endgame=true;
            gameover=false;
            ingame=false;
        }
        else {
            problemNumber++;
        }
    }

    @Override
    public void render(gameContainer gc, Renderer r, float dt) {
        r.drawImage(new Image("/resources/windowsxp.png"), 0, 0);
        //r.drawText(input, 100, 100, 0xff00ffff, 2);
        if(makingFlashcards) {
            r.renderCard(currentCard, 10, 10, questionSelected);
            r.drawImage(makerControls, 275, 10);
            r.drawText(Integer.toString(cardNumber+1), 348, 20, 0xff000000, 3);
            r.drawText(Integer.toString(setNumber+1), 348, 180, 0xff000000, 3);
        }
        if(starting){
            if(studysets.size()==0) {
                r.drawText("IT SEEMS LIKE YOU HAVE NOT", 10, 10, 0xff000000, 3);
                r.drawText("CREATED ANY STUDY SETS YET.", 10, 30, 0xff000000, 3);
                r.drawText("PRESS ESC TO GO BACK TO THE", 10, 50, 0xff000000, 3);
                r.drawText("MAIN MENU.", 10, 70, 0xff000000, 3);
            }
            else {
                r.drawText("SELECT YOUR STUDY SET TO USE:", 10, 10, 0xff000000, 3);
                r.drawText(Integer.toString(setNumber+1), 10, 35, 0xff000000, 3);
                r.drawText("SELECT YOUR DIFFICULTY LEVEL:", 10, 65, 0xff000000, 3);
                r.drawText(Integer.toString(level), 10, 90, 0xff000000, 3);
            }
            if(studySetSelected && studysets.size()>0){
                r.drawImage(arrow, 50, 35);
            } else if(!studySetSelected && studysets.size()>0){ r.drawImage(arrow, 50, 90); }
        }
        if(ingame) {
            roomSprite = (int)(gtime/16)%8;
            switch(roomSprite){
                case 0: r.drawImageTile(room, 0, 0, 0, 0); break;
                case 1: r.drawImageTile(room, 0, 0, 1, 0); break;
                case 2: r.drawImageTile(room, 0, 0, 0, 1); break;
                case 3: r.drawImageTile(room, 0, 0, 1, 1); break;
                case 4: r.drawImageTile(room, 0, 0, 0, 2); break;
                case 5: r.drawImageTile(room, 0, 0, 1, 2); break;
                case 6: r.drawImageTile(room, 0, 0, 0, 3); break;
                case 7: r.drawImageTile(room, 0, 0, 1, 3); break;
            }
            if(!openingAnimation && !killAnimation && !swingAnimation && !hitAnimation){ //idle animation
                if ((int) (gtime / 60) % 2 == 0) {
                    r.drawImageTile(bobIdle, pogX, pogY, 0, 0);
                } else if ((int) (gtime / 60) % 2 == 1) {
                    r.drawImageTile(bobIdle, pogX, pogY, 1, 0);
                }
            }
            if(openingAnimation) { //walking animation
                if ((int) (gtime / 15) % 4 == 0) {
                    r.drawImageTile(bob, pogX, pogY, 0, 0);
                } else if ((int) (gtime / 15) % 4 == 1) {
                    r.drawImageTile(bob, pogX, pogY, 1, 0);
                } else if ((int) (gtime / 15) % 4 == 2) {
                    r.drawImageTile(bob, pogX, pogY, 0, 1);
                } else if ((int) (gtime / 15) % 4 == 3) {
                    r.drawImageTile(bob, pogX, pogY, 1, 1);
                }
            }
            if(swingAnimation || killAnimation){
                if ((int) (gtime / 6) % 5 == 0) {
                    r.drawImageTile(bobSword, pogX, pogY-35, 0, 0);
                } else if ((int) (gtime / 6) % 5 == 1) {
                    r.drawImageTile(bobSword, pogX, pogY-35, 1, 0);
                } else if ((int) (gtime / 6) % 5 == 2) {
                    r.drawImageTile(bobSword, pogX, pogY-35, 0, 1);
                } else if ((int) (gtime / 6) % 5 == 3) {
                    r.drawImageTile(bobSword, pogX, pogY-35, 1, 1);
                } else if ((int) (gtime / 6) % 5 == 4) {
                    r.drawImageTile(bobSword, pogX, pogY-35, 0, 2);
                }
            }
            r.drawText(playerLife+"/3", pogX+20, pogY-10, 0xffffffff, 2);
            if(!bossEncounter) {
                if ((int) (gtime / 8) % 4 == 0) {
                    r.drawImageTile(enemySprite, enemyX, enemyY, 0, 0);
                } else if ((int) (gtime / 8) % 4 == 1) {
                    r.drawImageTile(enemySprite, enemyX, enemyY, 1, 0);
                } else if ((int) (gtime / 8) % 4 == 2) {
                    r.drawImageTile(enemySprite, enemyX, enemyY, 0, 1);
                } else if ((int) (gtime / 8) % 4 == 3) {
                    r.drawImageTile(enemySprite, enemyX, enemyY, 1, 1);
                }
            } else {
                bossSprite = (short)(gtime/16)%12;
                switch(bossSprite){
                    case 0: r.drawImageTile(enemySprite, enemyX, enemyY-35, 0, 0); break;
                    case 1: r.drawImageTile(enemySprite, enemyX, enemyY-35, 1, 0); break;
                    case 2: r.drawImageTile(enemySprite, enemyX, enemyY-35, 2, 0); break;
                    case 3: r.drawImageTile(enemySprite, enemyX, enemyY-35, 0, 1); break;
                    case 4: r.drawImageTile(enemySprite, enemyX, enemyY-35, 1, 1); break;
                    case 5: r.drawImageTile(enemySprite, enemyX, enemyY-35, 2, 1); break;
                    case 6: r.drawImageTile(enemySprite, enemyX, enemyY-35, 0, 2); break;
                    case 7: r.drawImageTile(enemySprite, enemyX, enemyY-35, 1, 2); break;
                    case 8: r.drawImageTile(enemySprite, enemyX, enemyY-35, 2, 2); break;
                    case 9: r.drawImageTile(enemySprite, enemyX, enemyY-35, 0, 3); break;
                    case 10: r.drawImageTile(enemySprite, enemyX, enemyY-35, 1, 3); break;
                    case 11: r.drawImageTile(enemySprite, enemyX, enemyY-35, 2, 3); break;
                }
            }
            String text = gameSet.get(problemNumber).getQuestion();
            if(killAnimation){
                r.drawImage(new Image("/resources/pog.png"), swordX, swordY);
            }
            if(hitAnimation){
                if((int)(gtime/15)%2 == 0){ r.drawImage(bobOw, pogX, pogY);
                } else if ((int)(gtime/15)%2==1){ r.drawImageTile(bobIdle, pogX, pogY, 0, 0); }
            }
            if(!bossEncounter && !killAnimation){
                r.drawText(enemyHp+"/"+enemyMaxHp, enemyX+30, enemyY-10, 0xffffffff, 2);
            } else if (bossEncounter && !killAnimation){ r.drawText(enemyHp+"/"+3*enemyMaxHp, enemyX+30, enemyY-10, 0xffffffff, 2); }
            if(missTime>0){
                r.drawText("MISS!", enemyX, enemyY-85+missTime*2, 0xffffffff, 4);
                missTime--;
            }
            int lines = (int) ((text.length() - 1) / 14) + 1;
            int num = lines;
            r.drawText("QUESTION:", 235, 10, 0xff312805, 3);
            if(!openingAnimation){
                for (int i = 0; i < lines; i++) {
                    if (i == 0) {
                        r.drawText(text.substring((num - 1) * 14), 235, 30 + 14 * (num - 1), 0xffffffff, 2);
                    } else {
                        r.drawText(text.substring((num - 1) * 14, (num) * 14), 235, 30 + 14 * (num - 1), 0xffffffff, 2);
                    }
                    num--;
                }
            }
            int lines2 = (int)((input.length()-1)/14)+1;
            int num2=lines2;
            r.drawText("ANSWER:", 5, 10, 0xffa94522, 4);
            for(int i=0; i<lines2; i++){
                if(i==0) {
                    r.drawText(input.substring((num2 - 1) * 14), 5, 35+20*(num2-1), 0xffffffff, 3);
                }
                else{
                    r.drawText(input.substring((num2-1) * 14, (num2) * 14), 5, 35+20*(num2-1), 0xffffffff, 3);
                }
                num2--;
            }
            r.drawText(Integer.toString((int)(timer/60)), 140,10,0xffffffff, 4);
        }
        if(endgame){
            if(!gameover) {
                r.drawImage(new Image("/resources/cool_beans.png"), 0, 0);
                r.drawText("GOOD JOB! YOU WENT", 11, 181, 0xffffffff, 3);
                r.drawText("THROUGH ALL THE PROBLEMS", 11, 206, 0xffffffff, 3);
                r.drawText("IN YOUR STUDY SET.", 11, 231, 0xffffffff, 3);
                r.drawText("PRESS ESC TO RETURN TO MENU", 11, 271, 0xffffffff, 3);

                r.drawText("GOOD JOB! YOU WENT", 10, 180, 0xff000000, 3);
                r.drawText("THROUGH ALL THE PROBLEMS", 10, 205, 0xff000000, 3);
                r.drawText("IN YOUR STUDY SET.", 10, 230, 0xff000000, 3);
                r.drawText("PRESS ESC TO RETURN TO MENU", 10, 270, 0xff000000, 3);
            }
            else{
                r.drawImage(new Image("/resources/bruh.png"), 0, 0);
                r.drawText("OOPS! IT LOOKS LIKE YOU", 11, 181, 0xffffffff, 3);
                r.drawText("NEED TO STUDY MORE. THAT'S", 11, 206, 0xffffffff, 3);
                r.drawText("OK. PRACTICE MAKES PERFECT!", 11, 231, 0xffffffff, 3);
                r.drawText("PRESS ESC TO RETURN TO MENU", 11, 271, 0xffffffff, 3);

                r.drawText("OOPS! IT LOOKS LIKE YOU", 10, 180, 0xff000000, 3);
                r.drawText("NEED TO STUDY MORE. THAT'S", 10, 205, 0xff000000, 3);
                r.drawText("OK. PRACTICE MAKES PERFECT!", 10, 230, 0xff000000, 3);
                r.drawText("PRESS ESC TO RETURN TO MENU", 10, 270, 0xff000000, 3);

                r.drawText("ITS OK TO LOSE", 50, 5, 0xff000000, 1);
            }
        }
    }

    public static void main(String args[]){
        gameContainer gc = new gameContainer(new gameManager());
        gc.start();
    }
}
