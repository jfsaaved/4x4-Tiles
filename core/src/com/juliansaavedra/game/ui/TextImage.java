package com.juliansaavedra.game.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.juliansaavedra.game.MomoGame;

/**
 * Created by 343076 on 17/06/2015.
 */

public class TextImage extends Box {

    private TextureRegion[][] fontSheet;
    private String text;
    private float showSize;
    private boolean hideIt = false;

    public TextImage(String text, float x, float y, float showSize){
        this.text = text;
        this.x = x;
        this.y = y;
        this.showSize = showSize;

        int size = 45;
        width = size * text.length();
        height = size;

        TextureRegion sheet = MomoGame.res.getAtlas("pack").findRegion("fontsheet");
        int numCols = sheet.getRegionWidth() / size;
        int numRows = sheet.getRegionHeight() / size;
        fontSheet = new TextureRegion[numRows][numCols];

        for(int rows = 0 ; rows < numRows ; rows++){
            for(int cols = 0 ; cols < numCols; cols++){
                fontSheet[rows][cols] = new TextureRegion(sheet,size*cols,size*rows,size,size);
            }
        }

    }

    public void render(SpriteBatch sb){
        if(hideIt == false) {
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                int index;
                index = c - 32;

                int row = index / fontSheet[0].length;
                int col = index % fontSheet[0].length;
                sb.draw(fontSheet[row][col], x - width / 2 + (45 * showSize) * i, y - height / 2, 45 * showSize, 45 * showSize);
            }
        }
    }

    public void hide(boolean b){
        hideIt = b;
    }

    public void update(String text, float x, float y){
        this.text = text;
        this.x = x;
        this.y = y;

        int size = 45;
        width = size * text.length();
        height = size;
    }

}
