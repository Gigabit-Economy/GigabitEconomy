package com.mygdx.gigabiteconomy.screens.levels;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.sprites.tiled.House;
import com.mygdx.gigabiteconomy.sprites.tiled.*;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite.Weapon;
import com.mygdx.gigabiteconomy.sprites.tiled.enemies.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Level 2 screen
 */
public class LevelTwoScreen extends LevelScreen {
    // Level string
    private static final String LEVEL = "level2";
    // Background texture
    private static final String BACKGROUND_TEXTURE_PNG = String.format("levels/%s.png", LEVEL);

    // Player character
    private final Player player = new Player(Weapon.KNIFE, 0 , 7, 1, 1);

    /* ENEMIES */
    private final ArrayList<Enemy> enemies = new ArrayList<Enemy>(Arrays.asList(
        new BatGuy(50, 3, LEVEL, player),
        new Fighter(15, 7, LEVEL, player, 3.4f, 2.8f, 60, new LinkedList<>(Arrays.asList(
                MovingSprite.DIRECTION.WEST, MovingSprite.DIRECTION.WEST, MovingSprite.DIRECTION.EAST, MovingSprite.DIRECTION.EAST,
                MovingSprite.DIRECTION.WEST, MovingSprite.DIRECTION.NORTH, MovingSprite.DIRECTION.EAST, MovingSprite.DIRECTION.SOUTH,
                MovingSprite.DIRECTION.SOUTH, MovingSprite.DIRECTION.WEST, MovingSprite.DIRECTION.EAST, MovingSprite.DIRECTION.EAST
        ))),
        new Dog(25, 2, LEVEL, player),
        new Dog(40, 5, LEVEL, player),
        new Dog(35, 4, LEVEL, player),
        new BatGuy(30, 5, LEVEL, player),
        new BatGuy(34, 7, LEVEL, player),
        new Fighter(32, 3, LEVEL, player),
        new BatGuy(20, 5, LEVEL, player),
        new Fighter(25, 6, LEVEL, player),
        new Fighter(16, 3, LEVEL, player),
        new Fighter(22, 4, LEVEL, player)
    ));

    // Parcel van (for Player to collect parcels from)
    private final ParcelVan parcelVan = new ParcelVan(0, 0);

    /* STATIC SPRITES (HOUSES, FENCES ETC...) */
    private final House houseOne = new House(House.HouseType.FLATS1, 0);
    private final House houseTwo = new House(House.HouseType.FLATS2, 10);
    private final House houseFour = new House(House.HouseType.OFFICES, 42);
    private final House houseFive = new House(House.HouseType.OFFICES, 36);
    private final House houseThree = new House(House.HouseType.OFFICES, 30);

    int[][] fenceCoords = {
            {0,8},
            {7,8}, {8,8},{9,8}, {10,8},
            {17,8}, {18,8},{19,8}, {20,8}, {21,8}, {22,8}, {23,8},{24,8}, {25,8}, {26,8}, {27,8},{28,8}, {29,8}, {30,8},
            {56,8}, {57,8}, {58,8},{59,8}, {60,8}, {61,8}, {62,8},{63,8}, {64,8}, {65,8}, {66,8},{67,8}, {68,8},
            {49,8},{50,8}, {51,8},{52,8},{53,8}, {54,8},

            /* Stops the map a bit short - it's huge */
            {55,8}, {55,7}, {55,6}, {55,5}, {55,4}, {55,3}, {55,2}, {55,1}, {55,0}
    };

    int[][] lampCoords = {
            {8, 8}, {19, 8}, {28, 8}
    };
    //static sprites in the middle of the map have clipping blocked tile is not where the image is

    private final ArrayList<StaticSprite> staticSprites = new ArrayList<StaticSprite>(Arrays.asList(houseOne, houseTwo, houseThree, houseFour, houseFive));

    /**
     * Creates a new screen instance for Level 2 based off the LevelScreen abstract class, which contains all shared
     * methods/properties between levels and implements the LibGDX Screen interface.
     *
     * @param director the instance of the game director
     */
    public LevelTwoScreen(GigabitEconomy director) {
        super(director, BACKGROUND_TEXTURE_PNG);

        for (int[] coords : fenceCoords) {
            staticSprites.add(new StaticSprite(String.format("static_sprites/%s/fence.png", LEVEL), coords[0], coords[1], 1, 1));
        }

        for (int[] coords : lampCoords) {
            staticSprites.add(new StaticSprite(String.format("static_sprites/%s/lampost2.png", LEVEL), coords[0], coords[1], 1, 1));
        }


        player.addHealthBar(director);
        addPlayer(player);

        addEnemies(enemies);
        for (Enemy enemy : enemies) {
            enemy.addHealthBar(director);
        }

        addParcelVan(parcelVan);
        addSprites(staticSprites);
    }
}
