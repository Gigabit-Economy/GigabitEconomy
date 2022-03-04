package com.mygdx.gigabiteconomy.desktop;


import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.gigabiteconomy.GigabitEconomy;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Gigabit Economy");
		config.setWindowedMode(1920, 1080);
		config.useVsync(true);

		new Lwjgl3Application(new GigabitEconomy(), config);
	}
}
