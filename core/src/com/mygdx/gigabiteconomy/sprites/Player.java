package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.gigabiteconomy.screens.Tile;

import javax.sound.midi.SysexMessage;

/**
 * Class for separating Player functionality from general Sprite functionality
 * Such as:
 *  > Attacking (detecting collisions for certain sprites only)
 */
public class Player extends MovingSprite implements ApplicationListener, InputProcessor {
    //Deprecated variable for determining if player has to finish movement to centre of next Tile on keyUp()
    boolean stillMoving = false;
    //How much player should move vertically and horizontally every move() respectively
    
    public Player(String move_config, String attack_config, int x, int y) {
        super(move_config, attack_config, x, y);

        Gdx.input.setInputProcessor(this);
    }



    /**
     * Method to handle movement
     * @param keycode
     */
    public void handleMovement(int keycode) {
        if (targetTile != null) {
            System.out.println("Not finished with previous movement");
            return; //Not finished with previous movement
        }

        /**
         * Sets direction enum which defines the velocity vector (which speed and direction to move in with each move() call)
         */
        Tile toTarget = null;

        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
            // Move left
            setDirectionMovement(DIRECTION.WEST);
        }
        else if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
            // Move right
            setDirectionMovement(DIRECTION.EAST);
        }
        else if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
            // Move up
            setDirectionMovement(DIRECTION.NORTH);
        }
        else if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            // Move down
            setDirectionMovement(DIRECTION.SOUTH);
        } else {
            System.out.println(keycode + " not accounted for in movement logic");
            return;
        }
        setMoving(true);

        /**
         * Uses tile manager to get adjecentTile
         * Sets velocity vector based on value of direction set above
         */
//        toTarget = tm.getAdjecentTile(currentTile, directionMoving.toString(), 1);
//        setDeltaMove(directionMoving);
//
//        //Checks if Player can move to Tile
//        if (toTarget != null && toTarget.getOccupiedBy() == null) {
//            targetTile = toTarget;
//            setMoving(true);
//            System.out.println("Moving true to " + targetTile.getTileCoords()[0] + " " + targetTile.getTileCoords()[1]);
//        } else {
//            targetTile = null;
//            //setMoving(false);
//        }
    }

    @Override
    public boolean keyDown(int keycode) {

        System.out.println("key pressed: " + keycode);

        //Might have to move movement handling into other method if we add more functions for key presses

        /**
         * Movement calculated by:
         *  -> Adding values defined above to deltaMove vector
         *  -> Every time move() is called, deltaMove is added to position vector
         *  -> This allows for diagonal movement
         */
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT || keycode == Input.Keys.D ||
                keycode == Input.Keys.RIGHT || keycode == Input.Keys.W ||
                keycode == Input.Keys.UP || keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {

            handleMovement(keycode);

        } else if (keycode == Input.Keys.SPACE) {
            System.out.println("Attacking now");
            setAttacking(true);
        } else {
            return false;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        //Player no longer WANTS to be moving, but we must finish animation to centre of target square
        setMoving(false);

        return false;
    }

    @Override
    public boolean moveBlocked() {
        if (targetTile == null || targetTile.getOccupiedBy() != null) {
            setDirectionMovement(null);
            targetTile = null;
            setMoving(false);
            return true;
        }
        return false;
    }

    @Override
    public Tile getNextTile() {
        targetTile = tm.getAdjecentTile(currentTile, directionMoving.name(), 1);
        if (targetTile != null) System.out.println("Getting player a target tile " + targetTile.getPositionTile()[0] + " " + targetTile.getPositionTile()[1]);
        return targetTile;
    }

    @Override
    public void moveStart() {
        return;
    }

    @Override
    public boolean move(float delta) {
        boolean ret = super.move(delta);

        if (ret && isMoving()) {
            setDirectionMovement(directionMoving);
            System.out.println("Holding down, direction movement set");
        } else if (ret && !isMoving()) {
            setDirectionMovement(null);
            System.out.println("Setting to null");
        }



//        if (ret) {
//            if (isMoving()) {
//                //If key is still held down, get next tile
//                targetTile = tm.getAdjecentTile(targetTile, directionMoving.toString(), 1);
//                if (targetTile == null) setMoving(false);
//                return true;
//            }
//            else if (!isMoving() && targetTile != null) {
//                //If key is released, but there's still distance to cover
//                //currentTile = targetTile; useless line?
//                targetTile = null;
//                snap(delta);
//                //Reset direction moving
//                directionMoving = null;
//                return true;
//            }
//        }

        return false;
    }



    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }




}
