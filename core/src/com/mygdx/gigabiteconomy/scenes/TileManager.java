package com.mygdx.gigabiteconomy.scenes;

import com.mygdx.gigabiteconomy.scenes.Tile;
/**
 * Class to hold and manage Tiles.
 */
public class TileManager {

    Tile[][] tileArray;

    /**
     * Constructor to create a number of tiles for the game screen
     * @param sideLength Side length of each tile to create
     * @param maxHeight Height to fill with tiles (usually texture height)
     * @param maxWidth Width to fill with tiles (usually texture width)
     * @param x Position of first tile on screen (bottom left)
     * @param y Position of first tile on screen (bottom left)
     */
    public TileManager(int sideLength, int maxHeight, int maxWidth, int x, int y) {
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

    public Tile getTile(int x, int y) {
        if (x<tileArray.length && y<tileArray[0].length) {
            return tileArray[x][y];
        } else {
            return null;
        }
    }

    /**
     *
     * @return Always returns Tile[4] with unavailable spaces as null
     */
    public Tile[] getAdjecentTiles(Tile tile) {
        int[] pos = tile.getPositionTile();

        Tile[] adjecentTiles = new Tile[4];
        adjecentTiles[0] = getTile(pos[0], pos[1]+1);
        adjecentTiles[1] = getTile(pos[0]+1, pos[1]);
        adjecentTiles[2] = getTile(pos[0], pos[1]-1);
        adjecentTiles[3] = getTile(pos[0]-1, pos[1]);

        return adjecentTiles;
    }

}
