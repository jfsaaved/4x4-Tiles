package com.juliansaavedra.game.ui;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.juliansaavedra.game.MomoGame;

/**
 * Created by 343076 on 27/05/2015.
 */
public class Tile extends Box {

    private TextureRegion light;
    private TextureRegion dark;

    private boolean selected;

    private float totalWidth;
    private float totalHeight;

    private float timer;
    private float maxTime = 0.5f;
    private float expiration;
    private float maxExpiration = 1f;

    private Sound sound;

    public int worth;
    public boolean empty;
    public boolean cdBool;
    public float coolDown;

    public Tile(float x, float y, float width, float height, int num) {

        this.x = x;
        this.y = y;
        this.totalWidth = width - 8;
        this.totalHeight = height - 8;

        worth = 1;
        coolDown = 0;
        empty = false;
        cdBool = false;

        light = MomoGame.res.getAtlas("pack").findRegion("light");
        dark = MomoGame.res.getAtlas("pack").findRegion("dark");
        sound = MomoGame.res.getSound(Integer.toString(num));
    }

    public void setTimer(float t){
        timer = t;
    }

    public void cdToggle(boolean b){
        cdBool = b;
        if(cdBool == true){
            coolDown = 200f;
        }
        else{
            coolDown = 0f;
        }
    }

    public void cdTimer(){
        if(coolDown > 0){
            cdBool= true;
            coolDown--;
        }
        else{
            cdBool = false;
        }
    }

    public boolean justSelected(){
        return cdBool;
    }

    public void playSound(){
        expiration = 0;
        if(selected == false) {
            selected = true;
            //sound.play();
        }
    }

    public void updatePressed(){
        if(selected == true){
            expiration += 1;
            if(expiration > maxExpiration){
                //sound.stop();
                selected = false;
            }
        }
    }

    public void stopSound(){
        sound.stop();
    }

    public int getPoint(){
        int point;
        if(empty == false){
            point = worth;
            return point;
        }
        else{
            point = 0;
            empty = true;
            return point;
        }
    }

    public void reset(){
        if(empty == true){
            empty = false;
        }
    }

    public boolean isEmpty(){
        return empty;
    }

    public void update(float dt) {
        if(width < totalWidth && height < totalHeight) {
            timer += dt;
            width = (timer / maxTime) * totalWidth;
            height = (timer / maxTime) * totalHeight;

            if(width < 0) width = 0;
            if(height < 0) height = 0;

            if(width > totalWidth) width = totalWidth;
            if(height > totalHeight) height = totalHeight;
        }

        cdTimer();
        updatePressed();
    }
    public void render(SpriteBatch sb) {
        if(selected) {
            sb.draw(light, x - width / 2, y - height / 2, width, height);
        }
        else {
            sb.draw(dark, x - width / 2, y - height / 2, width, height);
        }

    }

}
