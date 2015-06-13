package com.juliansaavedra.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.juliansaavedra.game.MomoGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = MomoGame.WIDTH/2;
        config.height = MomoGame.HEIGHT/2;
        config.title = MomoGame.TITLE;


		new LwjglApplication(new MomoGame(), config);
	}
}
