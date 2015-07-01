package com.juliansaavedra.game.ui;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.juliansaavedra.game.MainGame;

/**
 * Created by 343076 on 27/05/2015.
 */
public class Tile extends Box {

    private TextureRegion light;
    private TextureRegion dark;
    private TextureRegion wrong;

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
    private boolean isWrong;

    public Tile(float x, float y, float width, float height, int num) {

        this.x = x;
        this.y = y;
        this.totalWidth = width - 8;
        this.totalHeight = height - 8;

        worth = 1;
        coolDown = 0;
        empty = false;
        cdBool = false;
        isWrong = false;

        light = MainGame.res.getAtlas("pack").findRegion("light");
        dark = MainGame.res.getAtlas("pack").findRegion("dark");
        wrong = MainGame.res.getAtlas("pack").findRegion("wrong");
        sound = MainGame.res.getSound(Integer.toString(num));
    }

    public void setTimer(float t){
        timer = t;
    }

    public void cdToggle(boolean b){ // Avoid repeating hits w/ isTouched
        cdBool = b;
        if(cdBool == true){
            coolDown = 1000f; // Arbitrary large number bigger than the count down timer
        }
        else{
            coolDown = 0f;
        }
    }

    public void cdTimer(){ // Reset the cool down
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
            if(MainGame.pref.getSoundPref()) {
                sound.play();
            }
        }
    }

    public void showRight(){
        isWrong = true;
    }

    public void updatePressed(){
        if(selected == true){
            expiration += 1;
            if(expiration > maxExpiration){
                if(MainGame.pref.getSoundPref())
                    sound.stop();
                selected = false;
            }
        }
        else{
            coolDown = 0;
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
        else if(isWrong){
            sb.draw(wrong, x - width / 2, y - height / 2, width, height);
        }
        else {
            sb.draw(dark, x - width / 2, y - height / 2, width, height);
        }

    }

}
