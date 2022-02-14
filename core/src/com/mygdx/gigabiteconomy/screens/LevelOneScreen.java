package com.mygdx.gigabiteconomy.screens;

import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.gigabiteconomy.sprites.GameObject;
import com.mygdx.gigabiteconomy.sprites.House;
import com.mygdx.gigabiteconomy.sprites.tiled.Enemy;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;
import com.mygdx.gigabiteconomy.sprites.tiled.StaticSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.TiledObject;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite.Weapon;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Level 1 screen
 */
public class LevelOneScreen extends LevelScreen {
    // Player character
    private static final Weapon PLAYER_WEAPON = Weapon.KNIFE;
    private static final Player PLAYER = new Player(PLAYER_WEAPON, 0 , 0);

    /* ENEMIES */
    private static final Enemy ENEMY_ONE = new Enemy(Weapon.KATANA, 2, 2);
    private static final ArrayList<TiledObject> ENEMIES = new ArrayList<TiledObject>(Arrays.asList(ENEMY_ONE));

    /* HOUSES */
    private static final House HOUSE_ONE = new House(House.HouseType.DETACHED, 640, 480);
    private static final ArrayList<House> HOUSES = new ArrayList<House>(Arrays.asList(HOUSE_ONE));

    // Parcel van (for Player to collect parcels from)
    private static final StaticSprite PARCEL_VAN = new StaticSprite("finished_assets/static_sprites/van.png", 15, 3);

    /* STATIC SPRITES (FENCES ETC...) */
    private static final StaticSprite FENCE = new StaticSprite("finished_assets/static_sprites/fence.png", 5, 0);
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
