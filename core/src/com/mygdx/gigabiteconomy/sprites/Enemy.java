package com.mygdx.gigabiteconomy.sprites;

/**
 * Class to create a new enemy. Will have the ability to:
 * > Specify initial x, y
 * > Specify movement pattern (random about defined area or movement along radius of circle for the doggies)
 */
public class Enemy extends MySprite {

    public Enemy(String config, int x, int y) {
        super(config, x, y);
    }
}
