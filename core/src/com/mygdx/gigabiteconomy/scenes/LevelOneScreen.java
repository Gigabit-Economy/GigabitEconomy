package com.mygdx.gigabiteconomy.scenes;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.Player;
import com.badlogic.gdx.graphics.Texture;

/**
 * Level 1 screen
 */
public class LevelOneScreen extends LevelScreen {
    private static final Player p = new Player("amzn_9iron.txt",0 , 0);
    private static final Texture bT = new Texture("finished_assets/levels/level1.png");

    public LevelOneScreen(GigabitEconomy director) {
        super(director, p, bT);
    }
}
