package com.juliansaavedra.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juliansaavedra.game.MomoGame;
import com.juliansaavedra.game.ui.TextImage;

/**
 * Created by 343076 on 20/06/2015.
 */
public class DifficultyState extends State {

    private TextImage easy;
    private TextImage normal;
    private TextImage hard;
    private TextImage veryHard;
    private TextImage insane;

   public DifficultyState(GSM gsm){
       super(gsm);

       easy = new TextImage("easy", MomoGame.WIDTH/2, MomoGame.HEIGHT/2 + 200, 1);
       normal = new TextImage("normal",MomoGame.WIDTH/2,MomoGame.HEIGHT/2 + 100, 1);
       hard = new TextImage("hard",MomoGame.WIDTH/2, MomoGame.HEIGHT/2,1);
       veryHard = new TextImage("very hard",MomoGame.WIDTH/2, MomoGame.HEIGHT/2 - 100, 1);
       insane = new TextImage("insane",MomoGame.WIDTH/2, MomoGame.HEIGHT/2 - 200, 1);

   }

    public void handleInput(){
        if(Gdx.input.justTouched()){
            mouse.set(Gdx.input.getX(),Gdx.input.getY(),0);
            cam.unproject(mouse);

            if(easy.contains(mouse.x,mouse.y)){
                gsm.set(new PlayState(gsm,"easy"));
            }
            else if(normal.contains(mouse.x,mouse.y)){
                gsm.set(new PlayState(gsm,"normal"));
            }
            else if(hard.contains(mouse.x,mouse.y)){
                gsm.set(new PlayState(gsm,"hard"));
            }
            else if(veryHard.contains(mouse.x,mouse.y)){
                gsm.set(new PlayState(gsm,"very hard"));
            }
            else if(insane.contains(mouse.x,mouse.y)){
                gsm.set(new PlayState(gsm,"insane"));
            }

        }
    }

    public void update(float dt){
        handleInput();
    }

    public void render(SpriteBatch sb){
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        easy.render(sb);
        normal.render(sb);
        hard.render(sb);
        veryHard.render(sb);
        insane.render(sb);
        sb.end();
    }
}
