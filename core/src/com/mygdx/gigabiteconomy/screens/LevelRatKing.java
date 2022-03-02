package com.mygdx.gigabiteconomy.screens;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.sprites.tiled.*;
import com.mygdx.gigabiteconomy.sprites.tiled.enemies.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class LevelRatKing extends LevelScreen {
    // Level string
    private static final String LEVEL = "level3";
    // Level screen backgro1.5und texture
    private static final String BACKGROUND_TEXTURE_PNG = "finished_assets/levels/level2.png";

    // Player character
    private final MovingSprite.Weapon playerWeapon = MovingSprite.Weapon.KATANA;
    private final Player player = new Player(playerWeapon, 0 , 4, 1, 1);
    private final RatKing ratKing = new RatKing(26, 3, LEVEL, player);

    /* ENEMIES */
    private final ArrayList<Enemy> boxes = new ArrayList<Enemy>(Arrays.asList(
            ratKing,
            /* THE MINIONS */
            new RatKingFort(25, 0, player, ratKing)
            /*new RatKingFort(25, 1, player, ratKing, 2f, 1.5f, 65f, new LinkedList<MovingSprite.DIRECTION>()),
            new RatKingFort(25, 2, player, ratKing, 2f, 1.5f, 65f, new LinkedList<MovingSprite.DIRECTION>()),
            new RatKingFort(25, 3, player, ratKing, 2f, 1.5f, 65f, new LinkedList<MovingSprite.DIRECTION>()),
            new RatKingFort(25, 4, player, ratKing, 2f, 1.5f, 65f, new LinkedList<MovingSprite.DIRECTION>()),
            new RatKingFort(25, 5, player, ratKing, 2f, 1.5f, 65f, new LinkedList<MovingSprite.DIRECTION>()),
            new RatKingFort(25, 6, player, ratKing, 2f, 1.5f, 65f, new LinkedList<MovingSprite.DIRECTION>()),
            new RatKingFort(25, 7, player, ratKing, 2f, 1.5f, 65f, new LinkedList<MovingSprite.DIRECTION>()),
            new RatKingFort(25, 8, player, ratKing, 2f, 1.5f, 65f, new LinkedList<MovingSprite.DIRECTION>())*/
    ));
    private final ArrayList<Enemy> enemies = new ArrayList<Enemy>(Arrays.asList(
            new RatKing(26, 0, LEVEL, player)
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
    public LevelRatKing(GigabitEconomy director) {
        super(director, BACKGROUND_TEXTURE_PNG);

        for (int[] coords : fenceCoords) {
            staticSprites.add(new StaticSprite("static_sprites/level1/fence.png", coords[0], coords[1], 1, 1));
        }

        for (int[] coords : canCoords) {
            staticSprites.add(new StaticSprite("static_sprites/level1/trashcan.png", coords[0], coords[1], 1, 1));
        }

        addPlayer(player);
        player.new PlayerHealthBar(director);
        player.hideParcels();
        addEnemies(boxes);
        for (Enemy enemy : boxes) {
            enemy.new EnemyHealthBar(director);
        }
        addSprites(staticSprites);
    }
}
