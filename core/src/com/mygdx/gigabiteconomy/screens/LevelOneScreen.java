package com.mygdx.gigabiteconomy.screens;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.sprites.*;
import com.badlogic.gdx.graphics.Texture;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Level 1 screen
 */
public class LevelOneScreen extends LevelScreen {
    // Player character
    private static final Player PLAYER = new Player("amzn_9iron.txt",0 , 0);

    /* ENEMIES */
    private static final Enemy ENEMY_ONE = new Enemy("amzn_9iron.txt", 2, 2);
    private static final ArrayList<GameObject> ENEMIES = new ArrayList<GameObject>(Arrays.asList(ENEMY_ONE));

    /* STATIC SPRITES (FENCES ETC...) */
    private static final StaticSprite FENCE = new StaticSprite("finished_assets/obstacles/fence.png", 1, 1);
    private static final ArrayList<StaticSprite> STATIC_SPRITES = new ArrayList<StaticSprite>(Arrays.asList(FENCE));

    // Level screen background texture
    private static final Texture BACKGROUND_TEXTURE = new Texture("finished_assets/levels/level1.png");

    /**
     * Creates a new screen instance for Level 1 based off the LevelScreen abstract class, which contains all shared
     * methods/properties between levels and implements the LibGDX Screen interface.
     *
     * @param director the instance of the game director
     */
    public LevelOneScreen(GigabitEconomy director) {
        super(director, PLAYER, ENEMIES, STATIC_SPRITES, BACKGROUND_TEXTURE);
    }
}
