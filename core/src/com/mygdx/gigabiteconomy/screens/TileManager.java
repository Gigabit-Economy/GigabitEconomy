package com.mygdx.gigabiteconomy.screens;

import com.badlogic.gdx.Game;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.sprites.GameObject;

/**
 * Class to hold and manage Tiles.
 * - Each MySprite instance is passed the TileManager it belongs to and sets a Tile to occupy
 * - Then Player position can be retrieved from the Tile it's on (or the bottom leftmost if multiple -- Rat King)
 */
public class TileManager {

    private Tile[][] tileArray;
    private int sideLength;

    /**
     * Constructor to create a number of tiles for the game screen
     * @param sideLength Side length of each tile to create
     * @param maxHeight Height to fill with tiles (usually texture height)
     * @param maxWidth Width to fill with tiles (usually texture width)
     * @param x Position of first tile on screen (bottom left)
     * @param y Position of first tile on screen (bottom left)
     */
    public TileManager(int sideLength, int maxHeight, int maxWidth, int x, int y) {
        this.sideLength = sideLength;
        //Basic checking
        if (!(maxHeight%sideLength!=0 || maxWidth%sideLength!=0)) {
            System.out.println(">>> WARNING: maxHeight/maxWidth must be divisible by sideLength <<<"); //Throw exception here
            System.out.println(">>>        : Whole number of tiles needed within given bounds!  <<<");
            System.out.println(">>>        : Change sideLength?                                 <<<");
        }
        int tileMapY = maxHeight/sideLength;
        int tileMapX = maxWidth/sideLength;
        tileArray = new Tile[tileMapX][tileMapY];

        for (int i=0; i<tileMapX; i++) {
            for (int ii=0; ii<tileMapY; ii++) {
                tileArray[i][ii] = new Tile((x+(sideLength*i)), (y+(sideLength*ii)), sideLength, i, ii);
                System.out.println("Tile created at " + (x+(sideLength*i)) + " " + (y+(sideLength*ii)));
            }
        }

    }

    private Tile getTile(int x, int y) {
        try {
            return tileArray[x][y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Get certain in a direction "distance" from given Tile, not strictly adjacent but doesn't matter :p
     * @param tileFrom Tile from which to calculate distance from
     * @param direction Direction to retrieve the next tile in
     * @param distance Distance from Tile given to get new Tile
     * @return Tile satisfying conditions, or null if impossible
     */
    private Tile getAdjecentTile(Tile tileFrom, String direction, int distance) {
        if (tileFrom == null) return null;
        direction = direction.toUpperCase();
        int[] pos = tileFrom.getPositionTile();
        Tile ret;
        try {
            switch (direction) {
                case "LEFT":
                    ret = getTile(pos[0]-distance, pos[1]);
                    break;
                case "RIGHT":
                    ret = getTile(pos[0]+distance, pos[1]);
                    break;
                case "UP":
                    ret = getTile(pos[0], pos[1]+distance);
                    break;
                case "DOWN":
                    ret = getTile(pos[0], pos[1]-distance);
                    break;
                default:
                    System.out.println("Direction: " + direction + " not recognised");
                    return null;
            }
        } catch (ArrayIndexOutOfBoundsException e) { return null; }
        return ret;
    }

    /**
     * Place given object on given Tile
     * @param toTile Tile to place given object on
     * @param objectToPlace Object to place on given Tile
     */
    public void placeObject(Tile toTile, GameObject objectToPlace) {
        if (toTile.getOccupiedBy() != null) {
            toTile.setOccupied(objectToPlace);
        }
    }

    public Tile placeObject(int x, int y, GameObject objectToPlace) {
        Tile toPlace;
        if ((toPlace = getTile(x, y)) != null) {
            placeObject(toPlace, objectToPlace);
        }
        return toPlace;
    }

    /**
     * Method to get list of adjacent tiles to given Tile
     * Returns Tiles clockwise from given Tile starting with Northernmost
     * @return Always returns Tile[4] of adjacent Tiles
     */
    public Tile[] getAdjecentTiles(Tile tile) {
        int[] pos = tile.getPositionTile();

        Tile[] adjecentTiles = new Tile[4];
        adjecentTiles[0] = getAdjecentTile(tile, "UP", 1);
        adjecentTiles[1] = getAdjecentTile(tile, "RIGHT", 1);
        adjecentTiles[2] = getAdjecentTile(tile, "DOWN", 1);
        adjecentTiles[3] = getAdjecentTile(tile, "LEFT", 1);

        return adjecentTiles;
    }

    /**
     * Method to move an entity from one tile to another
     * @param tileFrom Tile from which to move the GameObject
     * @param direction New direction to place the GameObject
     * @param distance How far to move the GameObject
     */
    public void moveFromTile(Tile tileFrom, String direction, int distance) {
        direction = direction.toUpperCase();
        GameObject occupier = tileFrom.getOccupiedBy();
        Tile nextTile = getAdjecentTile(tileFrom, direction, distance);

        if (nextTile != null) {
            placeObject(tileFrom, null);
            placeObject(nextTile, occupier);
        }
    }

    /**
     * Method to move an entity by only one space
     */
    public void moveFromTile(Tile tileFrom, String direction) {
        moveFromTile(tileFrom, direction, 1);
    }

    /**
     * Method to return screen coordinates of given tile (bottom left)
     * @param tileOccupied Tile to return coordinates of
     * @return int[2] of form [screen coord x, screen coord y]
     */
    public int[] getTileCoords(Tile tileOccupied) {
        int[] pos = tileOccupied.getPositionTile().clone();
        pos[0] = pos[0]*sideLength; pos[1] = pos[1]*sideLength;
        return pos;
    }

}
