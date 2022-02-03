package com.mygdx.gigabiteconomy.sprites;

/**
 * Class to create a new enemy. Will have the ability to:
 * > Specify initial x, y
 * > Specify movement pattern (random about defined area or movement along radius of circle for the doggies)
 */
public class House extends MySprite {

    public House(String config, int x, int y) {
        super(config, x, y);
        super.move();
    }

    public void updateTexture() {
        super.move();
    }

    
}