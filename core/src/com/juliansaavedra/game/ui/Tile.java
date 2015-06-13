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
    private float maxExpiration;
    private boolean pressed;

    private Sound sound;

    public Tile(float x, float y, float width, float height, int num) {

        this.x = x;
        this.y = y;
        this.totalWidth = width - 8;
        this.totalHeight = height - 8;

        maxExpiration = 1;
        light = MomoGame.res.getAtlas("pack").findRegion("light");
        dark = MomoGame.res.getAtlas("pack").findRegion("dark");
        sound = MomoGame.res.getSound(Integer.toString(num));
    }

    public void setTimer(float t){
        timer = t;
    }

    public void setSelected(boolean b) {
        selected = b;
        if(selected == true) {
            expiration = 0;
        }
    }

    public void playSound(){
        if(pressed == false) {
            sound.play();
        }
    }

    public void stopSound(){
        sound.stop();
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

        if(selected == true){
            pressed = true;
            expiration += 1;
            if(expiration > maxExpiration) {
                selected = false;
                sound.stop();
            }
        }
        else {
            pressed = false;
        }

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
