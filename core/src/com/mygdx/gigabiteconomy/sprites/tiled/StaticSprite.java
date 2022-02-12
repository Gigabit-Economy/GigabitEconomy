package com.mygdx.gigabiteconomy.sprites.tiled;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.gigabiteconomy.sprites.tiled.TiledObject;

/**
 * Class representing a sprite shown on screen, ready to be drawn with batch.draw(); in MainScreen class.
 * Sprite cannot move from the tile it's first placed on.
 * MainScreen interfaces with this class through GameObject.
 */
public class StaticSprite extends TiledObject {
    private Texture texture;

    /**
     * Constructor used to create a new static sprite
     *
     * @param png the .png file of the sprite's texture
     * @param x position of Tile (within tile grid) to place sprite
     * @param y position of Tile (within tile grid) to place sprite
     * @param tiled if the sprite is placed using tiled (if false, placed using screen coordinates)
     */
    public StaticSprite(String png, int x, int y) {
        super(x, y);

        // Create texture & assign to TextureRegion
        texture = new Texture(png);
    }

    /**
     * Get the texture of the sprite
     *
     * @return the Texture instance of the sprite's png texture
     */
    public Texture getTexture()
    {
        return texture;
    }
}