package com.mygdx.gigabiteconomy.screens;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.exceptions.TileMovementException;
import com.mygdx.gigabiteconomy.sprites.tiled.*;
import com.mygdx.gigabiteconomy.sprites.tiled.enemies.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class LevelRatKing extends LevelScreen {
    // Level screen backgro1.5und texture
    private static final String BACKGROUND_TEXTURE_PNG = "finished_assets/levels/level1.png";

    // Player character
    private final MovingSprite.Weapon playerWeapon = MovingSprite.Weapon.KNIFE;
    private final Player player = new Player(playerWeapon, 0 , 7, 1, 1);
    private final RatKing ratKing = new RatKing(26, 3, player);

    /* ENEMIES */
    private final ArrayList<Enemy> boxes = new ArrayList<Enemy>(Arrays.asList(
            ratKing,
            /* THE MINIONS */
            new RatKingBoxes(25, 0, player, ratKing, 2f, 1.5f, 65f, new LinkedList<MovingSprite.DIRECTION>()),
            new RatKingBoxes(25, 1, player, ratKing, 2f, 1.5f, 65f, new LinkedList<MovingSprite.DIRECTION>()),
            new RatKingBoxes(25, 2, player, ratKing, 2f, 1.5f, 65f, new LinkedList<MovingSprite.DIRECTION>()),
            new RatKingBoxes(25, 3, player, ratKing, 2f, 1.5f, 65f, new LinkedList<MovingSprite.DIRECTION>()),
            new RatKingBoxes(25, 4, player, ratKing, 2f, 1.5f, 65f, new LinkedList<MovingSprite.DIRECTION>()),
            new RatKingBoxes(25, 5, player, ratKing, 2f, 1.5f, 65f, new LinkedList<MovingSprite.DIRECTION>()),
            new RatKingBoxes(25, 6, player, ratKing, 2f, 1.5f, 65f, new LinkedList<MovingSprite.DIRECTION>()),
            new RatKingBoxes(25, 7, player, ratKing, 2f, 1.5f, 65f, new LinkedList<MovingSprite.DIRECTION>()),
            new RatKingBoxes(25, 8, player, ratKing, 2f, 1.5f, 65f, new LinkedList<MovingSprite.DIRECTION>())
    ));

    // Parcel van (for Player to collect parcels from)
    private final ParcelVan parcelVan = new ParcelVan(0, 0);

    /* STATIC SPRITES (HOUSES, FENCES ETC...) */

    private final ArrayList<StaticSprite> fences = new ArrayList<>();
    private final ArrayList<StaticSprite> cans = new ArrayList<>();

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
            fences.add(new StaticSprite("finished_assets/static_sprites/level1/fence.png", coords[0], coords[1], 1, 1));
        }

        for (int[] coords : canCoords) {
            cans.add(new StaticSprite("finished_assets/static_sprites/level1/trashcan.png", coords[0], coords[1], 1, 1));
        }

        addPlayer(player);
        player.new PlayerHealthBar(director);
        addEnemies(boxes);
        for (Enemy enemy : boxes) {
            enemy.new EnemyHealthBar(director);
        }
        addParcelVan(parcelVan);
        parcelVan.setToEmpty();
        parcelVan.setInactive();
        addSprites(fences);
        addSprites(cans);
    }
}
