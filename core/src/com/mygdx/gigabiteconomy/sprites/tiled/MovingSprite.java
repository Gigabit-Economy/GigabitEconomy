package com.mygdx.gigabiteconomy.sprites.tiled;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.exceptions.TileMovementException;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.badlogic.gdx.utils.Disposable;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 * Class representing a sprite shown on screen, ready to be drawn with batch.draw(); in MainScreen class.
 * Sprite has ability to move tiles and launch attacks/be attacked.
 * MainScreen interfaces with this class through GameObject.
 */
public abstract class MovingSprite extends TiledObject implements Disposable {
    private TextureAtlas ta;
    // Array of regions in spritesheet
    private Array<TextureAtlas.AtlasRegion> regions;
    // Current image being displayed in the movement animation
    private TextureRegion textureRegion;

    private DIRECTION directionMoving = null;
    private DIRECTION directionFacing = DIRECTION.EAST;

    private boolean moving;
    // Velocity for this moving object
    private Vector2 velocity = new Vector2(0, 0);
    // Vector we add to position with every move
    private Vector2 deltaMove = new Vector2(0, 0);
    //private Tile targetTile;
    private ArrayList<Tile> targetTiles;

    private MovingAnimation<TextureRegion> movementAnimation;
    private MovingAnimation<TextureRegion> attackAnimation;

    private float health;
    private boolean attacking = false;
    private Weapon weapon;

    private final String basePath;

    public int textureOffset = 126;//Pixel offset of texture

    /**
     * Create a new moving sprite
     *
     * @param x position of Tile (within tile grid) to place sprite
     * @param y position of Tile (within tile grid) to place sprite
     */
    public MovingSprite(Weapon weapon, int x, int y, int height, int width, float deltaHoriz, float deltaVert, float health, String pathToMoveAndAttackMaps) {
        super(x, y, height, width);
        this.health = health;

        this.basePath = pathToMoveAndAttackMaps;

        velocity.x = deltaHoriz;
        velocity.y = deltaVert;

        System.out.println(String.format("Setting velocity to %f %f", deltaHoriz, deltaVert));

        setWeapon(weapon);
    }

    /**
     * Add a health bar to the sprite
     *
     * @param director the level's director class
     */
    public abstract void addHealthBar(GigabitEconomy director);

    /**
     * Get the texture region of the sprite
     *
     * @return the sprite's TextureRegion instance
     */
    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    @Override
    public void drawOn(SpriteBatch batch, float delta) {
        try {
            move(delta);
        } catch (TileMovementException ex) {
            System.out.println("Sprite was blocked");
        }

        float offsetX = ((TextureAtlas.AtlasRegion)textureRegion).offsetX;
        batch.draw(textureRegion, getX()-offsetX, getY());
    }

    /**
     * Update the sprite's texture regions (texture atlases being shown as the sprite)
     * based on its directionFacing & weapon.
     * Called when directionFacing or weapon is changed.
     */
    public void updateTextureRegions(DIRECTION directionFacing) {
        String spriteDirection;
        switch (directionFacing) {
            case NORTH: //direction at start should always be east
            case SOUTH:
                return;
            case WEST:
                spriteDirection = "Left";
                break;
            case EAST:
            default:
                spriteDirection = "Right";
        }

        String selectedWeapon = this.weapon.name().toLowerCase();
        String movementConfig = String.format("%s/movement/%s%s.txt", this.basePath, selectedWeapon, spriteDirection);
        String attackingConfig = String.format("%s/attacks/%s%s.txt", this.basePath, selectedWeapon, spriteDirection);

        this.ta = new TextureAtlas(movementConfig);
        this.regions = ta.getRegions();
        this.textureRegion = regions.get(0);

        this.movementAnimation = new MovingAnimation<TextureRegion>(1/14f, regions, true);
        this.attackAnimation = new MovingAnimation<TextureRegion>(1/14f, new TextureAtlas(attackingConfig).getRegions(), false);
    }

    /**
     * Set the direction the sprite is facing (and therefore moves in).
     *
     * @param dir the direction enum to move in
     */
    public void setDirectionMovement(MovingSprite.DIRECTION dir) {
        directionMoving = dir;
        if (directionMoving == null) {
            deltaMove.x = 0;
            deltaMove.y = 0;
            return;
        }
        if (dir != directionFacing) updateTextureRegions(dir);
        directionFacing = dir;

        deltaMove.x = velocity.x * dir.dxMult; deltaMove.y = velocity.y * dir.dyMult;
    }

    /**
     * Get if the sprite is currently moving or not.
     *
     * @return if the sprite is moving (true => moving; false => not moving)
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     * Set if the sprite is currently moving or not.
     *
     * @param moving if the sprite is moving (true => moving; false => not moving)
     */
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setDeltaX(float dx) {
        deltaMove.x = dx * getDirectionFacing().dxMult;
    }

    public void setDeltaY(float dy) {
        deltaMove.x = dy * getDirectionFacing().dyMult;
    }

    /**
     * 'snaps' sprite to current Tile centre
     *
     * @param delta the delta time of the current render
     */
    public void snap(float delta) {
        Tile currentTile = super.getCurrentTiles().get(0);

        setPos(currentTile.getTileCoords()[0], currentTile.getTileCoords()[1]);
    }

    /**
     * Get the target tile for the sprite to move to
     *
     * @return the Tile instance of the target tile
     */
    public ArrayList<Tile> getTargetTiles()
    {
        return targetTiles;
    }

    /**
     * Set the target tile for the sprite to move to
     *
     * @param targetTiles the new Tiles to occupy
     */
    public void setTargetTiles(ArrayList<Tile> targetTiles) {
        this.targetTiles = targetTiles;
        if (targetTiles == null) return;
        // Not sure what this line is doing
        // If this method breaks, we know why haha
        // if (targetTiles.contains(0)) return;

        getTileManager().placeObject(this, targetTiles);
    }


    /**
     * Class for setting next tiles, creates an ArrayList of next Tiles from currentTiles
     * @return ArrayList of next tiles
     */
    public ArrayList<Tile> setNextTiles() {
        //Setting next direction
        //updateTextureRegions();

        ArrayList<Tile> toSet = getTileManager().getNextTiles(this, getDirectionMoving(), 1);
        if (toSet.contains(null)) {
            targetTiles = null;
            return null;
        }

        setTargetTiles(toSet); //Set target tile to one we want to go to
        return toSet;
    }

    /**
     * Method to handle general MovingSprite movement
     * Attacks if boolean set and moves the sprite in direction of directionMoving
     * Implementation notes:
     *     -> setNextDirection() abstract method defines path of MovingSprite
     *     -> Can be extended by overriding and calling super on this method
     *     -> When a sprite is moving, two Tiles are occupied. Moving to and moving from.
     * @param delta
     * @return True: Arrived at next tile ; False: Currently moving
     */
    public boolean move(float delta) throws TileMovementException {
        /**
         * Checking if sprite should be attacking
         */
        if (attacking) {
            //Set current texture to next animation texture
            textureRegion = (TextureRegion) attackAnimation.runAnimation(delta);
            //Checking if animation finished
            if (attackAnimation.isFinished(delta)) {
                System.out.println("Finished attacking");
                launchAttack();
                setAttacking(false);
            } else {
                return false;
            }
        }

        //Not moving - can just return
        if (directionMoving == null) return false;

        //Target tile is null when movement restarts (targetTile has been reached), we must get new Tile
        if (targetTiles == null) {
            //If getting new tile results in null or new tile is occupied by something other than this, we are blocked
            if (setNextTiles() == null /**|| targetTile.getOccupiedBy() != this */) { //If we are still then get next tile
                //Making sure targetTile contains null will cause this loop to run again, checking if we are still blocked
                targetTiles = null;
                return true;
            }
        }

        /**
         * Commence movement logic by checking if we've arrived at the targetTile
         */
        if (getTileManager().withinTileBounds(this, getTargetTiles())) {
            setCurrentTiles(targetTiles);
            snap(delta);
            targetTiles = null;
            return true;
        }

        //Not made it yet!
        //Keep on moving
        addToPos(deltaMove);
        textureRegion = (TextureRegion) movementAnimation.runAnimation(delta);
        return false;
    }

    /**
     * Enum which maps direction to velocity vector with instance variables defined above
     */
    public enum DIRECTION {
        NORTH (0, 1),
        EAST (1, 0),
        SOUTH (0, -1),
        WEST (-1, 0);

        public final int dxMult;
        public final int dyMult;


        private DIRECTION(int dxMult, int dyMult) {
            this.dxMult = dxMult; this.dyMult = dyMult;
        }

        /**
         * For getting opposite direction of enum. Used for reversing path of entity.
         * e.g. NORTH.getOpposite() returns SOUTH
         *
         * @return Opposite of enum called on
         */
        public DIRECTION getOpposite() {
            //return this.dx == 0 ? DIRECTION.values()[this.ordinal()+2] : DIRECTION.values()[this.ordinal()-2];
            switch (this) {
                case NORTH:
                    return SOUTH;
                case SOUTH:
                    return NORTH;
                case EAST:
                    return WEST;
                case WEST:
                default:
                    return EAST;
            }
        }

        public DIRECTION getNext() {
            return DIRECTION.values()[(this.ordinal()+1) % DIRECTION.values().length];
        }

        public static LinkedList<DIRECTION> randomPath(int length) {
            LinkedList<DIRECTION> ret = new LinkedList<>();
            Random rand = new Random();

            for (int i=0; i<length; i++) {
                ret.add(DIRECTION.values()[rand.nextInt(4)]);
            }

            return ret;
        }
    }


    /**
     * Get the direction the sprite is currently moving
     *
     * @return direction enum of the sprite
     */
    public DIRECTION getDirectionMoving()
    {
        return directionMoving;
    }

    /**
     * Get the direction the sprite is currently facing
     *
     * @return direction enum of the sprite
     */
    public DIRECTION getDirectionFacing()
    {
        return directionFacing;
    }

    /**
     * Set the sprite's current weapon
     *
     * @param weapon the weapon the sprite is to carry
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;

        updateTextureRegions(directionFacing);
    }

    /**
     * Launch an attack using the sprite's weapon.
     * Will call attack() and detract from health of any surrounding sprite.
     */
    public void launchAttack() {
        setAttacking(true);
        
        for (int i=getWidth(); i>0; i--) {
            ArrayList<Tile> tempAdjTiles = getTileManager().getAdjacentTiles(this);

            for (Tile t : tempAdjTiles) {
                TiledObject adjacentSprite = t.getOccupiedBy();
                if (adjacentSprite instanceof MovingSprite && adjacentSprite != this) {
                    if (this instanceof Enemy) {
                        System.out.println(String.format("Trying to attack %d %d", t.getPositionTile()[0], t.getPositionTile()[1]));
                    }
                    ((MovingSprite) adjacentSprite).attack(weapon);
                }
            }

        }
    }

    /**
     * Attack the sprite; called when another sprite launches an attack within range of the sprite.
     * Deducts -5 * hit multiplier of used weapon from sprite's health.
     *
     * @param weapon the weapon the attacking sprite is currently carrying
     */
    public abstract void attack(Weapon weapon);

    /**
     * Set the health of the sprite
     *
     * @param health the sprite's new health value
     */
    public void setHealth(float health) {
        this.health = health;

        if (health <= 0) {
            destroy();
        }
    }

    /**
     * Get if the sprite is attacking
     *
     * @return if attacking (true => attacking; false => not attacking)
     */
    public boolean isAttacking() {
        return attacking;
    }

    /**
     * Set if the sprite is attacking
     *
     * @param attacking if attacking (true => attacking; false => not attacking)
     */
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
        System.out.println(String.format("Attacking: %s", attacking));
    }

    public enum Weapon {
        KNIFE (2f),
        GOLF (3f),
        PIPE (4f),
        KATANA (5f),
        //Enemies
        NONE (0.5f),
        DOG (1.25f),
        BAT (1.5f),
        RATKING(8f);


        private float hitMultiplier;

        private Weapon(float hitMultiplier) {
            this.hitMultiplier = hitMultiplier;
        }

        public float getHitMultiplier() {
            return hitMultiplier;
        }
    }

    /**
     * Get the sprite's health
     *
     * @return the sprite's health (as a percentage i.e. out of 100)
     */
    public float getHealth() {
        return this.health;
    }

    /**
     * Destroy the sprite.
     * Called when the sprite's health reaches 0 or less.
     */
    public abstract void destroy();

    /**
     * Remove the sprite's texture atlas from memory once the sprite is no longer needed
     */
    public void dispose() {
        ta.dispose();
    }
}
