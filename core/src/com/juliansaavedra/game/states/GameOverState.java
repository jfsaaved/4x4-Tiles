package com.juliansaavedra.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juliansaavedra.game.MomoGame;
import com.juliansaavedra.game.handler.MomoPreferences;
import com.juliansaavedra.game.ui.TextImage;

/**
 * Created by 343076 on 20/06/2015.
 */
public class GameOverState extends State {

    private TextImage gameOver;
    private TextImage scoreImage;
    private TextImage hiScoreImage;
    private int highScore;

    public GameOverState(GSM gsm, int newScore){
        super(gsm);

        MomoPreferences pref = new MomoPreferences();

        if(newScore > pref.getHighScore()){
            highScore = newScore;
            pref.setHighScore(newScore);
        }
        else{
            highScore = pref.getHighScore();
        }

        hiScoreImage = new TextImage("HIGHSCORE "+highScore, MomoGame.WIDTH/2 + 150,MomoGame.HEIGHT/2 - 100, 0.50f);
        scoreImage = new TextImage(""+newScore, MomoGame.WIDTH/2,MomoGame.HEIGHT/2,1);
        gameOver = new TextImage("GAME OVER", MomoGame.WIDTH/2,MomoGame.HEIGHT/2 + 100,1);

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
        hiScoreImage.render(sb);
        sb.end();
    }


}
