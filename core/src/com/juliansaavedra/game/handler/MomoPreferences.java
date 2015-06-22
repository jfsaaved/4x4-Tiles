package com.juliansaavedra.game.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by 343076 on 22/06/2015.
 */
public class MomoPreferences {

    private Preferences prefs;

    public MomoPreferences(){
        prefs = Gdx.app.getPreferences("save");
    }

    public void setHighScore(int score){
        prefs.putInteger("High Score",score);
        prefs.flush();
    }

    public int getHighScore(){
        if(prefs.getInteger("High Score") >= 0) {
            return prefs.getInteger("High Score");
        }
        else{
            return 0;
        }
    }

}
