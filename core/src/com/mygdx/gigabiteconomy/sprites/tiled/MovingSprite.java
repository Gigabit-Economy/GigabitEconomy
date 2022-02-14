package com.mygdx.gigabiteconomy.sprites.tiled;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gigabiteconomy.exceptions.TileMovementException;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.gigabiteconomy.sprites.tiled.TiledObject;

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
    private Tile targetTile;

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
        Tile currentTile = super.getCurrentTile();

        setPos(currentTile.getTileCoords()[0], currentTile.getTileCoords()[1]);
    }

    /**
     * Get the target tile for the sprite to move to
     *
     * @return the Tile instance of the target tile
     */
    public Tile getTargetTile()
    {
        return targetTile;
    }

    /**
     * Set the target tile for the sprite to move to
     *
     * @param targetTile the Tile instance of the target tile
     */
    public void setTargetTile(Tile targetTile) {
        this.targetTile = targetTile;
        if (targetTile == null) return;
        targetTile.setOccupied(this);
    }

    public boolean onTile(Tile toCheck) {
        if (targetTile==null) return false;
        return (Math.abs(getX()-targetTile.getTileCoords()[0])<5) && (Math.abs(getY()-targetTile.getTileCoords()[1])<5);
    }

    public abstract void moveStart();
    public abstract boolean moveBlocked();

    public abstract Tile getNextTile();

    /**
     * Move the sprite to the set targetTile
     *
     * @param delta
     */
    public boolean move(float delta) throws TileMovementException {
        /**
         * Checking if sprite should be attacking
         */
        if (attacking) {
            textureRegion = (TextureRegion) attackAnimation.runAnimation(delta);
            //System.out.println("Changed to" + current);
            if (attackAnimation.isFinished(delta)) {
                System.out.println("Finished attacking");
                setAttacking(false);
            }
        }

        if (directionMoving != null) { //First sign that we should be moving

            if (targetTile == null)
                targetTile = getNextTile(); //If we are still then get next tile

            ///** is */ moveBlocked();

            //if (targetTile == null) return false; //If we're blocked return false (more in depth check coming soon)
            if (targetTile == null || targetTile.getOccupiedBy() != this) {
                System.out.println("getNextTile() Returned null for some reason");
                targetTile = null;
                return false;
            }
            //Commence move
            if (this.onTile(targetTile)) { //Need to sort out blocking, will remove != null check


                //Reset currentTile to targetTile
                getCurrentTile().setOccupied(null);
                setCurrentTile(targetTile);
                snap(delta);
                targetTile = null;
                if (this instanceof Player) {
                    System.out.println("Arrived at tile");
                    System.out.println("Occupied tiles:");
                    getTileManager().printOccupiedTiles(); //Debugging function
                }
                //setDirectionMovement(null);
                return true;
            } else { //Not made it yet!
                //Keep on moving
                addToPos(deltaMove);
                textureRegion = (TextureRegion) movementAnimation.runAnimation(delta);
                //System.out.println("Current changed to: " + currentTile);
//                if (this instanceof Player)
//                    System.out.println("I want to move!!" + deltaMove.toString());
                //System.out.println("New pos: " + pos.x + " " + pos.y);
                return false;
            }
        } else {
            return false; //Not moving
        }
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
