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
import java.util.Collections;

public class gameManager extends Game {

    public int ticks = 0;

    private String input = "";

    private boolean onStart = true;
    private DataManager data = new DataManager();

    private boolean inMenu=true;
    private boolean inShop=false;
    private boolean inInventory=false;
    private boolean inSets=false;

    private boolean invLit;
    private boolean setsLit;
    private boolean playLit;
    private boolean shopLit;
    private boolean exitLit;

    private boolean hasHelmet=false;
    private boolean hasDiamond=false;
    private boolean helmet=false;
    private boolean diamond=false;

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
    private ArrayList<Integer> remaining = new ArrayList<Integer>();
    private int problemNumber = 0;

    private int coins = 15000;

    private int coinX=10;
    private int coinY=10;
    private int playX=140;
    private int playY=230;
    private int invX=300;
    private int invY=40;

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
    private int playerLife = 3;
    private int level = 1;
    private int mode = 0; //0 = infinite, 1 = survival

    private int missTime=0;

    private boolean studySetSelected = true;
    private boolean levelSelected = false;

    private Image door = new Image("/resources/door.png");
    private Image logo = new Image("/resources/logo.png");

    private Image play = new Image("/resources/play2.png");
    private Image play2 = new Image("/resources/playlit.png");
    private Image inv = new Image("/resources/inventory.png");
    private Image inv2 = new Image("/resources/inventory2.png");
    private Image sets = new Image("/resources/studysets.png");
    private Image sets2 = new Image("/resources/studysetslit.png");
    private Image shop = new Image("/resources/shop.png");
    private Image shop2 = new Image("/resources/shoplit.png");

    private Image shopsign = new Image("/resources/shopsign.png");
    private Image slot = new Image("/resources/slot.png");
    private Image shopslot = new Image("/resources/shopslot.png");

    private Image dsword = new Image("/resources/diamond.png");
    private Image isword = new Image("/resources/isword.png");
    private Image viking = new Image("/resources/helmet.png");
    private Image x = new Image("/resources/X.png");

    private Image coin = new Image("/resources/gold.png");

    private Image exit = new Image("/resources/exit.png");
    private Image exit2 = new Image("/resources/exitlit.png");
    private Image exit3 = new Image("/resources/tinyexit.png");
    private Image exit4 = new Image("/resources/tinyexitlit.png");

    private ImageTile bob = new ImageTile("/resources/bob.png", 128, 160);
    private ImageTile bobIdle = new ImageTile("/resources/bob_idle.png", 128, 160);
    private ImageTile bobSword = new ImageTile("/resources/bob_sword.png", 180, 200);
    private Image bobOw = new Image("/resources/get_rekt.png");

    private ImageTile dbob = new ImageTile("/resources/dwalk.png", 128, 160);
    private ImageTile dbobIdle = new ImageTile("/resources/dbob.png", 128, 160);
    private ImageTile dbobSword = new ImageTile("/resources/dattack.png", 180, 200);

    private ImageTile hbob = new ImageTile("/resources/hwalk.png", 128, 160);
    private ImageTile hbobIdle = new ImageTile("/resources/hidle.png", 128, 160);
    private ImageTile hbobSword = new ImageTile("/resources/hattack.png", 180, 200);

    private ImageTile dhbob = new ImageTile("/resources/dhwalk.png", 128, 160);
    private ImageTile dhbobIdle = new ImageTile("/resources/dhidle.png", 128, 160);
    private ImageTile dhbobSword = new ImageTile("/resources/dhattack.png", 180, 200);


    private ImageTile enemy1 = new ImageTile("/resources/ugly.png", 128, 128);
    private ImageTile enemy2 = new ImageTile("/resources/colgate.png", 200, 200);
    private ImageTile enemy3 = new ImageTile("/resources/wolf.png", 132, 148);
    private ImageTile enemy4 = new ImageTile("/resources/plant.png", 180, 176);
    private int plantSprite=0;
    private ImageTile boss = new ImageTile("/resources/really_ugly.png", 200, 200);
    private int bossSprite = 0;

    private ImageTile room = new ImageTile("/resources/chase_basement.png", 400, 300);
    private int roomSprite = 0;

    private ImageTile enemySprite = enemy1;

    public gameManager() {
    }

    public void reset(){
        inMenu=true;
        inShop=false;
        inInventory=false;
        inSets=false;
        ingame=false;
        starting=false;
        makingFlashcards=false;
        questionSelected=false;
        coinX=10;
        coinY=10;
        playX=140;
        playY=230;
        invX=300;
        invY=40;
        pogX = 20;
        pogY = 230;
        gtime=0;
        mode=0;
        kills=0;
    }

    @Override
    public void update(gameContainer gc, float dt) {
        Input in = gc.getInput();
        if (onStart) {
            onStart = false;
            inMenu=true;
        }

        //-----DEBUG CONTROLS-----\\
        //-----CONTROLS-----\\

        if(inMenu){
            if(in.getMouseX()>=invX && in.getMouseX()<=invX+100
                    && in.getMouseY()>=invY && in.getMouseY()<=invY+34){
                invLit=true;
                if(in.isKeyDown(MouseEvent.BUTTON1)) {
                    coinX=-500;
                    coinY=-500;
                    inMenu=false;
                    ingame=false;
                    inInventory=true;
                    playX=-500;
                    playY=-500;
                    invX=-500;
                    invY=-500;
                }
            }
            else{
                invLit=false;
            }

            if(in.getMouseX()>=invX && in.getMouseX()<=invX+100
                    && in.getMouseY()>=invY+120 && in.getMouseY()<=invY+154){
                shopLit=true;
                if(in.isKeyDown(MouseEvent.BUTTON1)) {
                    coinX=150;
                    coinY=100;
                    inMenu=false;
                    ingame=false;
                    inShop=true;
                    playX=-500;
                    playY=-500;
                    invX=-500;
                    invY=-500;

                }
            }
            else{
                shopLit=false;
            }

            if(in.getMouseX()>=invX && in.getMouseX()<=invX+100
                    && in.getMouseY()>=invY+60 && in.getMouseY()<=invY+94){
                setsLit=true;
                if(in.isKeyDown(MouseEvent.BUTTON1)) {
                    coinX=-500;
                    coinY=-500;
                    inMenu=false;
                    ingame=false;
                    inSets=true;
                    playX=-500;
                    playY=-500;
                    invX=-500;
                    invY=-500;
                }
            }
            else{
                setsLit=false;
            }
            //PLAY BUTTON
            if(in.getMouseX()>=playX && in.getMouseX()<=playX+126
                    && in.getMouseY()>=playY && in.getMouseY()<=playY+54){
                playLit=true;
                if(in.isKeyDown(MouseEvent.BUTTON1)){
                    inMenu=false;
                    starting=true;
                    playY-=60;
                    invX=-500;
                    invY=-500;
                    pogX=10;
                    pogY=150;
                }
            }
            else{
                playLit=false;
            }

        }

        if(inInventory||inShop){
            if(inInventory){
                gtime++;
                if(in.getMouseX()>=100 && in.getMouseX()<=180
                        && in.getMouseY()>=220&& in.getMouseY()<=297
                        &&in.isKeyDown(MouseEvent.BUTTON1) && hasDiamond){
                    if(diamond){
                        diamond=false;
                    }
                    else{
                        diamond=true;
                    }

                }
                if(in.getMouseX()>=220 && in.getMouseX()<=300
                        && in.getMouseY()>=220&& in.getMouseY()<=297
                        &&in.isKeyDown(MouseEvent.BUTTON1) && hasHelmet){
                    if(helmet){
                        helmet=false;
                    }
                    else{
                        helmet=true;
                    }
                }
            }
            if(inShop){
                if(in.getMouseX()>=100 && in.getMouseX()<=179
                        && in.getMouseY()>=150 && in.getMouseY()<=227
                &&in.isKeyDown(MouseEvent.BUTTON1)){
                    if(coins<5000){
                        //not enough
                    }
                    else if(coins>=5000 & (!hasDiamond)){
                        coins-=5000;
                        hasDiamond=true;
                    }
                }
                if(in.getMouseX()>=220 && in.getMouseX()<=298
                        && in.getMouseY()>=150 && in.getMouseY()<=227
                &&in.isKeyDown(MouseEvent.BUTTON1)){
                    if(coins<10000){
                        //not enough
                    }
                    else if(coins>=10000 & (!hasHelmet)){
                        coins-=10000;
                        hasHelmet=true;
                    }
                }
            }
            if(in.getMouseX()>=5 && in.getMouseX()<=103
                    && in.getMouseY()>=5 && in.getMouseY()<=39){
                exitLit=true;
                if(in.isKeyDown(MouseEvent.BUTTON1)) {
                    reset();
                }
            }
            else{
                exitLit=false;
            }
        }

        //horrible coding \/
        if (inSets) {
            //(in.isKeyDown(KeyEvent.VK_SHIFT) && !makingFlashcards && !starting && !ingame)
            inMenu=false;
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
            inSets=false;
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
                reset();
            }
        }

        if (in.isKeyDown(KeyEvent.VK_B) && !makingFlashcards && !starting && !ingame) {
            starting = true;
        }
        if (starting) {
            coinX=-500;
            coinY=-500;
            if (in.isKeyDown(KeyEvent.VK_ESCAPE)) {
                starting = false;
                reset();
            }
            if (in.isKeyDown(KeyEvent.VK_LEFT) && setNumber != 0 && studySetSelected) {
                setNumber -= 1;
            }
            if (in.isKeyDown(KeyEvent.VK_RIGHT) && setNumber < studysets.size() - 1 && studySetSelected) {
                setNumber += 1;
            }
            if(in.isKeyDown(KeyEvent.VK_LEFT) && level>1 && levelSelected){
                level--;
            }
            if(in.isKeyDown(KeyEvent.VK_RIGHT) && level<100 && levelSelected){
                level++;
            }
            if( (in.isKeyDown(KeyEvent.VK_LEFT)||in.isKeyDown(KeyEvent.VK_RIGHT))
            &&( (!levelSelected) && (!studySetSelected) ) ){ //mode selected
                mode= Math.abs(mode-1); //alternates between 0 and 1
            }
            if(in.isKeyDown(KeyEvent.VK_DOWN)){
                if(levelSelected){
                    studySetSelected = false;
                    levelSelected = false;
                }
                if(studySetSelected) {
                    studySetSelected = false;
                    levelSelected = true;
                }
            }
            if(in.isKeyDown(KeyEvent.VK_UP)){
                if(levelSelected) {
                    studySetSelected = true;
                    levelSelected = false;
                }
                if((!levelSelected) && (!studySetSelected)){ //mode selected
                    levelSelected = true;
                }
            }
            if(in.getMouseX()>=playX && in.getMouseX()<=playX+126
                    && in.getMouseY()>=playY && in.getMouseY()<=playY+54){
                playLit=true;
                if(in.isKeyDown(MouseEvent.BUTTON1)) {
                    playX = -500;
                    playY = -500;
                    //studysets.size() != 0 && in.isKeyDown(KeyEvent.VK_ENTER)
                    starting = false;
                    ingame = true;
                    input = "";
                    gameSet = (ArrayList<Flashcard>) studysets.get(setNumber).clone();
                    Collections.shuffle(gameSet);
                    enemyMaxHp = 5 + 2 * (level - 1);
                    enemyHp = enemyMaxHp;
                    incProblemNum();
                }
            }
            else{
                playLit=false;
            }
        }

        if (ingame) {
            coinX=300;
            coinY=70;
            if(in.getMouseX()>=180 && in.getMouseX()<=227
                    && in.getMouseY()>=10 && in.getMouseY()<=26){
                exitLit=true;
                if(in.isKeyDown(MouseEvent.BUTTON1)) {
                    inInventory=false;
                    inShop=false;
                    inMenu=true;
                    coinX=10;
                    coinY=10;
                    playX=140;
                    playY=220;
                    invX=300;
                    invY=40;
                    reset();
                    endgame=true;
                    gameover=false;
                    ingame=false;
                }
            }
            else{
                exitLit=false;
            }

            gtime++;
            if (gtime > 240 && openingAnimation) {
                openingAnimation = false;
            }
            if (openingAnimation && gtime % 2 == 0) {
                if(pogY>100) {
                    pogY--;
                }
                enemyY--;
            }
            if (!openingAnimation && !hitAnimation && !killAnimation) {
                if(mode==0) {
                    if (gtime % 4 == 0 && !bossEncounter) {
                        enemyX--;
                    } else if (gtime % 8 == 0 && bossEncounter) {
                        enemyX--;
                    }
                }
                else{ //survival mode
                    if(kills<10){
                        if (gtime % 4 == 0 && !bossEncounter) {
                            enemyX--;
                        } else if (gtime % 8 == 0 && bossEncounter) {
                            enemyX--;
                        }
                    }
                    else{
                        if (gtime % 4 == 0 && !bossEncounter) {
                            enemyX-= ((double)(kills+10)/20);
                        } else if (gtime % 8 == 0 && bossEncounter) {
                            enemyX-= ((double)(kills+10)/20);
                        }
                    }
                }
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
                if (in.isKeyDown(KeyEvent.VK_ENTER)) { //hit enemy
                    if (input.equals(gameSet.get(problemNumber).getAnswer())) { //right answer
                        enemyHp-=playerAtk;
                        if(enemyHp<=0) {
                            if(mode==0) {
                                timer = 900;
                            }
                            else{ //survival mode
                                if(kills<10){
                                    timer=900;
                                }
                                else {
                                    timer=(int)((enemyX-pogX-50)/((double)(kills+10) / 80));
                                    System.out.println(timer);
                                    //timer = (int)(900.0 / ((double)(kills+10) / 20));
                                }
                            }
                            if(mode==0||(mode==1 && kills<10)) {
                                coins += 50 + (Math.random() * 50);
                                if (enemySprite == boss) {
                                    coins += 50 + (Math.random() * 50);
                                }
                            }
                            else{
                                coins += (((double)kills+10)/20)*(50 + (Math.random() * 50));
                                if (enemySprite == boss) {
                                    coins += (((double)kills+10)/20)*(50 + (Math.random() * 50));
                                }
                            }
                            gtime = 0;
                            killAnimation = true;
                            kills++;
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
                    if(mode==0) {
                        timer = 900;
                    }
                    else{ //survival mode
                        if(kills<10){
                            timer=900;
                        }
                        else {
                            timer=(int)((enemyX-pogX-50)/((double)(kills+10) / 80));
                            if(timer==0){
                                timer=10;
                            }

                        }
                    }
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
                //kills++;
                if(gtime<30){
                    swordX+=2;
                } else { swordX-=2; }
                enemyY+=3;
                if(gtime>60) {
                    killAnimation=false;
                    input="";
                    enemyX=270;
                    enemyY=230;
                    if(level==1){
                        enemySprite=enemy1;
                    }
                    if(Math.random() >0.75 && level>1){
                        enemySprite = enemy2;
                    } else if (level>1 && Math.random() >0.5){
                        enemySprite = enemy1;
                    }
                    else if (level>1 && Math.random() >0.25){
                        enemySprite = enemy4;
                    }
                    else if(level>1){
                        enemySprite=enemy3;
                    }
                    if(bossEncounter){
                        bossEncounter=false;
                    }
                    if(kills%5==0){
                        bossEncounter=true;
                        enemyHp=3*enemyMaxHp;
                        timer=timer*2;
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
                reset();
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
        //random bag
        if(!(remaining.size()>0)){

            problemNumber=(int)(Math.random()*gameSet.size());
            remaining.clear();
            for(int i=0; i<gameSet.size();i++){
                if(i!=problemNumber) {
                    remaining.add(i);
                }
            }
            //endgame=true;
            //gameover=false;
            //ingame=false;
        }
        else {
            ArrayList<Integer> temp = new ArrayList<Integer>();
            int num=(int)(Math.random()*remaining.size());
            for(int i=0; i<remaining.size();i++) {
                if(i!=num){
                    temp.add(remaining.get(i));
                }
            }
            problemNumber=remaining.get(num);
            remaining= (ArrayList<Integer>) temp.clone();

        }
    }

    @Override
    public void render(gameContainer gc, Renderer r, float dt) {
        if(inMenu) {
            r.drawImage(door, 0, 0);
            r.drawImage(logo, 100, 4);
        }
        else{
            r.drawImage(new Image("/resources/windowsxp.png"), 0, 0);
        }
        //menu buttons
        if(invLit){
            r.drawImage(inv2, invX, invY);
        }
        else{
            r.drawImage(inv, invX, invY);
        }
        if(setsLit) {
            r.drawImage(sets2, invX, invY + 60);
        }
        else{
            r.drawImage(sets, invX, invY + 60);
        }
        if(shopLit) {
            r.drawImage(shop2, invX, invY + 120);
        }
        else{
            r.drawImage(shop, invX, invY + 120);
        }

        //r.drawText(input, 100, 100, 0xff00ffff, 2);
        if(makingFlashcards) {
            r.renderCard(currentCard, 10, 10, questionSelected);
            r.drawImage(makerControls, 275, 10);
            r.drawText(Integer.toString(cardNumber+1), 348, 20, 0xff000000, 3);
            r.drawText(Integer.toString(setNumber+1), 348, 180, 0xff000000, 3);
        }
        if(starting){
            if(studysets.size()==0) {
                playX=-500;
                playY=-500;
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
                r.drawText("SELECT YOUR MODE:", 10, 120, 0xff000000, 3);
                if(mode==0){
                    r.drawText("INFINITE", 10, 145, 0xff008000, 3);
                }
                else{
                    r.drawText("SURVIVAL", 10, 145, 0xff8b0000, 3);
                }
            }
            if(studySetSelected && studysets.size()>0){
                r.drawImage(arrow, 50, 35);
            } else if(levelSelected && studysets.size()>0){
                r.drawImage(arrow, 50, 90);
            } else if((!levelSelected)&&(!studySetSelected) && studysets.size()>0){
                r.drawImage(arrow, 120, 145);
            }
        }

        //play button
        if(playLit){
            r.drawImage(play2, playX, playY);
        }
        else{
            r.drawImage(play, playX, playY);
        }

        if(inInventory||inShop) {  //shop and inventory, and exit buttons
            if(inShop){
                r.drawText("YOUR GOLD", coinX,coinY-25,0xffffd700, 3);

                r.drawImage(shopsign, 145, 15);
                r.drawImage(shopslot, 100, 150);
                if(!hasDiamond) {
                    r.drawImage(dsword, 110, 160);
                }
                else{
                    r.drawImage(x, 100, 150);
                }
                r.drawImage(shopslot, 220, 150);
                if(!hasHelmet){
                    r.drawImage(viking, 225, 155);
                }
                else{
                    r.drawImage(x, 220, 150);
                }
                r.drawImage(coin, 100, 230);
                r.drawText("5,000", 120,230,0xffffd700, 2);
                r.drawImage(coin, 220, 230);
                r.drawText("10,000", 240,230,0xffffd700, 2);
            }
            if(inInventory){
                pogX=180;
                pogY=30;
                if((!helmet) && (!diamond)) {
                    if ((int) (gtime / 60) % 2 == 0) {
                        r.drawImageTile(bobIdle, pogX, pogY, 0, 0);
                    } else if ((int) (gtime / 60) % 2 == 1) {
                        r.drawImageTile(bobIdle, pogX, pogY, 1, 0);
                    }
                }
                else if(helmet && (!diamond)) {
                    if ((int) (gtime / 60) % 2 == 0) {
                        r.drawImageTile(hbobIdle, pogX, pogY, 0, 0);
                    } else if ((int) (gtime / 60) % 2 == 1) {
                        r.drawImageTile(hbobIdle, pogX, pogY, 1, 0);
                    }
                }
                else if(diamond && (!helmet)) {
                    if ((int) (gtime / 60) % 2 == 0) {
                        r.drawImageTile(dbobIdle, pogX, pogY, 0, 0);
                    } else if ((int) (gtime / 60) % 2 == 1) {
                        r.drawImageTile(dbobIdle, pogX, pogY, 1, 0);
                    }
                }
                else{
                    if ((int) (gtime / 60) % 2 == 0) {
                        r.drawImageTile(dhbobIdle, pogX, pogY, 0, 0);
                    } else if ((int) (gtime / 60) % 2 == 1) {
                        r.drawImageTile(dhbobIdle, pogX, pogY, 1, 0);
                    }
                }

                r.drawImage(slot, 140, 20);
                r.drawImage(slot, 140, 90);
                r.drawImage(slot, 140, 160);
                r.drawImage(slot, 310, 90);

                r.drawImage(shopslot, 60, 220);
                if(hasDiamond && (!diamond)){
                    r.drawImage(dsword, 70, 230);
                }
                r.drawImage(shopslot, 260, 220);
                if(hasHelmet && (!helmet)){
                    r.drawImage(viking, 265, 225);
                }
                if((!diamond)){
                    r.drawImage(isword, 305, 80);
                }
                else if(diamond){
                    r.drawImage(dsword, 305, 80);
                    r.drawImage(isword, 70, 230);
                }
                if(helmet){
                    r.drawImage(viking, 135, 15);
                }
            }
            if(exitLit) {
                r.drawImage(exit2, 5, 5);
            }
            else{
                r.drawImage(exit, 5, 5);
            }
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
            if(exitLit) {
                r.drawImage(exit4, 180, 10);
            }
            else{
                r.drawImage(exit3, 180, 10);
            }
            if(!openingAnimation && !killAnimation && !swingAnimation && !hitAnimation){ //idle animation
                if((!helmet) && (!diamond)) {
                    if ((int) (gtime / 60) % 2 == 0) {
                        r.drawImageTile(bobIdle, pogX, pogY, 0, 0);
                    } else if ((int) (gtime / 60) % 2 == 1) {
                        r.drawImageTile(bobIdle, pogX, pogY, 1, 0);
                    }
                }
                else if(helmet && (!diamond)) {
                    if ((int) (gtime / 60) % 2 == 0) {
                        r.drawImageTile(hbobIdle, pogX, pogY, 0, 0);
                    } else if ((int) (gtime / 60) % 2 == 1) {
                        r.drawImageTile(hbobIdle, pogX, pogY, 1, 0);
                    }
                }
                else if(diamond && (!helmet)) {
                    if ((int) (gtime / 60) % 2 == 0) {
                        r.drawImageTile(dbobIdle, pogX, pogY, 0, 0);
                    } else if ((int) (gtime / 60) % 2 == 1) {
                        r.drawImageTile(dbobIdle, pogX, pogY, 1, 0);
                    }
                }
                else{
                    if ((int) (gtime / 60) % 2 == 0) {
                        r.drawImageTile(dhbobIdle, pogX, pogY, 0, 0);
                    } else if ((int) (gtime / 60) % 2 == 1) {
                        r.drawImageTile(dhbobIdle, pogX, pogY, 1, 0);
                    }
                }
            }
            if(openingAnimation) { //walking animation
                if(pogY>100) {
                    if((!helmet) && (!diamond)) {
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
                    else if(helmet && (!diamond)) {
                        if ((int) (gtime / 15) % 4 == 0) {
                            r.drawImageTile(hbob, pogX, pogY, 0, 0);
                        } else if ((int) (gtime / 15) % 4 == 1) {
                            r.drawImageTile(hbob, pogX, pogY, 1, 0);
                        } else if ((int) (gtime / 15) % 4 == 2) {
                            r.drawImageTile(hbob, pogX, pogY, 0, 1);
                        } else if ((int) (gtime / 15) % 4 == 3) {
                            r.drawImageTile(hbob, pogX, pogY, 1, 1);
                        }
                    }
                    else if(diamond && (!helmet)) {
                        if ((int) (gtime / 15) % 4 == 0) {
                            r.drawImageTile(dbob, pogX, pogY, 0, 0);
                        } else if ((int) (gtime / 15) % 4 == 1) {
                            r.drawImageTile(dbob, pogX, pogY, 1, 0);
                        } else if ((int) (gtime / 15) % 4 == 2) {
                            r.drawImageTile(dbob, pogX, pogY, 0, 1);
                        } else if ((int) (gtime / 15) % 4 == 3) {
                            r.drawImageTile(dbob, pogX, pogY, 1, 1);
                        }
                    }
                    else{
                        if ((int) (gtime / 15) % 4 == 0) {
                            r.drawImageTile(dhbob, pogX, pogY, 0, 0);
                        } else if ((int) (gtime / 15) % 4 == 1) {
                            r.drawImageTile(dhbob, pogX, pogY, 1, 0);
                        } else if ((int) (gtime / 15) % 4 == 2) {
                            r.drawImageTile(dhbob, pogX, pogY, 0, 1);
                        } else if ((int) (gtime / 15) % 4 == 3) {
                            r.drawImageTile(dhbob, pogX, pogY, 1, 1);
                        }
                    }
                }
                else{
                    if((!helmet) && (!diamond)) {
                        if ((int) (gtime / 60) % 2 == 0) {
                            r.drawImageTile(bobIdle, pogX, pogY, 0, 0);
                        } else if ((int) (gtime / 60) % 2 == 1) {
                            r.drawImageTile(bobIdle, pogX, pogY, 1, 0);
                        }
                    }
                    else if(helmet && (!diamond)) {
                        if ((int) (gtime / 60) % 2 == 0) {
                            r.drawImageTile(hbobIdle, pogX, pogY, 0, 0);
                        } else if ((int) (gtime / 60) % 2 == 1) {
                            r.drawImageTile(hbobIdle, pogX, pogY, 1, 0);
                        }
                    }
                    else if(diamond && (!helmet)) {
                        if ((int) (gtime / 60) % 2 == 0) {
                            r.drawImageTile(dbobIdle, pogX, pogY, 0, 0);
                        } else if ((int) (gtime / 60) % 2 == 1) {
                            r.drawImageTile(dbobIdle, pogX, pogY, 1, 0);
                        }
                    }
                    else{
                        if ((int) (gtime / 60) % 2 == 0) {
                            r.drawImageTile(dhbobIdle, pogX, pogY, 0, 0);
                        } else if ((int) (gtime / 60) % 2 == 1) {
                            r.drawImageTile(dhbobIdle, pogX, pogY, 1, 0);
                        }
                    }
                }
            }
            if(swingAnimation || killAnimation){
                if((!helmet) && (!diamond)) {
                    if ((int) (gtime / 6) % 5 == 0) {
                        r.drawImageTile(bobSword, pogX, pogY - 35, 0, 0);
                    } else if ((int) (gtime / 6) % 5 == 1) {
                        r.drawImageTile(bobSword, pogX, pogY - 35, 1, 0);
                    } else if ((int) (gtime / 6) % 5 == 2) {
                        r.drawImageTile(bobSword, pogX, pogY - 35, 0, 1);
                    } else if ((int) (gtime / 6) % 5 == 3) {
                        r.drawImageTile(bobSword, pogX, pogY - 35, 1, 1);
                    } else if ((int) (gtime / 6) % 5 == 4) {
                        r.drawImageTile(bobSword, pogX, pogY - 35, 0, 2);
                    }
                }
                else if(helmet && (!diamond)) {
                    if ((int) (gtime / 6) % 5 == 0) {
                        r.drawImageTile(hbobSword, pogX, pogY - 35, 0, 0);
                    } else if ((int) (gtime / 6) % 5 == 1) {
                        r.drawImageTile(hbobSword, pogX, pogY - 35, 1, 0);
                    } else if ((int) (gtime / 6) % 5 == 2) {
                        r.drawImageTile(hbobSword, pogX, pogY - 35, 0, 1);
                    } else if ((int) (gtime / 6) % 5 == 3) {
                        r.drawImageTile(hbobSword, pogX, pogY - 35, 1, 1);
                    } else if ((int) (gtime / 6) % 5 == 4) {
                        r.drawImageTile(hbobSword, pogX, pogY - 35, 0, 2);
                    }
                }
                else if(diamond && (!helmet)) {
                    if ((int) (gtime / 6) % 5 == 0) {
                        r.drawImageTile(dbobSword, pogX, pogY - 35, 0, 0);
                    } else if ((int) (gtime / 6) % 5 == 1) {
                        r.drawImageTile(dbobSword, pogX, pogY - 35, 1, 0);
                    } else if ((int) (gtime / 6) % 5 == 2) {
                        r.drawImageTile(dbobSword, pogX, pogY - 35, 0, 1);
                    } else if ((int) (gtime / 6) % 5 == 3) {
                        r.drawImageTile(dbobSword, pogX, pogY - 35, 1, 1);
                    } else if ((int) (gtime / 6) % 5 == 4) {
                        r.drawImageTile(dbobSword, pogX, pogY - 35, 0, 2);
                    }
                }
                else{
                    if ((int) (gtime / 6) % 5 == 0) {
                        r.drawImageTile(dhbobSword, pogX, pogY - 35, 0, 0);
                    } else if ((int) (gtime / 6) % 5 == 1) {
                        r.drawImageTile(dhbobSword, pogX, pogY - 35, 1, 0);
                    } else if ((int) (gtime / 6) % 5 == 2) {
                        r.drawImageTile(hbobSword, pogX, pogY - 35, 0, 1);
                    } else if ((int) (gtime / 6) % 5 == 3) {
                        r.drawImageTile(dhbobSword, pogX, pogY - 35, 1, 1);
                    } else if ((int) (gtime / 6) % 5 == 4) {
                        r.drawImageTile(dhbobSword, pogX, pogY - 35, 0, 2);
                    }
                }
            }
            r.drawText(playerLife+"/3", pogX+20, pogY-10, 0xffffffff, 2);
            if(!bossEncounter) {
                if(enemySprite!=enemy4) {
                    if ((int) (gtime / 8) % 4 == 0) {
                        r.drawImageTile(enemySprite, enemyX, enemyY, 0, 0);
                    } else if ((int) (gtime / 8) % 4 == 1) {
                        r.drawImageTile(enemySprite, enemyX, enemyY, 1, 0);
                    } else if ((int) (gtime / 8) % 4 == 2) {
                        r.drawImageTile(enemySprite, enemyX, enemyY, 0, 1);
                    } else if ((int) (gtime / 8) % 4 == 3) {
                        r.drawImageTile(enemySprite, enemyX, enemyY, 1, 1);
                    }
                }
                else{
                    plantSprite = (short)(gtime/4)%8;
                    switch(plantSprite) {
                        case 0:
                            r.drawImageTile(enemySprite, enemyX+20, enemyY-30 , 0, 0);
                            break;
                        case 1:
                            r.drawImageTile(enemySprite, enemyX+20, enemyY-30 , 1, 0);
                            break;
                        case 2:
                            r.drawImageTile(enemySprite, enemyX+20, enemyY-30, 2, 0);
                            break;
                        case 3:
                            r.drawImageTile(enemySprite, enemyX+20, enemyY-30 , 0, 1);
                            break;
                        case 4:
                            r.drawImageTile(enemySprite, enemyX+20, enemyY-30, 1, 1);
                            break;
                        case 5:
                            r.drawImageTile(enemySprite, enemyX+20, enemyY-30, 2, 1);
                            break;
                        case 6:
                            r.drawImageTile(enemySprite, enemyX+20, enemyY-30 , 0, 2);
                            break;
                        case 7:
                            r.drawImageTile(enemySprite, enemyX+20, enemyY-30, 1, 2);
                            break;
                        case 8:
                            r.drawImageTile(enemySprite, enemyX+20, enemyY-30 , 2, 2);
                            break;
                    }
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

        r.drawImage(coin, coinX, coinY);
        r.drawText(Integer.toString(coins), coinX+25,coinY-1,0xffffffff, 3);

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
