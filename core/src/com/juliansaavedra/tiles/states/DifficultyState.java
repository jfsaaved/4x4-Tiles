package com.juliansaavedra.tiles.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.juliansaavedra.tiles.MainGame;
import com.juliansaavedra.tiles.ui.TextImage;

/**
 * Created by 343076 on 20/06/2015.
 */
public class DifficultyState extends State {

    private Array<TextImage> difficulties;
    private String[] text = {"EASY","NORMAL","HARD","VERY HARD","INSANE"};

   public DifficultyState(GSM gsm){
       super(gsm);

       int position = 200;
       difficulties = new Array<TextImage>();
       for(int i = 0 ; i < text.length ; i++){
           difficulties.add(new TextImage(text[i], MainGame.WIDTH / 2, MainGame.HEIGHT / 2 + position, 1));
           position = position - 100;
       }

   }

    public void handleInput(){
        if(Gdx.input.justTouched()){
            mouse.set(Gdx.input.getX(),Gdx.input.getY(),0);
            cam.unproject(mouse);

            for(int i = 0; i < difficulties.size; i++){
                if(difficulties.get(i).contains(mouse.x,mouse.y)) {
                    gsm.set(new PlayState(gsm, text[i]));
                }
            }

        }
    }

    public void update(float dt){
        handleInput();
    }

    public void render(SpriteBatch sb){
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        for(int i = 0 ; i < difficulties.size; i ++){
            difficulties.get(i).render(sb);
        }
        sb.end();
    }
}
