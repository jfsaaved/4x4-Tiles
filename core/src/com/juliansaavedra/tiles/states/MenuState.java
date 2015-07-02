package com.juliansaavedra.tiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juliansaavedra.tiles.MainGame;
import com.juliansaavedra.tiles.ui.TextImage;

/**
 * Created by 343076 on 11/06/2015.
 */
public class MenuState extends State {

    private TextImage play;
    //private TextImage title;
    private TextImage settings;
    private TextImage exit;
    private TextImage highScoreImage;

    private Texture background;
    private Texture title;
    private int currentBGX = 1920;
    private float timer = 1;

    private int highScore;



    public MenuState(GSM gsm) {
        super(gsm);

        highScore = MainGame.pref.getHighScore();

        //title = new TextImage(MainGame.TITLE.toUpperCase(), MainGame.WIDTH / 2, MainGame.HEIGHT / 2 + 200 , 1);
        play = new TextImage("START", MainGame.WIDTH / 2, MainGame.HEIGHT / 2, 1);
        settings = new TextImage("SETTINGS", MainGame.WIDTH / 2, MainGame.HEIGHT / 2 - 100, 1);
        exit = new TextImage("EXIT", MainGame.WIDTH / 2, MainGame.HEIGHT / 2 - 200, 1);
        highScoreImage = new TextImage("HIGHSCORE "+highScore, MainGame.WIDTH/2 + 150, MainGame.HEIGHT/2 - 300, 0.50f);

        background = MainGame.res.getTexture("bg");
        title = MainGame.res.getTexture("title");

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

        if(timer > 0){
            timer--;
        }
        else if(timer <= 0){
            timer = 1;
            currentBGX--;
            if(currentBGX <= 0){
                currentBGX = 1920;
            }
        }

    }

    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(background,currentBGX - 1920,0);
        sb.draw(background,currentBGX,0);

        //title.render(sb);
        play.render(sb);
        settings.render(sb);
        exit.render(sb);
        highScoreImage.render(sb);

        sb.draw(title,MainGame.WIDTH/2 - 150,MainGame.HEIGHT/2 + 100);
        sb.end();


    }

}
