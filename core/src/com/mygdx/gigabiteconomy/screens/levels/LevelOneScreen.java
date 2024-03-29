package com.mygdx.gigabiteconomy.screens.levels;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.sprites.tiled.House;
import com.mygdx.gigabiteconomy.sprites.tiled.*;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite.Weapon;
import com.mygdx.gigabiteconomy.sprites.tiled.enemies.*;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Level 1 screen
 */
public class LevelOneScreen extends LevelScreen {
    // Level string
    private static final String LEVEL = "level1";
    // Background texture
    private static final String BACKGROUND_TEXTURE_PNG = "levels/level1.png";

    // Player character
    private final Player player = new Player(Weapon.KNIFE, 0 , 7, 1, 1);

    /* ENEMIES */
    private final ArrayList<Enemy> enemies = new ArrayList<Enemy>(Arrays.asList(
        new BatGuy(50, 3, LEVEL, player),
        new Fighter(15, 7, LEVEL, player),
        new Dog(25, 2, LEVEL, player),
        new BatGuy(20, 5, LEVEL, player),
        new Fighter(30, 4, LEVEL, player)
    ));

    // Parcel van (for Player to collect parcels from)
    private final ParcelVan parcelVan = new ParcelVan(0, 0);

    /* STATIC SPRITES (HOUSES, FENCES ETC...) */
    private final House houseOne = new House(House.HouseType.DETACHED, 0);
    private final House houseTwo = new House(House.HouseType.TWO_STORY, 10);
    private final House houseThree = new House(House.HouseType.DETACHED, 20);
    private final House houseFour = new House(House.HouseType.TWO_STORY, 31);
    private final House houseFive = new House(House.HouseType.DETACHED, 38);

    int[][] fenceCoords = {
            {7,8},{8,8}, {9,8}, {10,8}, {17,8}, {18,8}, {19,8},{20,8}, {27,8},{28,8}, {29,8}, {30,8}, {31,8}, {38,8},

            /* Stops the map a bit short - it's huge */
            {55,8}, {55,7}, {55,6}, {55,5}, {55,4}, {55,3}, {55,2}, {55,1}, {55,0}
    };

    int[][] canCoords = {
            {8, 8}, {19, 8},
    };


    int[][] lampCoords = {
            {28, 8}
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
            staticSprites.add(new StaticSprite(String.format("static_sprites/%s/fence.png", LEVEL), coords[0], coords[1], 1, 1));
        }

        for (int[] coords : canCoords) {
            staticSprites.add(new StaticSprite(String.format("static_sprites/%s/trashcan.png", LEVEL), coords[0], coords[1], 1, 1));
        }
        for (int[] coords : lampCoords) {
            staticSprites.add(new StaticSprite(String.format("static_sprites/%s/lampost.png", LEVEL), coords[0], coords[1], 1, 1));
        }

        addPlayer(player);

        addEnemies(enemies);

        addParcelVan(parcelVan);
        addSprites(staticSprites);
    }
}
