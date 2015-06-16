package com.juliansaavedra.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juliansaavedra.game.MomoGame;
import com.juliansaavedra.game.ui.Tile;

import java.util.Scanner;
import java.util.logging.FileHandler;

/**
 * Created by 343076 on 27/05/2015.
 */
public class PlayState extends State {

    private final int MAX_FINGERS = 20;

    private Tile[][] tiles;
    private int tileSize;
    private float boardOffset;

    private String[] patternPacks;
    private String[] patternSplitter;
    private int[] previewRow = new int [16];
    private int[] previewCol = new int [16];

    private boolean patternPlaying;
    private float soundCount;
    private float soundFinish;
    private int previewIndex;

    public PlayState(GSM gsm, String soundPack){
        super(gsm);

        loadSoundPack(soundPack, false);
        createTiles();
        initPreview(0);

    }

    public void loadSoundPack(String name, boolean playBeat) {
        for (int i = 0; i < 16; i++) {
            int beatIndex = i + 1;
            String fileName = name + "/sound" + beatIndex + ".wav";
            MomoGame.res.loadSound(fileName, "" + i);
        }
        if (playBeat == true) {
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
                tiles[row][col].setTimer((-(tiles.length - row) - col) * 0.25f);
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
                previewRow[index] = Integer.parseInt(patternSplitter[i]);
                System.out.println("R: "+previewRow[index]);
                alternate = true;
            }
            else if(alternate == true){
                previewCol[index] = Integer.parseInt(patternSplitter[i]);
                System.out.println("C: "+previewCol[index]);
                alternate = false;
            }
        }
    }

    public void playPattern() {
        tiles[previewRow[previewIndex]][previewCol[previewIndex]].playSound();
        if(soundCount > soundFinish) {
            if (previewIndex < previewRow.length - 1) {
                soundCount = 0;
                previewIndex++;
            } else {
                patternPlaying = false;
            }
        }
    }

    public void initPreview(int previewPattern) {
        getPatterns(previewPattern);
        setPattern(previewPattern);
        patternPlaying = true;
        previewIndex = 0;
        soundCount = 0;
        soundFinish = 38f;
    }

    public void handleInput() {
        for(int i = 0; i < MAX_FINGERS; i++)  {
            if(Gdx.input.isTouched(i)){
                mouse.set(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                cam.unproject(mouse);

                for(int row = 0; row < tiles.length; row++){
                    for(int col = 0; col < tiles[0].length; col++){
                        if(tiles[row][col].contains(mouse.x,mouse.y)) {
                            tiles[row][col].playSound();
                        }
                    }
                }
            }
        }
    }

    public void update(float dt) {

        if(patternPlaying == true){
            playPattern();
            soundCount++;
        }
        else {
            handleInput();
        }
        for(int row = 0 ; row < tiles.length ; row ++){
            for (int col = 0; col < tiles[0].length; col++){
                tiles[row][col].update(dt);
            }
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
        sb.end();

    }

}
