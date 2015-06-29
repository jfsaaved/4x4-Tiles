package com.juliansaavedra.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juliansaavedra.game.handler.Content;
import com.juliansaavedra.game.handler.MomoPreferences;
import com.juliansaavedra.game.states.GSM;
import com.juliansaavedra.game.states.MenuState;
import com.juliansaavedra.game.states.PlayState;

public class MomoGame extends ApplicationAdapter {

    public static final String TITLE = "Momo";
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    public static Content res;
    public static MomoPreferences pref;

    private GSM gsm;
    private SpriteBatch sb;

	@Override
	public void create () {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f,1);

        res = new Content();
        res.loadAtlas("pack2.pack","pack");
        //loadMusicPack("music0");
        pref = new MomoPreferences();

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
