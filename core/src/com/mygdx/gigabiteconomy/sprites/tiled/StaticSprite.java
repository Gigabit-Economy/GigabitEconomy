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
     * Create a new static sprite
     *
     * @param png the .png file of the sprite's texture
     * @param x position of Tile (within tile grid) to place sprite
     * @param y position of Tile (within tile grid) to place sprite
     * @param height the height of the sprite (in Tiles)
     * @param width the width of the sprite (in Tiles)
     */
    public StaticSprite(String png, int x, int y, int height, int width) {
        super(x, y, height, width);

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

    /**
     * Update the sprite's texture
     *
     * @param texture the sprite's new texture
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    /**
     * Remove the sprite's texture from memory once the sprite is no longer needed
     */
    public void dispose() {
        texture.dispose();
    }
}
