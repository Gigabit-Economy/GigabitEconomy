package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
    Tile currentTile;

    // Coordinates of sprite on screen
    private Vector2 pos = new Vector2(0, 0);

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

        pos.x = x; pos.y = y;

        // Creating rectangle to cover texture
        rect = new Rectangle(x, y, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }

    @Override
    public void initTile(TileManager tmPass) throws Exception {
        // Check if tile manager has already been initialised
        if (tm != null)
            throw new Exception("The tile manager has already been initialised");

        this.tm = tmPass;

        System.out.println("Initialising StaticSprite at " + pos.x + " " + pos.y);
        currentTile = tm.placeObject((int)pos.x, (int)pos.y, this);

        if (currentTile == null) {
            throw new Exception("THERE IS ALREADY A SPRITE IN THIS LOCATION");
        }

        System.out.println("Current tile coords: " + currentTile.getTileCoords()[0] + " " + currentTile.getTileCoords()[1]);

        pos.x = currentTile.getTileCoords()[0]; pos.y = currentTile.getTileCoords()[1];

        System.out.println("Initialised at " + pos.x + " " + pos.y);
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
        return pos.x;
    }

    @Override
    public float getActorY() {
        return pos.y;
    }

    @Override
    public Tile getCurrentTile() {
        return null;
    }

    @Override
    public void setCurrentTile(Tile tile) {

    }
}
