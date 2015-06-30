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

    private TextImage retryText;
    private TextImage backText;

    private String difficulty;
    private int highScore;

    public GameOverState(GSM gsm, int newScore, String level){
        super(gsm);

        difficulty = level;

        if(newScore > MomoGame.pref.getHighScore()){
            highScore = newScore;
            MomoGame.pref.setHighScore(newScore);
        }
        else{
            highScore = MomoGame.pref.getHighScore();
        }

        gameOver = new TextImage("GAME OVER", MomoGame.WIDTH/2,MomoGame.HEIGHT/2 + 200,1);
        scoreImage = new TextImage(""+newScore, MomoGame.WIDTH/2,MomoGame.HEIGHT/2 + 100,1);
        hiScoreImage = new TextImage("HIGHSCORE "+highScore, MomoGame.WIDTH/2 + 150,MomoGame.HEIGHT/2, 0.50f);
        retryText = new TextImage("RETRY", MomoGame.WIDTH/2, MomoGame.HEIGHT/2 - 100, 1);
        backText = new TextImage("MAIN MENU", MomoGame.WIDTH/2,MomoGame.HEIGHT/2 - 200, 1);

    }

    public void handleInput(){
        if(Gdx.input.justTouched()){
            mouse.set(Gdx.input.getX(),Gdx.input.getY(),0);
            cam.unproject(mouse);

            if(retryText.contains(mouse.x,mouse.y)){
                gsm.set(new PlayState(gsm,difficulty));
            }
            else if(backText.contains(mouse.x,mouse.y)){
                gsm.set(new MenuState(gsm));
            }

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
        retryText.render(sb);
        backText.render(sb);
        sb.end();
    }


}
