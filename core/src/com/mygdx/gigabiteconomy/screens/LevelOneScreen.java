package com.mygdx.gigabiteconomy.screens;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.sprites.tiled.House;
import com.mygdx.gigabiteconomy.sprites.tiled.*;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite.Weapon;
import com.mygdx.gigabiteconomy.sprites.tiled.enemies.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Level 1 screen
 */
public class LevelOneScreen extends LevelScreen {
    // Background texture
    private static final String BACKGROUND_TEXTURE_PNG = "finished_assets/levels/level1.png";

    // Player character
    private final Player player = new Player(Weapon.KNIFE, 0 , 7, 1, 1);

    /* ENEMIES */
    private final ArrayList<Enemy> enemies = new ArrayList<Enemy>(Arrays.asList(
            new BatGuy(5, 3, player),
            /* Example of completely custom fighter */
            new Fighter(15, 7, player, 3.4f, 2.8f, 95f, new LinkedList<MovingSprite.DIRECTION>(Arrays.asList(MovingSprite.DIRECTION.WEST, MovingSprite.DIRECTION.EAST))),
            new Dog(25, 3, player)
    ));

    // Parcel van (for Player to collect parcels from)
    private final ParcelVan parcelVan = new ParcelVan(0, 0);

    /* STATIC SPRITES (HOUSES, FENCES ETC...) */
    private final House houseOne = new House(House.HouseType.DETACHED, 0, "level1");
    private final House houseTwo = new House(House.HouseType.TWO_STORY, 10, "level1");
    private final House houseThree = new House(House.HouseType.DETACHED, 20, "level1");
    private final House houseFour = new House(House.HouseType.TWO_STORY, 31, "level1");
    private final House houseFive = new House(House.HouseType.DETACHED, 38, "level1");

    private final ArrayList<StaticSprite> fences = new ArrayList<>();
    private final ArrayList<StaticSprite> cans = new ArrayList<>();

    int[][] fenceCoords = {
            {7,8}, {9,8}, {10,8}, {17,8}, {18,8}, {20,8}, {27,8}, {29,8}, {30,8}, {31,8}, {38,8},

            /* Stops the map a bit short - it's huge */
            {55,8}, {55,7}, {55,6}, {55,5}, {55,4}, {55,3}, {55,2}, {55,1}, {55,0}
    };

    int[][] canCoords = {
            {8, 8}, {19, 8}, {28, 8}
    };

    private final ArrayList<StaticSprite> staticSprites = new ArrayList<StaticSprite>(Arrays.asList(houseOne, houseTwo, houseThree, houseFour, houseFive));

    /**
     * Creates a new screen instance for Level 1 based off the LevelScreen abstract class, which contains all shared
     * methods/properties between levels and implements the LibGDX Screen interface.
     *
     * @param director the instance of the game director
     */
    public LevelOneScreen(GigabitEconomy director) {
        super(director, BACKGROUND_TEXTURE_PNG);

        for (int[] coords : fenceCoords) {
            fences.add(new StaticSprite("finished_assets/static_sprites/level1/fence.png", coords[0], coords[1], 1, 1));
        }

        for (int[] coords : canCoords) {
            cans.add(new StaticSprite("finished_assets/static_sprites/level1/trashcan.png", coords[0], coords[1], 1, 1));
        }

        player.addHealthBar(director);
        addPlayer(player);


        addEnemies(enemies);
        for (Enemy enemy : enemies) {
            enemy.addHealthBar(director);
        }

        addParcelVan(parcelVan);
        addSprites(staticSprites);
        addSprites(fences);
        addSprites(cans);
    }
}
