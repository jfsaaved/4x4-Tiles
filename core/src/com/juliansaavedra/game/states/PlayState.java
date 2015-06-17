package com.juliansaavedra.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juliansaavedra.game.MomoGame;
import com.juliansaavedra.game.ui.TextImage;
import com.juliansaavedra.game.ui.Tile;

import java.util.Random;

/**
 * Created by 343076 on 27/05/2015.
 */
public class PlayState extends State {

    private final int MAX_FINGERS = 10;

    private Tile[][] tiles;
    private int tileSize;
    private float boardOffset;

    private String[] patternPacks;
    private String[] patternSplitter;
    private int[] patternRow = new int [16];
    private int[] patternCol = new int [16];

    private boolean patternReady;
    private float patternTimer;
    private float patternTimerMax;
    private int patternIndex;

    private int waitTimer = 0;
    private int waitTimer2 = 0;
    private int splash;
    private final int maxWaitTime = 200;

    private boolean playTime = false;
    private int playIndex = 0;

    private TextImage ready;
    private TextImage go;
    private TextImage wrong;
    private TextImage scoreString;
    private int score;

    public PlayState(GSM gsm, String soundPack){
        super(gsm);

        loadSoundPack(soundPack, false);
        createTiles();
        initPattern(0);

        ready = new TextImage("ready",MomoGame.WIDTH/2,MomoGame.HEIGHT/2,1);
        go = new TextImage("go",MomoGame.WIDTH/2,MomoGame.HEIGHT/2,1);
        wrong = new TextImage("wrong",MomoGame.WIDTH/2,MomoGame.HEIGHT/2,1);
        score = 0;
        scoreString = new TextImage(score+"",MomoGame.WIDTH/2,MomoGame.HEIGHT/2 + 300,1);
        ready.hide(true);
        go.hide(true);
        wrong.hide(true);

    }

    public void initPattern(int previewPattern) {
        getPatterns(previewPattern);
        setPattern(previewPattern);
        patternReady = true;
        patternIndex = 0;
        patternTimer = 0;
        patternTimerMax = 38f;
    }

    public void loadSoundPack(String name, boolean playBeat) {
        for (int i = 0; i < 16; i++) {
            int beatIndex = i + 1;
            String fileName = name + "/sound" + beatIndex + ".wav";
            MomoGame.res.loadSound(fileName, "" + i);
        }
        if (playBeat) {
            MomoGame.res.getMusic("beat").setLooping(true);
            MomoGame.res.getMusic("beat").play();
        }
    }

    public void createTiles(){
        tiles = new Tile[4][4];
        tileSize = MomoGame.WIDTH / tiles[0].length;
        boardOffset = (MomoGame.HEIGHT - (tileSize * tiles.length)) / 2;

        int soundNum = 0;
        for(int row = 0 ; row < tiles.length ; row ++){
            for (int col = 0; col < tiles[0].length; col++){
                tiles[row][col] = new Tile( col * tileSize + tileSize / 2, row * tileSize + boardOffset + tileSize / 2, tileSize, tileSize, soundNum);
                Random random = new Random();
                int animation = random.nextInt(99) + 1;
                tiles[row][col].setTimer(-(animation) * 0.01f);
                //tiles[row][col].setTimer((-(tiles.length - row) - col) * 0.25f);
                soundNum++;
            }
        }
    }

    /*Parse the music.txt file to get all the patterns*/
    public void getPatterns(int pack){
        FileHandle file = Gdx.files.internal("music.txt");
        String text = file.readString();
        patternPacks = text.split(";");
        System.out.println(patternPacks[pack]);
    }

    /*Parse the String obtained from getPatterns method to get individual row and col index*/
    public void setPattern(int pack) {
        boolean alternate = false;
        patternSplitter = patternPacks[pack].split(",");
        for(int i = 1 ; i <= 32 ; i ++){
            int index = ((i-1)/2);
            if(alternate == false){
                patternRow[index] = Integer.parseInt(patternSplitter[i]);
                //System.out.println("R: "+patternRow[index]);
                alternate = true;
            }
            else if(alternate == true){
                patternCol[index] = Integer.parseInt(patternSplitter[i]);
                //System.out.println("C: "+patternCol[index]);
                alternate = false;
            }
        }
    }

    public void playPattern() {
        tiles[patternRow[patternIndex]][patternCol[patternIndex]].playSound();
        if(patternTimer > patternTimerMax) {
            if (patternIndex < patternRow.length - 1) {
                patternTimer = 0;
                patternIndex++;
            } else {
                patternReady = false;
            }
        }
    }

    public void handleInput() {
        //for(int i = 0; i < MAX_FINGERS; i++)  {
        if (Gdx.input.justTouched()) {
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(mouse);

            for (int row = 0; row < tiles.length; row++) {
                for (int col = 0; col < tiles[0].length; col++) {
                    if (tiles[row][col].contains(mouse.x, mouse.y)) {
                        tiles[row][col].playSound();
                        if (playTime == true) {
                            if (tiles[row][col] == tiles[patternRow[playIndex]][patternCol[playIndex]]) {
                                playIndex++;
                                score++;
                            } else {
                                wrong.hide(false);
                                splash = 50;
                            }
                        }
                    }
                }
            }
        }
        //}
    }

    public void update(float dt) {

        if(splash > 0){
            splash--;
        }
        else{
            wrong.hide(true);
        }

        if(waitTimer < maxWaitTime){
            waitTimer++;
        }
        else {
            if (patternReady == true) {
                playPattern();
                patternTimer++;
            } else {
                if(waitTimer2 < maxWaitTime + 25){
                    waitTimer2++;
                    if(waitTimer2 == 50){
                        ready.hide(false);
                    }
                    else if(waitTimer2 == maxWaitTime){
                        ready.hide(true);
                        go.hide(false);
                    }
                }
                else{
                    go.hide(true);
                    playTime = true;
                }
                handleInput();
            }
        }

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                tiles[row][col].update(dt);
            }
        }

        scoreString.update(score+"");
    }

    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix((cam.combined));
        sb.begin();
        for(int row = 0 ; row < tiles.length ; row ++){
            for (int col = 0; col < tiles[0].length; col++){
                tiles[row][col].render(sb);
            }
        }
        ready.render(sb);
        go.render(sb);
        wrong.render(sb);
        scoreString.render(sb);
        sb.end();

    }

}
