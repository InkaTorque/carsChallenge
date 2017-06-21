package com.leapgs.cars.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.leapgs.cars.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Cars Racing Challenge";
		config.width = 400;
		config.height = 400;
		new LwjglApplication(new MainGame(), config);
	}
}
