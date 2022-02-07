package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.screens.TileManager;
import com.mygdx.gigabiteconomy.sprites.GameObject;

/**
 * Class represents a sprite shown on screen, ready to be drawn with batch.draw(); in MainScreen class
 * MainScreen interfaces with this class through GameObject
 */
abstract class MySprite extends Actor implements GameObject {
    //Rectangle which holds the texture
    private Rectangle rect;

    TileManager tm;

    Tile currentTile;
    Tile targetTile;

    TextureAtlas ta;
    //Coordinates of sprite on screen
    Vector2 pos = new Vector2(0, 0);
    //Vector we add to position with every move
    Vector2 deltaMove = new Vector2(0, 0);
    //Array of regions in spritesheet
    private Array<TextureAtlas.AtlasRegion> regions;
    //Current image being displayed in the movement animation
    private TextureRegion current;

    boolean moving; //Can use for paused? Also useful for player (holding down keys)

    /**
     * Constructor used to create a new sprite
     * @param config Config file, name of .txt file generated by TexturePacker application
     * @param x position of Tile (within tile grid) to place sprite
     * @param y position of Tile (within tile grid) to place sprite
     */
    public MySprite(String config, int x, int y) {
        ta = new TextureAtlas(config);
        regions = ta.getRegions();

        current = regions.get(0); //Change to more general name to fit with all sprites
                                                              //See about exceptions and error handling here

        setBounds(x, y, current.getRegionWidth(), current.getRegionHeight());

        pos.x = x; pos.y=y;

        //Creating rectangle to cover texture
        rect = new Rectangle(x, y, current.getRegionWidth(), current.getRegionHeight()); //What happens to rectangle when texture changes size (e.g. in an animation)?

    }

    @Override
    public int initTile(TileManager tmPass) {
        if (tm != null) return -1; //Returning error code if tm is already init
        this.tm = tmPass;
        currentTile = tm.placeObject((int)pos.x, (int)pos.y, this); //At init tile coords[x] will be filled with tile coords on grid
        pos.x = currentTile.getTileCoords()[0]; pos.y = currentTile.getTileCoords()[1];
        System.out.println("Initialised at " + pos.x + " " + pos.y);
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
        return pos.x;
    }

    @Override
    public float getActorY() {
        return pos.y;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;

    }

    public boolean isMoving() {
        return moving;
    }

    public void setDeltaMove(float x, float y) {
        deltaMove.x = x; deltaMove.y = y;
    }

    /**
     * 'snaps' player sprite to current Tile centre
     */
    public boolean snap(float delta) {
        pos.x = currentTile.getTileCoords()[0];
        pos.y = currentTile.getTileCoords()[1];
        return true;
    }

    /**
     * Method runs on each GameObject if there is a targetSquare set to go to.
     * @param delta
     */
    public boolean move(float delta) {
        if (targetTile == null) {
            return false;
        }

        /**
         * If the distance from the centre is 5px, we snap and call it a day (Sprite has arrived at target)
         */
        if ((Math.abs(pos.x-targetTile.getTileCoords()[0])<5) && (Math.abs(pos.y-targetTile.getTileCoords()[1])<5)) {
            //Arrived at tile
            System.out.println("Arrived at tile");
            currentTile = targetTile;
            targetTile = null;
            snap(delta);
        } else {
            /**
             * Otherwise, keep adding deltaMove vector to positionVector
             */
            //Keep on moving
            pos.add(deltaMove);
            System.out.println("New pos: " + pos.x + " " + pos.y);
        }

        return true;
    }

}
