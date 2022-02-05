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

    TextureAtlas ta;
    //Coordinates of sprite on screen
    //private float[] coords = new float[2];
    Vector2 pos = new Vector2(0, 0); //Coordinates of sprite on screen
    Vector2 deltaMove = new Vector2(0, 0); //To add to "Vector2 pos" with every move
    //Array of regions in spritesheet
    private Array<TextureAtlas.AtlasRegion> regions;
    //Current image being displayed in the movement animation
    private TextureRegion current;

    boolean moving; //Can use for paused? Also useful for player (holding down keys)
    MoveDirection direction;

    private int[] dcoords = new int[2]; //Increment of each x, y if moving is true

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

    @Override
    public void setActorX() {

    }

    @Override
    public void setActorY() {

    }
    @Override
    public Rectangle getRectangle() {
        return rect;
    }

    public void setDCoords(int dx, int dy) {
        dcoords[0] += dx; dcoords[1] += dy;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;

    }

    public boolean isMoving() {
        return moving;
    }

//    private int[] movingFrom() {
//
//        int[] ret = new int[2];
//        System.out.println("Calculating with direction: " + direction);
//        switch (direction) {
//            case LEFT: //[x, y] = [1, 0]
//                ret[0] = 1; ret[1] = 0;
//                break;
//            case RIGHT:
//                ret[0] = -1; ret[1] = 0;
//                break;
//            case UP:
//                ret[1] = 1; ret[0] = 0;
//                break;
//            case DOWN:
//                ret[1] = -1; ret[0] = 0;
//                break;
//        }
//        return ret;
//    }


    /**
     * Method runs if boolean moving set to true
     * @param delta
     */
    public boolean move(float delta) {
        if (!isMoving()) return false;

        pos.add(deltaMove);

        Tile toMove = tm.getTileFromCoords(pos.x, pos.y);
        if (toMove != null) currentTile = toMove;
        System.out.println("Player now on Tile: " + currentTile.getTileCoords()[0] + " " + currentTile.getTileCoords()[1]);

        return true;
    }

    public enum MoveDirection {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

}
