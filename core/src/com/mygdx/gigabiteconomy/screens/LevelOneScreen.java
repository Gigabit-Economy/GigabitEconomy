package com.mygdx.gigabiteconomy.screens;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.badlogic.gdx.graphics.Texture;
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
    // Level screen backgro1.5und texture
    private static final String BACKGROUND_TEXTURE_PNG = "finished_assets/levels/level1.png";

    // Player character
    private final Weapon playerWeapon = Weapon.KNIFE;
    private final Player player = new Player(playerWeapon, 0 , 0, 1, 1);

    /* ENEMIES */
    private final Enemy enemyOne = new BatGuy(25, 3, player);

    private final Enemy enemyTwo = new Dog(5, 1, player);

    private final ArrayList<Enemy> enemies = new ArrayList<Enemy>(Arrays.asList(enemyOne, enemyTwo));

    // Parcel van (for Player to collect parcels from)
    private final ParcelVan parcelVan = new ParcelVan(15, 3);

    /* STATIC SPRITES (HOUSES, FENCES ETC...) */
    private final House houseOne = new House(House.HouseType.DETACHED, 0);
    private final House houseTwo = new House(House.HouseType.TWO_STORY, 10);
    private final House houseThree = new House(House.HouseType.TWO_STORY, 20);
    private final House houseFour = new House(House.HouseType.DETACHED, 36);
    private final StaticSprite fence = new StaticSprite("finished_assets/static_sprites/fence.png", 5, 0, 1, 2);
    private final ArrayList<StaticSprite> staticSprites = new ArrayList<StaticSprite>(Arrays.asList(houseOne, houseTwo, houseThree, houseFour, fence));

    /**
     * Creates a new screen instance for Level 1 based off the LevelScreen abstract class, which contains all shared
     * methods/properties between levels and implements the LibGDX Screen interface.
     *
     * @param director the instance of the game director
     */
    public LevelOneScreen(GigabitEconomy director) {
        super(director, BACKGROUND_TEXTURE_PNG);

        addPlayer(player);
        addEnemies(enemies);
        addParcelVan(parcelVan);
        addSprites(staticSprites);
    }
}
