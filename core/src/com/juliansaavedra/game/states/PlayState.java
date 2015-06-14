package com.juliansaavedra.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juliansaavedra.game.MomoGame;
import com.juliansaavedra.game.ui.Tile;

/**
 * Created by 343076 on 27/05/2015.
 */
public class PlayState extends State {

    private final int MAX_FINGERS = 20;

    private Tile[][] tiles;
    private int tileSize;
    private float boardOffset;

    private boolean showPreview;
    private int rowIndex;
    private int colIndex;
    private float soundStart;
    private final float soundFinish = 38f;
    private Tile currentTile;

    public PlayState(GSM gsm, String soundPack){
        super(gsm);

        loadSoundPack(soundPack, false);
        createTiles();
        initPreview();

    }

    public void loadSoundPack(String name, boolean playBeat){
        if(name.equals("default")){
            for(int i = 0 ; i < 16 ; i ++){
                int beatIndex = i + 1;
                String fileName = "avenger/sound"+beatIndex+".wav";
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

    public void initPreview(){
        showPreview = true;
        rowIndex = 0;
        colIndex = 0;
        soundStart = 0;
        currentTile = tiles[rowIndex][colIndex];
    }

    public void playPattern(){
        currentTile.playSound();
        if(soundStart > soundFinish) {
            soundStart = 0;
            if(colIndex < tiles[0].length) {
                colIndex++;
                if(colIndex >= tiles[0].length) {
                    if(rowIndex < tiles.length) {
                        rowIndex++;
                    }
                    colIndex = 0;
                }
                if(colIndex < tiles[0].length && rowIndex < tiles.length) {
                    currentTile = tiles[rowIndex][colIndex];
                }
                else {
                    //initPreview();
                    showPreview = false;
                    currentTile.updatePressed();
                }
            }
        }
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
        if(showPreview == true){
            playPattern();
            soundStart++;
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
