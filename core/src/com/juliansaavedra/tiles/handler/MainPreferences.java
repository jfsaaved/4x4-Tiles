package com.juliansaavedra.tiles.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by 343076 on 22/06/2015.
 */
public class MainPreferences {

    private Preferences prefs;

    public MainPreferences(){
        prefs = Gdx.app.getPreferences("save");
    }

    public void setPackPref(boolean b){
        prefs.putBoolean("Sound Pack", b);
        prefs.flush();
    }

    public boolean getPackPref(){
        return prefs.getBoolean("Sound Pack");
    }

    public void setSoundPref(boolean b){
        prefs.putBoolean("Sound",b);
        prefs.flush();
    }

    public boolean getSoundPref(){
        return prefs.getBoolean("Sound");
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
