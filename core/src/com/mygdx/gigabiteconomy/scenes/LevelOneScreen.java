package com.mygdx.gigabiteconomy.scenes;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.Player;
import com.mygdx.gigabiteconomy.Enemy;
import com.badlogic.gdx.graphics.Texture;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Level 1 screen
 */
public class LevelOneScreen extends LevelScreen {
    private static final Player PLAYER = new Player("amzn_9iron.txt",0 , 0);
    private static final Enemy ENEMY_ONE = new Enemy("amzn_9iron.txt", 500, 500);
    private static ArrayList<Enemy> ENEMIES = new ArrayList<Enemy>(Arrays.asList(ENEMY_ONE));
    private static final Texture BACKGROUND_TEXTURE = new Texture("finished_assets/levels/level1.png");

    public LevelOneScreen(GigabitEconomy director) {
        super(director, PLAYER, ENEMIES, BACKGROUND_TEXTURE);
    }
}
