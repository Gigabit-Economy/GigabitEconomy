package com.mygdx.gigabiteconomy.screens;

import com.mygdx.gigabiteconomy.exceptions.TileException;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.StaticSprite;
import com.mygdx.gigabiteconomy.sprites.tiled.TiledObject;

import java.util.ArrayList;

/**
 * Class used to hold and manage all Tiles.
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
        return tileArray[x][y]; //This will need to TileMovementException
    }

    /**
     * Get certain in a direction "distance" from given Tile, not strictly adjacent but doesn't matter :p
     *
     * @param tileFrom Tile from which to calculate distance from
     * @param direction Direction to retrieve the next tile in
     * @param distance Distance from Tile given to get new Tile
     * @return Tile satisfying conditions, or null if impossible
     */
    public Tile getAdjacentTile(Tile tileFrom, MovingSprite.DIRECTION direction, int distance) {
        if (tileFrom == null) return null;
        if (distance == 0) {
            return tileFrom;
        }

        int[] pos = tileFrom.getPositionTile();
        Tile ret;

        try {
            switch (direction) {
                case WEST:
                    ret = getTile(pos[0]-distance, pos[1]);
                    break;
                case EAST:
                    ret = getTile(pos[0]+distance, pos[1]);
                    break;
                case NORTH:
                    ret = getTile(pos[0], pos[1]+distance);
                    break;
                case SOUTH:
                    ret = getTile(pos[0], pos[1]-distance);
                    break;
                default:
                    return null;
            }
        } catch (ArrayIndexOutOfBoundsException e) { return getAdjacentTile(tileFrom, direction, distance-1); }
        return ret;
    }

    /**
     * Place given object on given single Tile
     * @param toTile Tile to place given object on
     * @param objectToPlace Object to place on given Tile
     */
    private Tile placeObject(Tile toTile, TiledObject objectToPlace) {
        if (toTile.getOccupiedBy() != null) return null;

        toTile.setOccupied(objectToPlace);
        return toTile;
    }

    /**
     * Returns ArrayList of unoccupied tiles between coordinates given
     * @param x bottom left coord of where to start
     * @param y bottom left coord of where to start
     * @param width of tile segment to return
     * @param height of tile segment to return
     * @param objectToPlace object to place, can be null
     * @return ArrayList of requested tiles
     */
    public ArrayList<Tile> placeObject(int x, int y, int width, int height, TiledObject objectToPlace) {
        ArrayList<Tile> toPlace = new ArrayList<>();

        for (int i=0; i<width; i++) {
            for (int ii=0; ii<height; ii++) {
                Tile toAdd = getTile(x+i, y+ii);
                if ((toAdd != null ? toPlace.add(toAdd) : toPlace.add(null))) {
                    placeObject(toAdd, objectToPlace);
                } else {
                    return null;
                }
            }
        }
        System.out.println(String.format("Length %d for h:%d and w:%d", toPlace.size(), height, width));
        return toPlace;
    }

    /**
     * @param x coordinate to find row of
     * @param y coordinate to find row of
     * @param direction which direction to return row of
     * @return
     */
    public ArrayList<Tile> getSelectiveDir(int x, int y, MovingSprite.DIRECTION direction) {
        ArrayList<Tile> ret = new ArrayList<>();


        try {
            for (int i=0; i<=gridWidth; i++) {
                Tile toAdd = tileArray[x][y];
                ret.add(toAdd);
                if (toAdd.getOccupiedBy() instanceof StaticSprite) {
                    return ret;
                }
                //System.out.println(String.format("%d %d", x, y));
                if (direction.dyMult == 0) x = (direction.dxMult < 0) ? x-1 : x+1;
                if (direction.dxMult == 0) y = (direction.dyMult < 0) ? y-1 : y+1;
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            return ret;
        }
        return null;
    }

    /**
     * @param curr Tile to search from
     * @param next Tile to search for
     * @return MovingSprite.DIRECTION of next from curr. E.g. Which direction is Player relative to Enemy?
     */
    public MovingSprite.DIRECTION findDirectionFrom(Tile curr, Tile next) {
        // For each direction, we need to find corresponding row or col, then see if that's contained

        //System.out.println(String.format("Initiating search from [%d, %d] to [%d, %d]", curr.getPositionTile()[0], curr.getPositionTile()[1], next.getPositionTile()[0], next.getPositionTile()[1]));

        for (MovingSprite.DIRECTION direction : MovingSprite.DIRECTION.values()) {
            //System.out.println("ON DIRECTION" + direction);
            ArrayList<Tile> toSearch = getSelectiveDir(curr.getPositionTile()[0], curr.getPositionTile()[1], direction);
            if (toSearch.contains(next)) {
                //System.out.println("In direction: " + direction);
                return direction;
            }
        }

        return null;
    }

    /**
     * Method to get list of adjacent tiles to given Tile
     *
     * @return Tile[4] of Tiles clockwise from given Tile starting with Northern-most
     */
    public Tile[] getAdjacentTiles(Tile tile) {
        int[] pos = tile.getPositionTile();

        Tile[] adjacentTiles = new Tile[4];
        adjacentTiles[0] = getAdjacentTile(tile, MovingSprite.DIRECTION.NORTH, 1);
        adjacentTiles[1] = getAdjacentTile(tile, MovingSprite.DIRECTION.EAST, 1);
        adjacentTiles[2] = getAdjacentTile(tile, MovingSprite.DIRECTION.SOUTH, 1);
        adjacentTiles[3] = getAdjacentTile(tile, MovingSprite.DIRECTION.WEST, 1);

        return adjacentTiles;
    }

    /**
     * Initialise Sprites on the gameboard
     * @param objsArr ArrayLists of TiledObject to place
     */
    public void initObjects(ArrayList<TiledObject>... objsArr) {
        for (ArrayList<TiledObject> arr : objsArr) {
            for (TiledObject o : arr) {
                float spriteX = o.getX();
                float spriteY = o.getY();
                int spriteH = o.getHeight();
                int spriteW = o.getWidth();
                ArrayList<Tile> placeAt = this.placeObject((int) spriteX, (int) spriteY, spriteW, spriteH, o);

                //System.out.println(pos.x + " " + pos.y);

                o.setCurrentTiles(placeAt); //Setting current tiles should be done within player
                o.setTileManager(this);
            }
        }
    }

    /**
     * Method to move an entity from one tile to another
     * @param tileFrom Tile from which to move the GameObject
     * @param direction New direction to place the GameObject
     * @param distance How far to move the GameObject
     */
    public Tile moveFromTile(Tile tileFrom, MovingSprite.DIRECTION direction, int distance) {
        TiledObject occupier = tileFrom.getOccupiedBy();
        Tile nextTile = getAdjacentTile(tileFrom, direction, distance);

        if (nextTile != null) {
            placeObject(tileFrom, null);
            placeObject(nextTile, occupier);
        }
        return nextTile!=null ? nextTile : tileFrom;
    }

    /**
     * Method returns Tile placed over x, y
     *
     * @param x coord within Tile to get
     * @param y coord within Tile to get
     * @return Tile which holds [x, y]
     */
    public Tile getTileFromCoords(float x, float y) throws TileException {
        try {
            return tileArray[(int)x/sideLength][(int)y/sideLength];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new TileException("Could not find a Tile at " + x + ", " + y);
        }
    }

    /**
     * Get the nearest Tile to passed x, y coordinates
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the nearest tile to x, y
     */
    public Tile getNearestTileToCoords(float x, float y) throws TileException {
        try {
            return getTileFromCoords(x, y);
        } catch (TileException ex) {
            if (gridWidth > (x / sideLength)) {
                x = gridWidth;
            }

            if (gridHeight > (y / sideLength)) {
                y = gridHeight;
            }

            return getTileFromCoords(x, y);
        }
    }

    /**
     * Method to move an entity by only one space
     */
    public Tile moveFromTile(Tile tileFrom, MovingSprite.DIRECTION direction) {
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
