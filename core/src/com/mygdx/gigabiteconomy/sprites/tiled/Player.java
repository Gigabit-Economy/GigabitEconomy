package com.mygdx.gigabiteconomy.sprites.tiled;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.gigabiteconomy.exceptions.ParcelException;
import com.mygdx.gigabiteconomy.exceptions.TileMovementException;
import com.mygdx.gigabiteconomy.screens.LevelScreen;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.sprites.GameObject;
import com.mygdx.gigabiteconomy.sprites.House;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class representing a player sprite (one per level)
 */
public class Player extends MovingSprite {
    private static final Random RANDOM = new Random();

    private LevelScreen level;

    private Parcel parcel;

    /**
     * Create a new Player sprite (MovingSprite)
     *
     * @param weapon the weapon the Player is carrying
     * @param x position of Tile (within tile grid) to place sprite
     * @param y position of Tile (within tile grid) to place sprite
     * @param height of Tiles to occupy
     * @param width of Tiles to occupy
     */
    public Player(Weapon weapon, int x, int y, int height, int width) {
        super(weapon, x, y, height, width);
    }

    /**
     * Set the level the Player is in
     *
     * @param level the level the Player is in
     */
    public void setLevel(LevelScreen level) {
        this.level = level;
    }


    /**
     * Method to handle movement of the Player
     *
     * @param keycode the user inputted key
     */
    public void handleMovement(int keycode) {
        if (getTargetTiles() != null) {
            System.out.println("Not finished with previous movement");
            return; // Not finished with previous movement
        }

        /**
         * Sets direction enum which defines the velocity vector (which speed and
         * direction to move in with each move() call)
         */
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
            // Move left
            super.setDirectionMovement(DIRECTION.WEST);
        }
        else if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
            // Move right
            super.setDirectionMovement(DIRECTION.EAST);
        }
        else if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
            // Move up
            super.setDirectionMovement(DIRECTION.NORTH);
        }
        else if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            // Move down
            super.setDirectionMovement(DIRECTION.SOUTH);
        } else {
            System.out.println(keycode + " not accounted for in movement logic");
            return;
        }
        setMoving(true);
    }

    /**
     * Stop the player from moving (i.e. when a movement key is lifted)
     */
    public void stopMovement() {
        setMoving(false);
    }

    @Override
    public DIRECTION setNextDirection() {
        return getDirectionMoving();
    }

    /**
     * Defines specific movement for when we get to targetTile
     * @param delta
     * @return
     * @throws TileMovementException
     */
    @Override
    public boolean move(float delta) throws TileMovementException  {
        boolean ret = super.move(delta);
        if (!ret) return false;

        /**
         * Still holding down key, reset direction
         */
        if (isMoving()) {
            setDirectionMovement(getDirectionMoving());
            return false;
        }

        setDirectionMovement(null);
        setMoving(false);

        return true;
    }

    /**
     * Launch an attack using the sprite's weapon (using launchAttack() in MovingSprite) OR collect a parcel from the parcel van.
     */
    @Override
    public void launchAttack() {
        // get Tile adjacent to Player
        Tile adjacentTile = getTileManager().getAdjacentTile(getCurrentTiles().get(0), getDirectionFacing(), 1);
        if (adjacentTile == null) return; // trying to attack invalid Tile

        // if Player doesn't yet have a Parcel, check if next to a parcel van to collect one
        if (this.parcel == null) {
            // if adjacent tile is occupied by a parcel van, collect parcel
            TiledObject adjacentSprite = adjacentTile.getOccupiedBy();
            if (adjacentSprite instanceof ParcelVan) {
                this.parcel = new Parcel();
                return;
            }
        }
        // if Player does have a Parcel, check if next to House to be delivered to
        else {
            // if adjacent tile is owned by a House, deliver parcel
            GameObject adjacentObject = adjacentTile.getOwnedBy();
            if (adjacentObject != null && adjacentObject instanceof House) {
                parcel.deliver();
            }
        }

        super.launchAttack();
    }

    /**
     * Open the Player's Parcel to get the weapon
     *
     * @throws ParcelException if no parcel is being carried by the Player
     */
    public void openParcel() throws ParcelException {
        if (parcel == null) {
            throw new ParcelException("No parcel is being carried");
        }

        Weapon parcelWeapon = parcel.open();
        setWeapon(parcelWeapon);

        this.parcel = null;
    }

    /**
     * Destroy the player & end the current level.
     * Called when the player's health reaches 0 or less.
     */
    @Override
    public void destroy() {
        if (level != null) {
            level.end();
        }

        super.destroy();
    }

    private class Parcel {
        private MovingSprite.Weapon weapon;
        private House house;
        private boolean isFinalParcel;

        /**
         * Create a new Parcel to be carried by the Player
         */
        public Parcel() {
            // pick a random Weapon from the enum values to be weapon inside parcel (if it's opened)
            this.weapon = MovingSprite.Weapon.values()[RANDOM.nextInt(MovingSprite.Weapon.values().length)];

            // pick a random House from the level's houses to be house to be delivered to
            ArrayList<House> levelHouses = level.getHouses();
            this.house = levelHouses.get((RANDOM.nextInt(levelHouses.size())));
            try {
                this.house.markAsDeliveryLocation(level);
            } catch (Exception ex) {
                Gdx.app.error("Exception", "Error assigning House as delivery location", ex);
                System.exit(-1);
            }

            // if level is onto final parcel, mark as such
            this.isFinalParcel = (level.getParcels() == 1);

            level.decrementParcels();

            // if final parcel, switch van to van with no parcels
            if (isFinalParcel) {
                level.getParcelVan().setToEmpty();
            }
        }

        /**
         * Deliver the parcel in exchange for level points
         */
        public void deliver() {
            parcel = null;

            level.addToScore(1);

            house.unmarkAsDeliveryLocation(level);

            if (isFinalParcel) {
                level.complete();
            }
        }

        /**
         * Open the parcel to get the Weapon inside the parcel
         *
         * @return the Weapon inside the parcel
         */
        public Weapon open() throws ParcelException {
            if (isFinalParcel) {
                throw new ParcelException("The final parcel must be delivered and cannot be opened");
            }

            return weapon;
        }
    }
}
