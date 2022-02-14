package com.mygdx.gigabiteconomy.sprites.tiled;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gigabiteconomy.exceptions.TileMovementException;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.gigabiteconomy.sprites.tiled.TiledObject;

import java.util.ArrayList;
import java.util.Locale;

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

    private DIRECTION directionMoving = DIRECTION.EAST;
    private boolean moving;
    // Vector we add to position with every move
    private Vector2 deltaMove = new Vector2(0, 0);
    private static float deltaVert = 3F;
    private static float deltaHoriz = 3.5F;
    //private Tile targetTile;
    private ArrayList<Tile> targetTiles;

    private MovingAnimation<TextureRegion> movementAnimation;
    private MovingAnimation<TextureRegion> attackAnimation;

    private int health = 100;
    private boolean attacking = false;
    private Weapon weapon;

    /**
     * Create a new moving sprite
     *
     * @param x position of Tile (within tile grid) to place sprite
     * @param y position of Tile (within tile grid) to place sprite
     */
    public MovingSprite(Weapon weapon, int x, int y) {
        super(x, y);

        setWeapon(weapon);
    }

    /**
     * Get the texture region of the sprite
     *
     * @return the sprite's TextureRegion instance
     */
    public TextureRegion getTextureRegion() {
        return textureRegion;
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
        deltaMove.x = dir.dx; deltaMove.y = dir.dy;
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

    public void setDeltaMove(float x, float y) {
        deltaMove.x = x;
        deltaMove.y = y;
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
        if (targetTiles.contains(0)) return;
        //Setting tile occupation in foreach
        for (Tile t : targetTiles) {
            t.setOccupied(this);
        }
    }

    public boolean onTargetTiles() {
        if (targetTiles.contains(null)) return false;
        boolean ret = false;
        for (Tile t : targetTiles) {
            ret |= t.withinTile(this);
        }
        return ret;
    }

    public abstract DIRECTION setNextDirection();

    /**
     * Class for setting next tiles, creates an ArrayList of next Tiles from currentTiles
     * @return
     */
    public ArrayList<Tile> setNextTiles() {
        //Setting next direction
        setNextDirection();

        ArrayList<Tile> toSet = new ArrayList<>();

        for (int i=0; i<getCurrentTiles().size(); i++) {
            Tile tileToAdd = getTileManager().getAdjecentTile(getCurrentTiles().get(i), getDirectionMoving().name(), 1);
            if (tileToAdd == null || (tileToAdd.getOccupiedBy() != null && tileToAdd.getOccupiedBy() != this)) {
                targetTiles = null;
                return null;
            }

            toSet.add(tileToAdd); //Add next tile
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
                setAttacking(false);
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
                return false;
            }
        }

        /**
         * Commence movement logic by checking if we've arrived at the targetTile
         */
        if (this.onTargetTiles()) {
            for (Tile t : getCurrentTiles()) {
                t.setOccupied(null);
            }
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
        NORTH (0, deltaVert),
        EAST (deltaHoriz, 0),
        SOUTH (0, -deltaVert),
        WEST (-deltaHoriz, 0);

        public final float dx;
        public final float dy;

        private DIRECTION(float dx, float dy) {
            this.dx = dx; this.dy = dy;
        }
    }

    /**
     * Get the direction the sprite is currently facing (i.e. move direction)
     *
     * @return direction enum of the sprite
     */
    public DIRECTION getDirectionMoving()
    {
        return directionMoving;
    }

    /**
     * Set the sprite's current weapon
     *
     * @param weapon the weapon the sprite is to carry
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;

        String direction;
        switch (directionMoving) {
            case WEST:
                direction = "Left";
            case EAST:
                direction = "Right";
            default:
                direction = "Right";
        }
        String selectedWeapon = weapon.name().toLowerCase();
        String movementConfig = String.format("finished_assets/player/movement/%s%s.txt", selectedWeapon, direction);
        String attackingConfig = String.format("finished_assets/player/attacks/%s%s.txt", selectedWeapon, direction);

        this.ta = new TextureAtlas(movementConfig);
        this.regions = ta.getRegions();
        this.textureRegion = regions.get(0);

        this.movementAnimation = new MovingAnimation<TextureRegion>(1/14f, regions, true);
        this.attackAnimation = new MovingAnimation<TextureRegion>(1/14f, new TextureAtlas(attackingConfig).getRegions(), false);
    }

    /**
     * Launch an attack using the sprite's weapon.
     * Will call attack() and detract from health of any surrounding sprite.
     */
    public void launchAttack() {
        Tile adjacentTile = getTileManager().getAdjacentTile(getCurrentTile(), directionMoving, 1);

        // if adjacent tile is occupied by sprite which can be attacked, attack
        TiledObject adjacentSprite = adjacentTile.getOccupiedBy();
        if (adjacentSprite instanceof MovingSprite) {
            ((MovingSprite) adjacentSprite).attack(weapon);
        }
    }

    /**
     * Attack the sprite; called when another sprite launches an attack within range of the sprite.
     * Deducts -5 * hit multiplier of used weapon from sprite's health.
     *
     * @param weapon the weapon the attacking sprite is currently carrying
     */
    public void attack(Weapon weapon) {
        // deduct -5 (base health detraction) multiplied by hit multiplier of the used weapon from sprite
        setHealth(health - (5 * weapon.hitMultiplier));
    }

    /**
     * Set the health of the sprite
     *
     * @param health the sprite's new health value
     */
    public void setHealth(int health) {
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
        KNIFE (1),
        GOLF (2),
        PIPE (3),
        BAR_BELL (4),
        KATANA (5);

        private int hitMultiplier;

        private Weapon(int hitMultiplier) {
            this.hitMultiplier = hitMultiplier;
        }
    }

    /**
     * Get the sprite's health
     *
     * @return the sprite's health (as a percentage i.e. out of 100)
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Destroy the sprite.
     * Called when the sprite's health reaches 0 or less.
     */
    public void destroy() {
        // dispose of the sprite from memory
        dispose();
    }

    /**
     * Remove the sprite's texture atlas from memory once the sprite is no longer needed
     */
    public void dispose() {
        ta.dispose();
    }
}
