package com.mygdx.gigabiteconomy.screens;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.gigabiteconomy.sprites.GameObject;
import com.mygdx.gigabiteconomy.sprites.House;
import com.mygdx.gigabiteconomy.sprites.tiled.*;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite.Weapon;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Level 1 screen
 */
public class LevelOneScreen extends LevelScreen {
    // Player character
    private static final Weapon PLAYER_WEAPON = Weapon.KNIFE;
    private static final Player PLAYER = new Player(PLAYER_WEAPON, 0 , 0, 1, 1);

    /* ENEMIES */
    private static final Enemy ENEMY_ONE = new Enemy(Weapon.KATANA, 20, 3, 1, 1, PLAYER);
    private static final ArrayList<TiledObject> ENEMIES = new ArrayList<TiledObject>(Arrays.asList(ENEMY_ONE));

    /* HOUSES */
    private static final House HOUSE_ONE = new House(House.HouseType.DETACHED, 640, 480);
    private static final House HOUSE_TWO = new House(House.HouseType.TWO_STORY, 1050, 480);
    private static final ArrayList<House> HOUSES = new ArrayList<House>(Arrays.asList(HOUSE_ONE, HOUSE_TWO));

    // Parcel van (for Player to collect parcels from)
    private static final ParcelVan PARCEL_VAN = new ParcelVan(15, 3);

    /* STATIC SPRITES (FENCES ETC...) */
    private static final StaticSprite FENCE = new StaticSprite("finished_assets/static_sprites/fence.png", 5, 0, 1, 3);
    private static final ArrayList<TiledObject> STATIC_SPRITES = new ArrayList<TiledObject>(Arrays.asList(FENCE));

    // Level screen background texture
    private static final Texture BACKGROUND_TEXTURE = new Texture("finished_assets/levels/level1.png");

    /**
     * Creates a new screen instance for Level 1 based off the LevelScreen abstract class, which contains all shared
     * methods/properties between levels and implements the LibGDX Screen interface.
     *
     * @param director the instance of the game director
     */
    public LevelOneScreen(GigabitEconomy director) {
        super(director, PLAYER, ENEMIES, HOUSES, PARCEL_VAN, STATIC_SPRITES, BACKGROUND_TEXTURE);

        PLAYER.setLevel(this);
    }
}
