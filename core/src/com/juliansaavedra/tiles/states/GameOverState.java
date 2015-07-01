package com.juliansaavedra.tiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juliansaavedra.tiles.MainGame;
import com.juliansaavedra.tiles.ui.TextImage;

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

        if(newScore > MainGame.pref.getHighScore()){
            highScore = newScore;
            MainGame.pref.setHighScore(newScore);
        }
        else{
            highScore = MainGame.pref.getHighScore();
        }

        gameOver = new TextImage("GAME OVER", MainGame.WIDTH/2, MainGame.HEIGHT/2 + 200,1);
        scoreImage = new TextImage(""+newScore, MainGame.WIDTH/2, MainGame.HEIGHT/2 + 100,1);
        hiScoreImage = new TextImage("HIGHSCORE "+highScore, MainGame.WIDTH/2 + 150, MainGame.HEIGHT/2, 0.50f);
        retryText = new TextImage("RETRY", MainGame.WIDTH/2, MainGame.HEIGHT/2 - 100, 1);
        backText = new TextImage("MAIN MENU", MainGame.WIDTH/2, MainGame.HEIGHT/2 - 200, 1);

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
