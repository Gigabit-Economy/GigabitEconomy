package com.mygdx.gigabiteconomy.sprites.tiled;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.exceptions.ParcelException;
import com.mygdx.gigabiteconomy.exceptions.TileMovementException;
import com.mygdx.gigabiteconomy.screens.LevelScreen;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.sprites.HealthBar;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class representing a player sprite (one per level)
 */
public class Player extends MovingSprite {
    private static final Random RANDOM = new Random();

    private LevelScreen level;
    private Parcel parcel;

    private static final String BASE_PATH = "player";

    private static final int BASE_HEALTH_DETRACTION = 5;
    private PlayerHealthBar healthBar;

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
        super(weapon, x, y, height, width, 3.5f, 3f, 100f, BASE_PATH);
    }

    /**
     * Add a health bar to be displayed in the top left corner of the level screen
     *
     * @param director the level's director class
     */
    public void addHealthBar(GigabitEconomy director) {
        this.healthBar = new PlayerHealthBar(director);
    }

    /**
     * Set the level the MovingSprite is in
     *
     * @param level the level the MovingSprite is in
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

    @Override
    public void drawOn(SpriteBatch batch, float delta) {
        super.drawOn(batch, delta);

        if (this.healthBar != null) {
            this.healthBar.drawOn(batch);
        }
    }

    /**
     * Launch an attack using the sprite's weapon (using launchAttack() in MovingSprite) OR collect a parcel from the parcel van.
     */
    @Override
    public void launchAttack() {
        // get Tile adjacent to Player
        System.out.println(getDirectionFacing());
        Tile adjacentTile = getTileManager().getAdjacentTile(getCurrentTiles().get(0), getDirectionFacing(), 1);
        if (adjacentTile == null) return; // trying to attack invalid Tile

        // if Player doesn't yet have a Parcel, check if next to a parcel van to collect one
        if (this.parcel == null) {
            // if adjacent tile is occupied by a parcel van, collect parcel
            TiledObject adjacentSprite = adjacentTile.getOccupiedBy();
            if (adjacentSprite instanceof ParcelVan) {
                level.getParcelVan().setInactive();
                this.parcel = new Parcel();
                return;
            }
        }
        // if Player does have a Parcel, check if next to House to be delivered to
        else {
            // if current tile or adjacent tile is owned by a House (door), deliver parcel
            TiledObject currentObject;
            try {
                currentObject = getCurrentTiles().get(0).getOwnedBy();
            } catch (NullPointerException ex) {
                currentObject = null;
            }
            TiledObject adjacentObject = adjacentTile.getOwnedBy();
            if ((adjacentObject instanceof House) ||
                    currentObject instanceof House) {
                parcel.deliver();
            }
        }

        super.launchAttack();
    }

    @Override
    public void attack(Weapon weapon) {
        // deduct -5 (base health detraction for Player) multiplied by hit multiplier of the used weapon from sprite
        setHealth(getHealth() - (BASE_HEALTH_DETRACTION * weapon.getHitMultiplier()));

        // update health bar value
        healthBar.setHealth(getHealth());
    }

    /**
     * Class for displaying and controlling Player health bar in top left of the screen
     */
    public class PlayerHealthBar extends HealthBar {
        private final Texture HEALTH_BAR_TEXTURE = new Texture("ui_elements/healthbar.png");
        private final Texture PARCEL_ICON = new Texture("ui_elements/parcelicon.png");

        private static final float WIDTH = 318f;
        private static final float HEIGHT = 72f;

        private Vector3 cam;
        private Vector2 pos = new Vector2();

        public PlayerHealthBar(GigabitEconomy director) {
            super(HEIGHT, WIDTH);

            this.cam = director.getCameraPos();
            pos.set(cam.x-900, cam.y+370);
        }

        @Override
        public void drawOn(SpriteBatch batch) {
            batch.end();

            getEllipse().begin(ShapeRenderer.ShapeType.Filled);
            getEllipse().setColor(Color.RED);
            getEllipse().rect(pos.x+100, pos.y+39, getDimensions()[0], getDimensions()[1]);
            getEllipse().end();

            batch.begin();
            batch.draw(HEALTH_BAR_TEXTURE, cam.x-900, cam.y+370);

            for (int i=0; i<level.getParcels(); i++) {
                batch.draw(PARCEL_ICON, cam.x-900+100+(1.15f*i*(PARCEL_ICON.getWidth())), cam.y+370);
            }
        }
    }

    /**
     * Open the Parcel the Player is currently carrying (if any)
     */
    public void openParcel() throws ParcelException {
        if (parcel == null) {
            throw new ParcelException("No parcel is being carried");
        }

        parcel.open();
    }

    /**
     * Destroy the Player & end the current level.
     * Called when the Player's health reaches 0 or less.
     */
    @Override
    public void destroy() {
        if (level != null) {
            level.end();
        }
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
            //Weapon assignedWeapon = MovingSprite.Weapon.values()[RANDOM.nextInt(Weapon.values().length)];
            Weapon[] randomWeapon = {Weapon.GOLF, Weapon.PIPE, Weapon.KNIFE, Weapon.KATANA};
            this.weapon = randomWeapon[RANDOM.nextInt(randomWeapon.length)];

            // pick a random House from the level's houses to be house to be delivered to
            ArrayList<House> levelHouses = level.getHouses();
            this.house = levelHouses.get((RANDOM.nextInt(levelHouses.size())));
            try {
                this.house.markAsDeliveryLocation();
            } catch (Exception ex) {
                Gdx.app.error("Exception", "Error assigning House as delivery location", ex);
                System.exit(-1);
            }

            // if level is onto final parcel, mark as such
            this.isFinalParcel = (level.getParcels() <= 1);

            level.decrementParcels();

            // set Parcel Van to inactive (no indicator)
            level.getParcelVan().setInactive();
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

            house.unmarkAsDeliveryLocation();
            if (level.getParcels() >= 1) {
                level.getParcelVan().setActive();
            }

            if (isFinalParcel) {
                level.complete();
            }
        }

        /**
         * Open the parcel and replace Player's Weapon with the Weapon inside the parcel
         *
         * @throws ParcelException if no Parcel is being carried or Parcel is final parcel
         */
        public void open() throws ParcelException {
            if (isFinalParcel) {
                throw new ParcelException("The final parcel must be delivered and cannot be opened");
            }

            setWeapon(this.weapon);

            house.unmarkAsDeliveryLocation();
            if (level.getParcels() >= 1) {
                level.getParcelVan().setActive();
            }

            parcel = null;
        }
    }
}
