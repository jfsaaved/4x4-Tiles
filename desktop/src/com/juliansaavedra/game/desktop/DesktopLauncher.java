package com.juliansaavedra.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.juliansaavedra.game.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.addIcon("icons/128x128.png", Files.FileType.Internal);
        config.addIcon("icons/32x32.png", Files.FileType.Internal);
        config.addIcon("icons/16x16.png", Files.FileType.Internal);
        config.width = MainGame.WIDTH;
        config.height = MainGame.HEIGHT;
        config.title = MainGame.TITLE;


		new LwjglApplication(new MainGame(), config);
	}
}
