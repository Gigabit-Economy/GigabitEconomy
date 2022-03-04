package com.mygdx.gigabiteconomy.screens;

import com.badlogic.gdx.utils.Disposable;
import com.mygdx.gigabiteconomy.exceptions.TileException;
import com.mygdx.gigabiteconomy.sprites.tiled.*;
import com.mygdx.gigabiteconomy.sprites.tiled.enemies.Dog;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Class used to hold and manage all Tiles.
 * - Each MySprite instance is passed the TileManager it belongs to and sets a Tile to occupy
 * - Then Player position can be retrieved from the Tile it's on (or the bottom leftmost if multiple -- Rat King)
 */
public class TileManager implements Disposable {

    private Tile[][] tileArray;
    private int sideLength;
    int initialX, initialY; //Where the Tiles begin (bottom left) - don't really need to worry about this since all ours start at 0,0 for the time being
    private int gridHeight; private int gridWidth;

    private ArrayList<TiledObject>[] rowArray;

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
        gridHeight = maxHeight/sideLength;
        gridWidth = maxWidth/sideLength;
        tileArray = new Tile[gridWidth][gridHeight];

        for (int i=0; i<gridWidth; i++) {
            for (int ii=0; ii<gridHeight; ii++) {
                tileArray[i][ii] = new Tile((x+(sideLength*i)), (y+(sideLength*ii)), sideLength, i, ii);
            }
        }
        //Creating array to store objects in order
        rowArray = new ArrayList[gridHeight];
        for (int i=0; i<gridHeight; i++) {
            rowArray[i] = new ArrayList<>();
        }
    }

    public Tile getTile(int x, int y) {
        try {
            return tileArray[x][y]; //This will need to TileMovementException
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
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

        return ret;
    }

    /**
     * Method to place an object on a group of Tiles.
     * Modifies rowArray accordingly
     * @param to TiledObject to place
     * @param toPlace ArrayList of Tiles to place on
     * @return toPlace
     */
    public ArrayList<Tile> placeObject(TiledObject to, ArrayList<Tile> toPlace) {
        removeFromRows(to);

        int lowestRow = gridHeight;
        for (Tile t : toPlace) {
            int tileRow = t.getPositionTile()[1];
            if (lowestRow > tileRow) lowestRow = tileRow;
            t.setOccupied(to);
        }

        //Adding to correct row array
        if (to != null) rowArray[lowestRow].add(to);

        return toPlace;
    }

    /**
     * Returns ArrayList of unoccupied tiles between coordinates given
     * @param x bottom left coord of where to start
     * @param y bottom left coord of where to start
     * @param width of tile segment to return
     * @param height of tile segment to return
     * @return ArrayList of requested tiles
     */
    public ArrayList<Tile> getTiles(int x, int y, int width, int height) {
        ArrayList<Tile> toPlace = new ArrayList<>();

        for (int i=0; i<width; i++) {
            for (int ii=0; ii<height; ii++) {
                Tile toAdd = getTile(x+i, y+ii);
                if (!(toAdd != null ? toPlace.add(toAdd) : toPlace.add(null))) {
                    return null;
                }
            }
        }
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
        for (MovingSprite.DIRECTION direction : MovingSprite.DIRECTION.values()) {
            ArrayList<Tile> toSearch = getSelectiveDir(curr.getPositionTile()[0], curr.getPositionTile()[1], direction);
            if (toSearch.contains(next)) {
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

        Tile[] adjacentTiles = new Tile[4];
        adjacentTiles[0] = getAdjacentTile(tile, MovingSprite.DIRECTION.NORTH, 1);
        adjacentTiles[1] = getAdjacentTile(tile, MovingSprite.DIRECTION.EAST, 1);
        adjacentTiles[2] = getAdjacentTile(tile, MovingSprite.DIRECTION.SOUTH, 1);
        adjacentTiles[3] = getAdjacentTile(tile, MovingSprite.DIRECTION.WEST, 1);

        return adjacentTiles;
    }

    /**
     * Get adjacent tiles to a specific TiledObject
     * @param to TiledObject - can be multi-tiled
     * @return ArrayList of adjacent tiles
     */
    public ArrayList<Tile> getAdjacentTiles(TiledObject to) {
        ArrayList<Tile> ret = new ArrayList<>();
        ArrayList<Tile> tiles = to.getCurrentTiles();

        for (Tile tile : tiles) {
            Tile[] tempTiles = getAdjacentTiles(tile);
            for (Tile tileInTemp : tempTiles) {
                if (tileInTemp == null) continue;
                if (!tileInTemp.isOccupiedBy(to))
                    ret.add(tileInTemp);
            }
        }

        return ret;
    }

    /**
     * Initialise Sprites on the gameboard
     * @param objsArr ArrayLists of TiledObject to place
     */
    public void initObjects(ArrayList<? extends TiledObject>... objsArr) {
        for (ArrayList<? extends TiledObject> arr : objsArr) {
            for (TiledObject o : arr) {
                //Always coordinates of starting tile
                float spriteX = o.getX();
                float spriteY = o.getY();
                int spriteH = o.getHeight();
                int spriteW = o.getWidth();
                ArrayList<Tile> placeAt = this.getTiles((int) spriteX, (int) spriteY, spriteW, spriteH);

                o.setTileManager(this);
                o.setCurrentTiles(placeAt);
            }
        }
    }

    /**
     * Method to return whether 'to' is present in 'tiles'
     * @param to
     * @param tiles
     * @return True: Yes ; False: No
     */
    public boolean isGroupOccupiedBy(TiledObject to, ArrayList<Tile> tiles) {
        for (Tile t : tiles) {
            if (t == null) continue;
            if (t.isOccupiedBy(to)) return true;
        }
        return false;
    }

    /**
     * Method to get group of next tiles to move to
     * @param mo MovingSprite on which to act on
     * @param dirIn Direction to move MovingSprite in
     * @param distance How much are we moving by? (Usually 1)
     * @return ArrayList of tiles to move to
     */
    public ArrayList<Tile> getNextTiles(MovingSprite mo, MovingSprite.DIRECTION dirIn, int distance) {
        ArrayList<Tile> toSet = new ArrayList<>();

        ArrayList<Tile> toCurrTiles = mo.getCurrentTiles();
        for (int i=0; i<toCurrTiles.size(); i++) {
            Tile tileToAdd = getAdjacentTile(toCurrTiles.get(i), dirIn, distance);
            if (tileToAdd == null || (tileToAdd.getOccupiedBy() != mo && tileToAdd.getOccupiedBy() != null)) {
                toSet.add(null);
            }
            toSet.add(tileToAdd);
        }
        return toSet;
    }

    /**
     * Method to check whether passed MovingSprite is within the bounds of a group of Tiles
     * @param mo MovingSprite to check
     * @param toCheck ArrayList of Tiles to check bounds of
     * @return True: Yes, within bounds ; False: No
     */
    public boolean withinTileBounds(MovingSprite mo, ArrayList<Tile> toCheck) {
        if (toCheck.contains(null)) return false;
        //Only check for one tile
        return toCheck.get(0).withinTile(mo);
    }

    /**
     * @return width of tile grid
     */
    public int getWidth() { return gridWidth; }

    /**
     * @return height of tile grid
     */
    public int getHeight() { return gridHeight; }

    /**
     * Debugging func
     */
    public String printOccupiedTiles() {
        String occupied = " ";
        for (Tile[] tileX : tileArray) {
            for (Tile tile : tileX) {
                if (tile.getOccupiedBy() != null) {
                    if (tile.getOccupiedBy() instanceof Dog)
                        occupied += "[" + tile.getTileCoords()[0] + "," + tile.getTileCoords()[1] + "] ";
                }
            }
        }
        return occupied;
    }

    /**
     * Method to purge all instances of to from row
     */
    public void purge(int row, TiledObject to) {
        for (int i=0; i<gridWidth-1; i++) {
            Tile currTile = tileArray[i][row];
            if (currTile.isOccupiedBy(to))
                currTile.setOccupied(null);
        }
    }

    /**
     * Removes TiledObject passed from rows
     * @param to TiledObject to remove from rows
     */
    public boolean removeFromRows(TiledObject to) {
        boolean ret = false;
        for (ArrayList<TiledObject> tiledObjects : rowArray) {
            ret |= tiledObjects.remove(to);
        }

        return ret;
    }

    /**
     * Return a clone of the row array to edit (changing sprite layers)
     * @return Array of TiledObject ArrayLists defining which Sprites are on which rows
     */
    public ArrayList<TiledObject>[] getRowArray() {
        ArrayList<TiledObject>[] cloned = new ArrayList[rowArray.length];
        for (int i=0; i<rowArray.length; i++) {
            //Adding to cloned in reverse order so higher rows are drawn first
            cloned[i] = (ArrayList<TiledObject>)rowArray[rowArray.length-i-1].clone();
        }
        return cloned;
    }

    /**
     * Remove all sprites in the Tile Manager from memory.
     * To be called when the associated level ends/completes.
     */
    public void dispose () {
        for (ArrayList<TiledObject> sprites : getRowArray()) {
            for (TiledObject sprite : sprites) {
                sprite.dispose();
            }
        }
    }
}
