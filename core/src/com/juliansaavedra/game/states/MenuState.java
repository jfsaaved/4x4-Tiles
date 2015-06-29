package com.juliansaavedra.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juliansaavedra.game.MomoGame;
import com.juliansaavedra.game.ui.TextImage;
import com.juliansaavedra.game.ui.Tile;

/**
 * Created by 343076 on 11/06/2015.
 */
public class MenuState extends State {

    private TextImage play;
    private TextImage title;
    private TextImage settings;
    private TextImage exit;

    public MenuState(GSM gsm) {
        super(gsm);

        title = new TextImage("MOMOGAME",MomoGame.WIDTH / 2, MomoGame.HEIGHT / 2 + 200 , 1);
        play = new TextImage("START",MomoGame.WIDTH / 2, MomoGame.HEIGHT / 2, 1);
        settings = new TextImage("SETTINGS",MomoGame.WIDTH / 2, MomoGame.HEIGHT / 2 - 100, 1);
        exit = new TextImage("EXIT",MomoGame.WIDTH / 2, MomoGame.HEIGHT / 2 - 200, 1);

    }

    public void handleInput() {
        if(Gdx.input.justTouched()){
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(mouse);
            if(play.contains(mouse.x,mouse.y)){
                gsm.set(new DifficultyState(gsm));
            }
            else if(settings.contains(mouse.x,mouse.y)){
                gsm.set(new SettingsState(gsm));
            }
            else if(exit.contains(mouse.x,mouse.y)){
                Gdx.app.exit();
            }
        }
    }

    public void update(float dt) {
        handleInput();
    }

    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        title.render(sb);
        play.render(sb);
        settings.render(sb);
        exit.render(sb);
        sb.end();
    }

}
