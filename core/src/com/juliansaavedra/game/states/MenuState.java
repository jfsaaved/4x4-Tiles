package com.juliansaavedra.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private TextImage exit;

    public MenuState(GSM gsm) {
        super(gsm);
        System.out.println("Entering Main Menu!");

        title = new TextImage("momo",MomoGame.WIDTH / 2, MomoGame.HEIGHT / 2 + 200 , 1);
        play = new TextImage("play",MomoGame.WIDTH / 2, MomoGame.HEIGHT / 2, 1);
        exit = new TextImage("exit",MomoGame.WIDTH / 2, MomoGame.HEIGHT / 2 - 100, 1);

    }

    public void handleInput() {
        if(Gdx.input.isTouched()){
            mouse.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(mouse);
            if(play.contains(mouse.x,mouse.y)){
                System.out.println("Exiting Main Menu!");
                gsm.set(new PlayState(gsm,"insane"));
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
        exit.render(sb);
        sb.end();
    }

}
