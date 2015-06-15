package com.juliansaavedra.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juliansaavedra.game.MomoGame;
import com.juliansaavedra.game.ui.Tile;

/**
 * Created by 343076 on 11/06/2015.
 */
public class MenuState extends State {

    public MenuState(GSM gsm) {
        super(gsm);
        System.out.println("Entering Main Menu!");
    }

    public void handleInput() {
        if(Gdx.input.isTouched()){
            System.out.println("Exiting Main Menu!");
            gsm.set(new PlayState(gsm,"0"));
        }
    }

    public void update(float dt) {
        handleInput();
    }

    public void render(SpriteBatch sb) {

    }

}
