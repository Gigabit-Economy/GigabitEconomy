package com.mygdx.gigabiteconomy.scenes;

import com.mygdx.gigabiteconomy.Tile;

import java.util.ArrayList;

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
    public TileManager(int sideLength, int maxHeight, int maxWidth, float x, float y) {
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
                tileArray[i][ii] = new Tile((x+(sideLength*i)), (y+(sideLength*ii)), sideLength);
            }
        }

    }

    /**
     *
     * @return Always returns Tile[4] with unavailable spaces as null
     */
    public Tile[] getAdjecentTiles() {
        return null;
    }
}
