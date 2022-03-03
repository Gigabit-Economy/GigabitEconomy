package com.mygdx.gigabiteconomy.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.gigabiteconomy.GigabitEconomy;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Gigabit Economy";
		config.width = 1920;
		config.height = 1080;
		System.out.println("Creating new GBit");
		new LwjglApplication(new GigabitEconomy(), config);
	}
} 