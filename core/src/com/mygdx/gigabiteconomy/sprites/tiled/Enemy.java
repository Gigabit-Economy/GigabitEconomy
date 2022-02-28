package com.mygdx.gigabiteconomy.sprites.tiled;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.exceptions.TileMovementException;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.screens.TileManager;
import com.mygdx.gigabiteconomy.sprites.IHealthBar;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.*;

/**
 * Class representing an enemy sprite (many per level)
 */
public abstract class Enemy extends MovingSprite {

    private EnemyHealthBar healthBar;

    private Queue<DIRECTION> movePath;
    private Queue<DIRECTION> agroMovePath;
    private Tile pathBegin; //Bottom leftmost tile

    private HashMap<String, Queue<DIRECTION>> movementPaths = new HashMap<>(); //Allows n paths for n behaviours
    private Queue<DIRECTION> currentPath;

    ArrayList<Tile> agroTiles; //Fixed tile coords in following format: [ [curr-x, curr+x] , [curr+y, curr-y] ]
    int[] agroTilePos;
    int horizAgroTiles = 5;
    int vertAgroTiles = 5;

    int[] agroTileVals = {5, 5, 5, 5}; // [horizAgroTiles, vertAgroTiles]

    boolean agro = false;

    private TiledObject targetEntity;

    /**
     * Create a new Enemy sprite (MovingSprite)
     *
     * @param BASE_PATH of texture
     * @param weapon the weapon the Enemy is carrying
     * @param x position of Tile (within tile grid) to place sprite
     * @param y position of Tile (within tile grid) to place sprite
     * @param height of Tiles to occupy
     * @param width of Tiles to occupy
     * @param deltaHoriz horizontal speed
     * @param deltaVert vertical speed
     * @param horizAgroTiles number of horizontal tiles either side of starting position to protect
     * @param vertAgroTiles number of vertical tiles either size of starting position to protect
     */
    public Enemy(String BASE_PATH, Weapon weapon, int x, int y, int height, int width, Player targetEntity, float deltaHoriz, float deltaVert, float health, LinkedList<DIRECTION> movePath) {
        super(weapon, x, y, height, width, deltaHoriz, deltaVert, health, BASE_PATH);


        this.movePath = movePath;

        agroMovePath = new LinkedList<>();
        for (int i=0; i<5; i++) agroMovePath.add(DIRECTION.NORTH);
        for (int i=0; i<5; i++) agroMovePath.add(DIRECTION.SOUTH);

        movementPaths.put("move", movePath);
        movementPaths.put("agro", agroMovePath);

        setPath("move");

        this.targetEntity = targetEntity;

        setMoving(true);


    }

    @Override
    public void drawOn(SpriteBatch batch, float delta) {
        super.drawOn(batch, delta);
        healthBar.drawOn(batch);
    }

    public void setPath(Queue<DIRECTION> pathList) {
        currentPath = pathList;
        setDirectionMovement(currentPath.peek());
    }

    /**
     * Method to set path of enemy from hashmap of defined paths
     * @param pathID Hashmap ID of path
     */
    public void setPath(String pathID) {
        if ((currentPath = movementPaths.get(pathID)) == null) {
            new Exception("Movement path does not exist");
        }
        setDirectionMovement(currentPath.peek());
    }

    @Override
    public void attack(Weapon weapon) {
        super.attack(weapon);
        healthBar.modifyHealth(5*weapon.getHitMultiplier());
    }

    /**
     * Class that manages the display of enemy health
     */
    public class EnemyHealthBar implements IHealthBar {
        private float INIT_WIDTH = 69;
        private float INIT_HEIGHT = 7;


        private ShapeRenderer healthEllipse;
        private float[] dimensions = new float[2];

        private Vector3 pos;

        private OrthographicCamera cam;

        //Custom health bar for bigger enemies
        public EnemyHealthBar(GigabitEconomy director, int width, int height) {
            healthBar = this;
            cam = director.getCamera(); //Need to use .project for drawing shape
            INIT_HEIGHT = dimensions[0] = width; INIT_WIDTH = dimensions[1] = height;
            healthEllipse = new ShapeRenderer();
        }

        public EnemyHealthBar(GigabitEconomy director) {
            healthBar = this;
            cam = director.getCamera();
            dimensions[0] = INIT_WIDTH; dimensions[1] = INIT_HEIGHT;
            healthEllipse = new ShapeRenderer();
        }

        @Override
        public void drawOn(SpriteBatch batch) {
            batch.end();

            healthEllipse.begin(ShapeRenderer.ShapeType.Filled);
            healthEllipse.setColor(Color.RED);
            //Ellipse is always centred over middle of texture
            pos = cam.project(new Vector3(
                    /* Mess around with these to centre health bar over enemy */

                    getX()+(((TextureAtlas.AtlasRegion)getTextureRegion()).offsetX-INIT_WIDTH)/2,

                    getY()+(getTextureRegion().getRegionHeight())-dimensions[1],
                    0
                    ));
            healthEllipse.ellipse(pos.x, pos.y, dimensions[0], dimensions[1]);
            healthEllipse.end();

            batch.begin();
        }

        @Override
        public void modifyHealth(float dhealth) {
            if ((dimensions[0] -= (dhealth*(INIT_WIDTH/100))) <= 0) dimensions[0] = 0;
            System.out.println("Width now: " + dimensions[0]);
        }
    }

    /**
     * Method takes Player and sets agro if within the defined distance
     * @param
     * @return
     */
    public boolean checkAgro() {
        if (agro) return false;

        if (agroTiles == null) {
            agroTiles = new ArrayList<>(4);
            agroTilePos = new int[4];

            TileManager tm = getTileManager();
            Tile currTile = getCurrentTiles().get(0);

            for (int i=0; i<4; i++) {
                //Add potential value
                Tile toAdd;

                while((toAdd = tm.getAdjacentTile(currTile, DIRECTION.values()[i], agroTileVals[i])) == null && agroTileVals[i] > 0) {
                    agroTileVals[i]--;
                }
                if (agroTileVals[i] <= 0 || toAdd == null) {
                    toAdd = currTile;
                }

                agroTiles.add(toAdd);

                agroTilePos[i] = toAdd.getPositionTile()[(i+1)%2]; //Seq: 1, 0, 1, 0 == y, x, y, x == N, E, S, W
            }

        }


        Tile currPlayerTile = targetEntity.getCurrentTiles().get(0);



        agro=true;
        //agroTilePos = [ N < , E < , S > , W > ]
        for (int i=0; i<agroTilePos.length; i++) {
            if (i<2) { //On N || E ; i==0 || i==1
                agro &= currPlayerTile.getPositionTile()[(i + 1) % 2] < agroTilePos[i];
            } else {// S || W ; i==2 || i==3
                agro &= currPlayerTile.getPositionTile()[(i + 1) % 2] > agroTilePos[i];
            }
        }


        return agro;
    }

    @Override
    public boolean move(float delta) throws TileMovementException {
        boolean ret = super.move(delta); //Checks if we've arrived else moved

        if (ret) setMoving(false);

        /**
         * >>> ATTACKING LOGIC <<<
         * If ret == false (couldn't move to requested Tile) && any adjecent tile is owned by player
         * -> We're next to player
         * -> Set attacking mode
         * -> Call inflictDamage(float val); on player
         */

        /**
         * >>> AGRO LOGIC <<<
         * If agro is true
         * -> Find new path with TileManager: Queue<Tile> findShortestPathBetween(Tile from, Tile to);
         * -> Set new targetSquare from Queue (for next move();
         */

        //Only run following code if we are still
        if (ret) {
            if (checkAgro()) {
                System.out.println("Agro set to true");
                setPath("agro");
            }
            super.setDirectionMovement(currentPath.remove());
            currentPath.add(getDirectionMoving());
            if (agro) {
                TileManager tm = getTileManager();
                //Check if player is on adjacent tiles

                if (getTileManager().isGroupOccupiedBy(targetEntity, new ArrayList<>(Arrays.asList(tm.getAdjacentTiles(this.getCurrentTiles().get(0)))))) {
                    //super.launchAttack();
                    setAttacking(true);

                }

                //Check if player is on the row
                //Move in that direction

                DIRECTION dirTo = tm.findDirectionFrom(getCurrentTiles().get(0), targetEntity.getCurrentTiles().get(0));

                if (dirTo != null) {
                    setPath(new LinkedList<>(Arrays.asList(dirTo, dirTo)));
                } else {
                    setPath("agro");
                }
            }

        }



        /**
         * If above is implemented right, enemy should be able to move in 'direction' towards 'targetTile'
         * with each move() call
         */


        return ret;
    }

    /**
     * Destroy the Enemy & remove from the level.
     * Called when the Enemy's health reaches 0 or less.
     */
    @Override
    public void destroy() {
        // remove the sprite from the level and clear its tile
        getTileManager().placeObject(null, getCurrentTiles());
        getTileManager().removeFromRows(this);

        // dispose of the sprite from memory
        dispose();
    }
}
