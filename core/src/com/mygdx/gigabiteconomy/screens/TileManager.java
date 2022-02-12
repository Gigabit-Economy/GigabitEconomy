package com.mygdx.gigabiteconomy.screens;

import com.mygdx.gigabiteconomy.sprites.tiled.TiledObject;

/**
 * Class to hold and manage Tiles.
 * - Each MySprite instance is passed the TileManager it belongs to and sets a Tile to occupy
 * - Then Player position can be retrieved from the Tile it's on (or the bottom leftmost if multiple -- Rat King)
 */
public class TileManager {

    private Tile[][] tileArray;
    private int sideLength;
    int initialX, initialY; //Where the Tiles begin (bottom left) - don't really need to worry about this since all ours start at 0,0 for the time being
    private int gridHeight; private int gridWidth;

    /**
     * Constructor to create a number of tiles for the game screen
     * @param sideLength Side length of each tile to create
     * @param maxHeight Height to fill with tiles (usually texture height)
     * @param maxWidth Width to fill with tiles (usually texture width)
     * @param x Position of first tile on screen (bottom left)
     * @param y Position of first tile on screen (bottom left)
     */
    public TileManager(int sideLength, int maxHeight, int maxWidth, int x, int y) {
        initialX=x; initialY=y;

        this.sideLength = sideLength;
        System.out.println("Side length: " + sideLength);
        //Basic checking
        if (!(maxHeight%sideLength!=0 || maxWidth%sideLength!=0)) {
            System.out.println(">>> WARNING: maxHeight/maxWidth must be divisible by sideLength <<<"); //Throw exception here
            System.out.println(">>>        : Whole number of tiles needed within given bounds!  <<<");
            System.out.println(">>>        : Change sideLength?                                 <<<");
        }
        gridHeight = maxHeight/sideLength;
        gridWidth = maxWidth/sideLength;
        tileArray = new Tile[gridWidth][gridHeight];

        for (int i=0; i<gridWidth; i++) {
            for (int ii=0; ii<gridHeight; ii++) {
                tileArray[i][ii] = new Tile((x+(sideLength*i)), (y+(sideLength*ii)), sideLength, i, ii);
                //System.out.println("Tile created at " + (i) + " " + (ii));
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
    public Tile getAdjecentTile(Tile tileFrom, String direction, int distance) {
        if (tileFrom == null) return null;
        direction = direction.toUpperCase();
        int[] pos = tileFrom.getPositionTile();
        Tile ret;
        try {
            switch (direction) {
                case "WEST":
                    ret = getTile(pos[0]-distance, pos[1]);
                    break;
                case "EAST":
                    ret = getTile(pos[0]+distance, pos[1]);
                    break;
                case "NORTH":
                    ret = getTile(pos[0], pos[1]+distance);
                    break;
                case "SOUTH":
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
    public Tile placeObject(Tile toTile, TiledObject objectToPlace) {
        if (toTile.getOccupiedBy() != null) return null;
        Tile objTile = objectToPlace.getCurrentTile();
        //Clearing old tile
        if (objTile != null) {
            objTile.setOccupied(null);
            objectToPlace.setCurrentTile(null);
        }
        toTile.setOccupied(objectToPlace);
        return toTile;
    }

    public Tile placeObject(int x, int y, TiledObject objectToPlace) {
        Tile toPlace;
        if ((toPlace = getTile(x, y)) != null) {
            toPlace = placeObject(toPlace, objectToPlace);
        }
        return toPlace;
    }

    public Tile placeObjectFromCoords(float x, float y, TiledObject objectToPlace) {
        Tile ret = this.getTileFromCoords(x, y);
        ret = this.placeObject(ret, objectToPlace);
        return ret;
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
    public Tile moveFromTile(Tile tileFrom, String direction, int distance) {
        direction = direction.toUpperCase();
        TiledObject occupier = tileFrom.getOccupiedBy();
        Tile nextTile = getAdjecentTile(tileFrom, direction, distance);

        if (nextTile != null) {
            placeObject(tileFrom, null);
            placeObject(nextTile, occupier);
        }
        return nextTile!=null ? nextTile : tileFrom;
    }

    /**
     * Method returns Tile placed over x, y
     * @param x coord within Tile to get
     * @param y coord within Tile to get
     * @return Tile which holds [x, y]
     */
    public Tile getTileFromCoords(float x, float y) {
        Tile ret = null;
        try {
            ret = tileArray[(int)x/sideLength][(int)y/sideLength];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Could not find at " + x + " " + y);
        }
        return ret;
    }



    /**
     * Method to move an entity by only one space
     */
    public Tile moveFromTile(Tile tileFrom, String direction) {
        return moveFromTile(tileFrom, direction, 1);
    }

    public int getSideLength() {
        return sideLength;
    }

    public int getWidth() { return gridWidth; }

    public int getHeight() { return gridHeight; }

    /**
     * Debugging func
     */
    public void printOccupiedTiles() {
        String occupied = " ";
        for (Tile[] tileX : tileArray) {
            for (Tile tile : tileX) {
                if (tile.getOccupiedBy() != null) {
                    occupied += "[" + tile.getTileCoords()[0] + "," + tile.getTileCoords()[1] + "] ";
                }
            }
        }
        System.out.println(occupied);
    }

}
