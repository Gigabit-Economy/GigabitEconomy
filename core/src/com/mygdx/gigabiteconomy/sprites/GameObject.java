package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    // Coordinates of sprite on screen
    private Vector2 pos;

    public GameObject(float x, float y) {
        pos.x = x;
        pos.y = y;
    }

    /**
     * Get the Vector2 position of the sprite
     *
     * @return the current X coordinate of the sprite
     */
    public float getX()
    {
        return pos.x;
    }

    /**
     * Get the Vector2 position of the sprite
     *
     * @return the current U coordinate of the sprite
     */
    public float getY()
    {
        return pos.y;
    }

    /**
     * Set the Vector2 position of the sprite
     *
     * @param x the new X coordinate
     * @param y the new Y coordinate
     */
    public void setPos(float x, float y)
    {
        pos.x = x;
        pos.y = y;
    }

    /**
     * Add to the sprite's current Vector2 position.
     *
     * @param deltaMove the Vector 2 coordinates to move by
     */
    public void addToPos(Vector2 deltaMove)
    {
        pos.add(deltaMove);
    }
    
    void setTileManager(TileManager tm);
}
