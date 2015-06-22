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

    private String[] patternPacks;
    private int[] patternRow = new int [16];
    private int[] patternCol = new int [16];

    private boolean patternReady;
    private float patternTimer;
    private float patternTimerMax;
    private int patternIndex;
    private String patternLevel;

    private boolean playTime = false;
    private int playIndex = 0;

    private TextImage splashString;
    private TextImage scoreString;
    private TextImage timeString;
    private int prepareTime;
    private int splash;

    private int score = 0;
    private int level = 0;
    private int milliseconds;
    private int seconds;
    private String difficulty;

    public PlayState(GSM gsm,String difficulty){
        super(gsm);

        splashString = new TextImage("ready",MomoGame.WIDTH/2,MomoGame.HEIGHT/2,1);
        scoreString = new TextImage(score+"",MomoGame.WIDTH/2,MomoGame.HEIGHT/2 + 300,1);
        splashString.hide(true);

        this.difficulty = difficulty;
        setDifficulty(difficulty);
        loadSoundPack("pack0", false);
        createTiles();
        initPattern(level);

        timeString = new TextImage(seconds+"",MomoGame.WIDTH/2,MomoGame.HEIGHT/2 - 300,1);

    }

    public void setDifficulty(String difficulty){
        milliseconds = 60;
        if(difficulty.equals("easy")){
            patternTimerMax = 1f;
            seconds = 10;
        }
        else if(difficulty.equals("normal")){
            patternTimerMax = 1f;
            seconds = 8;
        }
        else if(difficulty.equals("hard")){
            patternTimerMax = 0.1f;
            seconds = 7;
        }
        else if(difficulty.equals("very hard")){
            patternTimerMax = 0.01f;
            seconds = 5;
        }
        else if(difficulty.equals("insane")){
            patternTimerMax = 0.001f;
            seconds = 3;
        }
        else{
            patternTimerMax = 19f;
        }
    }

    public void initPattern(int previewPattern) {
        getPatterns(previewPattern);
        setPattern(previewPattern);
        patternReady = true;
        patternIndex = 0;
        patternTimer = 0;

        splashString.update(patternLevel, MomoGame.WIDTH / 2, MomoGame.HEIGHT / 2);
        splashString.hide(false);
        prepareTime = 250;
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
        int tileSize = MomoGame.WIDTH / tiles[0].length;
        float boardOffset = (MomoGame.HEIGHT - (tileSize * tiles.length)) / 2;

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
        String[] patternSplitter = patternPacks[pack].split(",");
        patternLevel = patternSplitter[0].trim();
        for(int i = 1 ; i <= 32 ; i ++){
            int index = ((i-1)/2);
            if(!alternate){
                patternRow[index] = Integer.parseInt(patternSplitter[i]);
                //System.out.println("R: "+patternRow[index]);
                alternate = true;
            }
            else if(alternate){
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

        if (Gdx.input.isTouched()) {
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(mouse);
            for (int row = 0; row < tiles.length; row++) {
                for (int col = 0; col < tiles[0].length; col++) {
                    if (tiles[row][col].contains(mouse.x, mouse.y)) {
                        tiles[row][col].playSound();
                        if (playIndex < 16 && playTime) {
                            if (tiles[row][col] == tiles[patternRow[playIndex]][patternCol[playIndex]]) {
                                tiles[row][col].cdToggle(true);
                                if (!tiles[row][col].isEmpty()) {
                                    playIndex++;
                                    if (playIndex == 16) {
                                        playTime = false;
                                        setDifficulty(difficulty);
                                        splashString.update("complete", MomoGame.WIDTH / 2, MomoGame.HEIGHT / 2);
                                        splashString.hide(false);
                                        prepareTime = 350;
                                    }
                                }
                                score += tiles[row][col].getPoint();
                            } else {
                                if (!tiles[row][col].justSelected()) {
                                    gsm.set(new GameOverState(gsm,score));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void update(float dt) {

        if(playTime) {
            if (seconds < 0) {
                gsm.set(new GameOverState(gsm,score));
            } else {
                if (milliseconds >= 0) {
                    milliseconds--;
                } else {
                    seconds--;
                    milliseconds = 60;
                }
            }
        }

        if(prepareTime < 150){ // Pattern initialized
            if(patternReady){
                playPattern();
                patternTimer++;
            }
            else{
                if(prepareTime > 50){
                    splashString.update("ready",MomoGame.WIDTH/2,MomoGame.HEIGHT/2);
                    splashString.hide(false);
                    prepareTime--;
                }
                else if(prepareTime <= 50 && prepareTime > 0){
                    playTime = true;
                    if(splash > 0){ // If user selected a wrong tile, let text change to "wrong" and discard "go"
                        prepareTime = 0;
                    }
                    else {
                        splashString.update("go", MomoGame.WIDTH / 2, MomoGame.HEIGHT / 2);
                        splashString.hide(false);
                        prepareTime--;
                    }
                }
                else if(prepareTime <= 0){
                    if(splash > 0){
                        splash--;
                    }else if(splash == 0){
                        splash = -1;
                        splashString.hide(true);
                    }
                }
                handleInput();
            }
        }
        else if(prepareTime > 250 && prepareTime <= 350){ // A level is completed
            prepareTime--;
            if(prepareTime == 260){
                if(level < 3) {
                    level++;
                }
                else{
                    level = 0;
                }
                splashString.hide(true);
                splash = 0; // Reset from -1 to 0 to avoid Splash Text showing
                playIndex = 0; // Reset the pattern index to 0
                initPattern(level);
            }
        }
        else if (prepareTime > 149 && prepareTime <= 250){ // Timer for pattern initialization/showing level
            prepareTime--;
            if(prepareTime == 160){
                splashString.hide(true);
            }
        }

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                tiles[row][col].update(dt);
            }
        }
        scoreString.update(score + "",MomoGame.WIDTH/2,MomoGame.HEIGHT/2 + 300);
        if(seconds >=0) {
            timeString.update(seconds + "", MomoGame.WIDTH / 2, MomoGame.HEIGHT / 2 - 300);
        }
    }

    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix((cam.combined));
        sb.begin();
        for(int row = 0 ; row < tiles.length ; row ++){
            for (int col = 0; col < tiles[0].length; col++){
                tiles[row][col].render(sb);
            }
        }
        splashString.render(sb);
        scoreString.render(sb);
        timeString.render(sb);
        sb.end();

    }

}
