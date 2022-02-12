package com.mygdx.gigabiteconomy.sprites.tiled;

import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite;

/**
 * Class to create a new enemy. Will have the ability to:
 * > Specify initial x, y
 * > Specify movement pattern (random about defined area or movement along radius of circle for the doggies)
 */
public class Enemy extends MovingSprite {

    public Enemy(String move_config, String attack_config, int x, int y) {
        super(move_config, attack_config, x, y);
    }


}
