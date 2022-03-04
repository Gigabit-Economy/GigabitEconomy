package com.mygdx.gigabiteconomy.screens;

import com.badlogic.gdx.Gdx;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.exceptions.ScreenException;
import com.mygdx.gigabiteconomy.sprites.tiled.*;
import com.mygdx.gigabiteconomy.sprites.tiled.enemies.*;

import java.util.ArrayList;
import java.util.Arrays;

public class LevelThreeScreen extends LevelScreen {
    // Level string
    private static final String LEVEL = "ratking";
    // Level screen background texture
    private static final String BACKGROUND_TEXTURE_PNG = "levels/level3.png";

    // Player character
    private final MovingSprite.Weapon playerWeapon = MovingSprite.Weapon.KATANA;
    private final Player player = new Player(playerWeapon, 0 , 4, 1, 1);
    private final RatKing ratKing = new RatKing(26, 3, LEVEL, player);

    /* ENEMIES */
    private final ArrayList<Enemy> enemies = new ArrayList<Enemy>(Arrays.asList(
            ratKing,
            /* THE MINIONS */
            new RatKingFort(25, 0, player, ratKing)
    ));

    // Parcel van (for Player to collect parcels from)

    /* STATIC SPRITES (HOUSES, FENCES ETC...) */
    private final ArrayList<StaticSprite> staticSprites = new ArrayList<>();

    int[][] fenceCoords = {

            /* Stops the map a bit short - it's huge */
            {35,8}, {35,7}, {35,6}, {35,5}, {35,4}, {35,3}, {35,2}, {35,1}, {35,0}
    };

    int[][] canCoords = {
    };

    /**
     * Creates a new screen instance for Level 1 based off the LevelScreen abstract class, which contains all shared
     * methods/properties between levels and implements the LibGDX Screen interface.
     *
     * @param director the instance of the game director
     */
    public LevelThreeScreen(GigabitEconomy director) {
        super(director, BACKGROUND_TEXTURE_PNG);

        for (int[] coords : fenceCoords) {
            staticSprites.add(new StaticSprite("static_sprites/level2/fence.png", coords[0], coords[1], 1, 1));
        }

        for (int[] coords : canCoords) {
            staticSprites.add(new StaticSprite("static_sprites/level1/trashcan.png", coords[0], coords[1], 1, 1));
        }

        addPlayer(player);
        player.new PlayerHealthBar(director);
        player.hideParcels();
        addEnemies(enemies);
        for (Enemy enemy : enemies) {
            enemy.new EnemyHealthBar(director);
        }

        enemies.get(1).hideHealthBar();
        enemies.get(0).attack(MovingSprite.Weapon.BOXFORT);

        addSprites(staticSprites);
    }

    /**
     * Complete the level (called when the level is complete i.e. the final parcel is delivered)
     */
    @Override
    public void complete() {
        // add proportion of health (multiplied by 5) as score
        addToScore(Math.round(player.getHealth()) * 5);
        // save score
        getScore().saveScore();

        try {
            getDirector().switchScreen("GameComplete");
        } catch (ScreenException ex) {
            Gdx.app.error("Exception", "The screen could not be switched when game (level 3) complete", ex);
            System.exit(-1);
        }
    }
}
