package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.screens.TileManager;
import com.badlogic.gdx.utils.Disposable;

/**
 * Class represents a sprite shown on screen, ready to be drawn with batch.draw(); in MainScreen class
 * MainScreen interfaces with this class through GameObject
 */
public abstract class MovingSprite extends Actor implements GameObject, Disposable {
    //Rectangle which holds the texture
    private Rectangle rect;

    TileManager tm;

    Tile currentTile;
    Tile targetTile;

    DIRECTION directionMoving;

    TextureAtlas ta;
    //Coordinates of sprite on screen
    Vector2 pos = new Vector2(0, 0);
    //Vector we add to position with every move
    Vector2 deltaMove = new Vector2(0, 0);
    //Array of regions in spritesheet
    private Array<TextureAtlas.AtlasRegion> regions;
    //Current image being displayed in the movement animation
    private TextureRegion current;

    private MovingAnimation<TextureRegion> movementAnimation;
    private MovingAnimation<TextureRegion> attackAnimation;
    private boolean attacking = false;



    private static float deltaVert = 3.5F;
    private static float deltaHoriz = 4F;



    boolean moving; //Can use for paused? Also useful for player (holding down keys)

    /**
     * Constructor used to create a new moving sprite
     *
     * @param move_config Move config file, name of .txt file generated by TexturePacker application
     * @param attack_config Attack config file, name of .txt file generated by TexturePacker application
     * @param x position of Tile (within tile grid) to place sprite
     * @param y position of Tile (within tile grid) to place sprite
     */
    public MovingSprite(String move_config, String attack_config, int x, int y) {
        ta = new TextureAtlas(move_config);
        regions = ta.getRegions();

        current = regions.get(0); //Change to more general name to fit with all sprites
                                                              //See about exceptions and error handling here

        setBounds(x, y, current.getRegionWidth(), current.getRegionHeight());

        pos.x = x; pos.y = y;

        movementAnimation = new MovingAnimation<TextureRegion>(1/14f, regions, true);
        attackAnimation = new MovingAnimation<TextureRegion>(1/14f, new TextureAtlas(attack_config).getRegions(), false);
        //Creating rectangle to cover texture
        rect = new Rectangle(x, y, current.getRegionWidth(), current.getRegionHeight()); //What happens to rectangle when texture changes size (e.g. in an animation)?
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(current, this.getActorX(), this.getActorY());
    }

    @Override
    public TextureRegion getCurrRegion() {
        return current;
    }

    @Override
    public float getActorX() {
        return pos.x;
    }

    @Override
    public float getActorY() {
        return pos.y;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;

    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
        System.out.println(attacking);
    }

    public boolean isMoving() {
        return moving;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setDeltaMove(DIRECTION dir) {
        deltaMove.x = dir.dx; deltaMove.y = dir.dy;
    }

    /**
     * 'snaps' player sprite to current Tile centre
     */
    public boolean snap(float delta) {
        pos.x = currentTile.getTileCoords()[0];
        pos.y = currentTile.getTileCoords()[1];
        return true;
    }

    /**
     * Method runs on each GameObject if there is a targetSquare set to go to.
     * @param delta
     * @return False: Movement has finished ; True: Movement is continuing
     */
    public boolean move(float delta) {
        if (attacking) {
            current = (TextureRegion) attackAnimation.runAnimation(delta);
            //System.out.println("Changed to" + current);
            if (attackAnimation.isFinished(delta)) {
                System.out.println("Finished attacking");
                setAttacking(false);
            }
        }
        if (targetTile == null || targetTile.getOccupiedBy() != null) {
            directionMoving = null;
            targetTile = null;
            setMoving(false);
            return false;
        }

        /**
         * If the distance from the centre is 5px, we snap and call it a day (Sprite has arrived at target)
         */
        if ((Math.abs(pos.x-targetTile.getTileCoords()[0])<5) && (Math.abs(pos.y-targetTile.getTileCoords()[1])<5)) {
            //Arrived at tile
            System.out.println("Arrived at tile");
            System.out.println("Occupied tiles:");
            tm.printOccupiedTiles(); //Debugging function

            //Reset currentTile to targetTile
            currentTile = tm.placeObject(targetTile, this);
            return true;
        } else {
            /**
             * Otherwise, keep adding deltaMove vector to positionVector
             */
            //Keep on moving
            pos.add(deltaMove);
            current = (TextureRegion) movementAnimation.runAnimation(delta);
            //System.out.println("Current changed to: " + currentTile);

            //System.out.println("New pos: " + pos.x + " " + pos.y);
            return false;
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
     * Remove the sprite's texture atlas from memory once the sprite is no longer needed.
     */
    public void dispose() {
        ta.dispose();
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile tile) {
        currentTile = tile;
        if (tile == null) return;
        pos.x = currentTile.getTileCoords()[0]; pos.y = currentTile.getTileCoords()[1];
        System.out.println("Setting postion to " + pos.x + " " + pos.y);
    }

    public void setTileManager(TileManager tm) {
        this.tm = tm;
    }
}
