package com.juliansaavedra.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.juliansaavedra.game.MainGame;
import com.juliansaavedra.game.ui.TextImage;

/**
 * Created by 343076 on 29/06/2015.
 */
public class SettingsState extends State {

    private TextImage sounds;
    private TextImage soundsToggleText;
    private TextImage soundPack;
    private TextImage soundPackToggleText;
    private TextImage back;

    private String soundToggle;
    private String soundPackToggle;

    public SettingsState(GSM gsm){
        super(gsm);

        if(!MainGame.pref.getSoundPref()){
            soundToggle = "OFF";
        }
        else{
            soundToggle = "ON";
        }

        if(!MainGame.pref.getPackPref()){
            soundPackToggle = "TYPE2";
        }
        else{
            soundPackToggle = "TYPE1";
        }

        sounds = new TextImage("SOUNDS", MainGame.WIDTH/2 - 100, MainGame.HEIGHT/2,1);
        soundsToggleText = new TextImage(soundToggle, MainGame.WIDTH/2 + 150, MainGame.HEIGHT/2 - 50,1);

        soundPack = new TextImage("SOUNDPACK", MainGame.WIDTH/2 - 35, MainGame.HEIGHT/2 + 200,1);
        soundPackToggleText = new TextImage(soundPackToggle, MainGame.WIDTH/2 + 125, MainGame.HEIGHT/2 + 150,1);

        back = new TextImage("BACK", MainGame.WIDTH/2, MainGame.HEIGHT/2 - 200,1);

    }

    public void handleInput(){

        if(Gdx.input.justTouched()){
            mouse.set(Gdx.input.getX(),Gdx.input.getY(),0);
            cam.unproject(mouse);

            if(soundsToggleText.contains(mouse.x,mouse.y)){
                if(soundToggle.equals("ON")) {
                    soundToggle = "OFF";
                    MainGame.pref.setSoundPref(false);
                    soundsToggleText.update("OFF", MainGame.WIDTH / 2 + 150, MainGame.HEIGHT / 2 - 50);
                }
                else{
                    soundToggle = "ON";
                    MainGame.pref.setSoundPref(true);
                    soundsToggleText.update("ON", MainGame.WIDTH / 2 + 150, MainGame.HEIGHT / 2 - 50);
                }
            }

            else if(soundPackToggleText.contains(mouse.x,mouse.y)){
                if(soundPackToggle.equals("TYPE1")){
                    soundPackToggle = "TYPE2";
                    MainGame.pref.setPackPref(false);
                    soundPackToggleText.update("TYPE2", MainGame.WIDTH/2 + 125, MainGame.HEIGHT/2 + 150);
                }
                else{
                    soundPackToggle = "TYPE1";

                    MainGame.pref.setPackPref(true);
                    soundPackToggleText.update("TYPE1", MainGame.WIDTH/2 + 125, MainGame.HEIGHT/2 + 150);
                }
            }

            else if(back.contains(mouse.x,mouse.y)){
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

        sounds.render(sb);
        soundsToggleText.render(sb);

        soundPack.render(sb);
        soundPackToggleText.render(sb);

        back.render(sb);

        sb.end();


    }

}
