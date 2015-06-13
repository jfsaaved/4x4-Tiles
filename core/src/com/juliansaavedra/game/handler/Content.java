package com.juliansaavedra.game.handler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

/**
 * Created by 343076 on 30/05/2015.
 */
public class Content {

    private HashMap<String, TextureAtlas> atlases;
    private HashMap<String, Sound> sounds;
    private HashMap<String, Music> musics;

    public Content() {
        atlases = new HashMap<String, TextureAtlas>();
        sounds = new HashMap<String, Sound>();
        musics = new HashMap<String, Music>();
    }

    public void loadAtlas(String path, String key) {
        atlases.put(key, new TextureAtlas(Gdx.files.internal(path)));
    }

    public TextureAtlas getAtlas(String key) {
       return atlases.get(key);
    }

    public void loadSound(String path, String key){
        sounds.put(key, Gdx.audio.newSound(Gdx.files.internal(path)));
    }

    public Sound getSound(String key){
        return sounds.get(key);
    }

    public void loadMusic(String path, String key){
        musics.put(key, Gdx.audio.newMusic(Gdx.files.internal(path)));
    }

    public Music getMusic(String key) { return musics.get(key); }
}
