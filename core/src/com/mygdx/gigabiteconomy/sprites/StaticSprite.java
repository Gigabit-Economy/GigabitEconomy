package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.screens.TileManager;

public class StaticSprite extends Actor implements GameObject {
    private Texture texture;

    // Rectangle which holds the texture
    private Rectangle rect;

    TileManager tm;
    Tile currentTile;

    // Coordinates of sprite on screen
    private float[] coords = new float[2];
    // Current image being displayed in the movement animation
    private TextureRegion current;

    /**
     * Constructor used to create a new static sprite
     *
     * @param png the .png file of the sprite's texture
     * @param x position of Tile (within tile grid) to place sprite
     * @param y position of Tile (within tile grid) to place sprite
     */
    public StaticSprite(String png, int x, int y) {
        // Create texture
        texture = new Texture(png);

        setBounds(x, y, current.getRegionWidth(), current.getRegionHeight());

        coords[0] = x; coords[1] = y;

        // Creating rectangle to cover texture
        rect = new Rectangle(x, y, current.getRegionWidth(), current.getRegionHeight()); //What happens to rectangle when texture changes size (e.g. in an animation)?
    }

    @Override
    public int initTile(TileManager tmPass) {
        if (tm != null) return -1; //Returning error code if tm is already init
        this.tm = tmPass;
        currentTile = tm.placeObject((int)coords[0], (int)coords[1], this); //At init tile coords[x] will be filled with tile coords on grid
        coords[0] = currentTile.getTileCoords()[0]; coords[1] = currentTile.getTileCoords()[1];
        System.out.println("Initialised at " + coords[0] + " " + coords[1]);
        //Success
        return 0;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(current, this.getActorX(), this.getActorY());
    }

    @Override
    public TextureRegion getCurrRegion() {
        return current;
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
