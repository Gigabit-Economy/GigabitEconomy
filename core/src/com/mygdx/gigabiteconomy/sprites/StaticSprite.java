package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.screens.TileManager;

import java.lang.Exception;

public class StaticSprite extends Actor implements GameObject {
    private Texture texture;
    private TextureRegion textureRegion = new TextureRegion();
    // Rectangle which holds the texture
    private Rectangle rect;

    TileManager tm;
    Tile tile;

    // Coordinates of sprite on screen
    private float[] coords = new float[2];

    /**
     * Constructor used to create a new static sprite
     *
     * @param png the .png file of the sprite's texture
     * @param x position of Tile (within tile grid) to place sprite
     * @param y position of Tile (within tile grid) to place sprite
     */
    public StaticSprite(String png, int x, int y) {
        // Create texture & assign to TextureRegion
        texture = new Texture(png);
        textureRegion.setTexture(texture);

        setBounds(x, y, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());

        coords[0] = x; coords[1] = y;

        // Creating rectangle to cover texture
        rect = new Rectangle(x, y, textureRegion.getRegionWidth(), textureRegion.getRegionHeight()); //What happens to rectangle when texture changes size (e.g. in an animation)?
    }

    @Override
    public void initTile(TileManager tmPass) throws Exception {
        // Check if tile manager has already been initialised
        if (tm != null)
            throw new Exception("The tile manager has already been initialised");

        this.tm = tmPass;

        tile = tm.placeObject((int)coords[0], (int)coords[1], this); //At init tile coords[x] will be filled with tile coords on grid
        coords[0] = tile.getTileCoords()[0]; coords[1] = tile.getTileCoords()[1];
        System.out.println("Initialised at " + coords[0] + " " + coords[1]);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(textureRegion, this.getActorX(), this.getActorY());
    }

    @Override
    public TextureRegion getCurrRegion() {
        return textureRegion;
    }

    @Override
    public float getActorX() {
        return coords[0];
    }

    @Override
    public float getActorY() {
        return coords[1];
    }

    @Override
    public void setActorX() {}

    @Override
    public void setActorY() {}

    @Override
    public Rectangle getRectangle() {
        return rect;
    }
}
