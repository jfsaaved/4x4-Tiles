package com.juliansaavedra.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juliansaavedra.game.MomoGame;
import com.juliansaavedra.game.ui.TextImage;

/**
 * Created by 343076 on 20/06/2015.
 */
public class GameOverState extends State {

    private TextImage gameOver;
    private TextImage scoreImage;
    private int score;

    public GameOverState(GSM gsm, int getScore){
        super(gsm);

        score = getScore;

        scoreImage = new TextImage(score+"", MomoGame.WIDTH/2,MomoGame.HEIGHT/2,1);
        gameOver = new TextImage("game over", MomoGame.WIDTH/2,MomoGame.HEIGHT/2 + 100,1);

    }

    public void handleInput(){
        if(Gdx.input.justTouched()){
            //mouse.set(Gdx.input.getX(),Gdx.input.getY(),0);
            //cam.unproject(mouse);

            gsm.set(new MenuState(gsm));

        }
    }

    public void update(float dt){
        handleInput();
    }

    public void render(SpriteBatch sb){
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        gameOver.render(sb);
        scoreImage.render(sb);
        sb.end();
    }


}
