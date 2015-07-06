package com.juliansaavedra.tiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juliansaavedra.tiles.handler.Content;
import com.juliansaavedra.tiles.handler.MainPreferences;
import com.juliansaavedra.tiles.states.GSM;
import com.juliansaavedra.tiles.states.MenuState;


// Julian Saavedra
public class MainGame extends ApplicationAdapter {


    public static final String TITLE = "4x4 Tiles";
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    public static Content res;
    public static MainPreferences pref;

    private GSM gsm;
    private SpriteBatch sb;

	@Override
	public void create () {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f,1);

        res = new Content();
        res.loadAtlas("pack2.pack","pack");
        res.loadTexture("background.png", "bg");
        res.loadTexture("playBG.png","playBG");
        res.loadTexture("title.png","title");
        //loadMusicPack("music0");
        pref = new MainPreferences();
        if(!pref.getPref().contains("Sound")){
            pref.setSoundPref(true);
        }
        if(!pref.getPref().contains("Sound Pack")){
            pref.setPackPref(true);
        }
        if(pref.getPref() == null){
            pref.setSoundPref(true);
            pref.setPackPref(true);
        }

        sb = new SpriteBatch();
        gsm = new GSM();
        gsm.push(new MenuState(gsm));
	}

    public void loadMusicPack(String name) {
        for(int i = 0 ; i < 8 ; i++){
            int musicIndex = i + 1;
            String fileName = name + "/music" + musicIndex + ".wav";
            res.loadMusic(fileName,"m"+i);
        }
    }

	@Override
	public void render () {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(sb);

	}
}
