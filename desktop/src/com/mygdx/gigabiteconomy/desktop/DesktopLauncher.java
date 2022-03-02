package com.mygdx.gigabiteconomy.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.screens.PauseMenu;

public class DesktopLauncher{
	LwjglApplicationConfiguration config;
	
    public static void main (String[] arg) {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        

        config.title = "Gigabit Economy";

        config.width = 1366;

        config.height = 728;

        //config.fullscreen = true;    

        config.vSyncEnabled = true;


        System.out.println("Creating new GBit");

        new LwjglApplication(new GigabitEconomy(), config);


    }

	public void setConfigLauncher(int width, int height) {
		
		config.height = height;
		config.width = width;

	}
}