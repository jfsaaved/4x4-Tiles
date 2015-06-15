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
    private int[] rowI = new int[15];
    private int[] colI = new int[15];
    private int previewIndex;
    private boolean patternPlaying;
    private Tile[][] currentTile;
    private float soundStart;
    private float soundFinish = 40f;

    public PlayState(GSM gsm, String soundPack){
        super(gsm);

        loadSoundPack(soundPack, false);
        createTiles();
        initPreview();
        getPatterns();
        setPattern(soundPack);
        playPattern(soundPack);

    }

    public void loadSoundPack(String name, boolean playBeat){
        if(name.equals("default")){
            for(int i = 0 ; i < 16 ; i ++){
                int beatIndex = i + 1;
                String fileName = "pack1/sound"+beatIndex+".wav";
                MomoGame.res.loadSound(fileName,""+i);
            }
            if(playBeat == true){
                MomoGame.res.getMusic("beat").setLooping(true);
                MomoGame.res.getMusic("beat").play();
            }
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

    public void getPatterns(){
        FileHandle file = Gdx.files.internal("music.txt");
        String text = file.readString();
        patternPacks = text.split(";");
    }

    public void setPattern(String name) {
        boolean rowOrCol = false;
        int rowP = 0;
        int colP = 0;
        int i = 1;
        if(name.equals("default")) {
            patternSplitter = patternPacks[0].split(",");
            while(i < 31){
                if(rowOrCol == false) {
                    rowI[rowP] = Integer.parseInt(patternSplitter[i]);
                    rowP++;
                    rowOrCol = true;
                }
                else if(rowOrCol == true) {
                    colI[colP] = Integer.parseInt(patternSplitter[i]);
                    colP++;
                    rowOrCol = false;
                }
                i++;
            }

        }
    }

    public void playPattern(String name) {
        patternPlaying = true;
        if(name.equals("default")){
            currentTile[rowI[previewIndex]][colI[previewIndex]].playSound();
        }
    }

    public void initPreview() {
        soundStart = 0;
        previewIndex = 0;
        currentTile = tiles;
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
            soundStart++;
            if(soundStart > soundFinish) {
                playPattern("default");
                previewIndex++;
            }
        }

        handleInput();
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
