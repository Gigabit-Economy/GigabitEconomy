package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.gigabiteconomy.screens.Tile;

/**
 * Class for separating Player functionality from general Sprite functionality
 * Such as:
 *  > Attacking (detecting collisions for certain sprites only)
 */
public class Player extends MySprite implements ApplicationListener, InputProcessor {

    //Deprecated variable for determining if player has to finish movement to centre of next Tile on keyUp()
    boolean stillMoving = false;
    //How much player should move vertically and horizontally every move() respectively
    private static float deltaVert = 3F;
    private static float deltaHoriz = 3.5F;



    public Player(String config, int x, int y) {
        super(config, x, y);

        Gdx.input.setInputProcessor(this);
    }

    public void handleMovement(int keycode) {
        if (targetTile != null && !isMoving()) {
            System.out.println("Not finished with previous movement");
            return; //Not finished with previous movement
        }

        Tile toTarget = null;
        DIRECTION direction = null;
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
            // Move left
            direction = DIRECTION.WEST;
        }
        else if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
            // Move right
            direction = DIRECTION.EAST;
        }
        else if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
            // Move up
            direction = DIRECTION.NORTH;
        }
        else if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            // Move down
            direction = DIRECTION.SOUTH;
        } else {
            System.out.println(keycode + " not accounted for in movement logic");
            return;
        }

        toTarget = tm.getAdjecentTile(currentTile, direction.toString(), 1);
        setDeltaMove(direction.dx, direction.dy);

        if (toTarget != null && toTarget.getOccupiedBy() == null) {
            targetTile = toTarget;
            setMoving(true);
            System.out.println("Moving true to " + targetTile.getTileCoords()[0] + " " + targetTile.getTileCoords()[1]);
        }
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

        } else {
            return false;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        setMoving(false);

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

    private enum DIRECTION {
        NORTH (0, deltaVert),
        EAST (deltaHoriz, 0),
        SOUTH (0, -deltaVert),
        WEST (-deltaHoriz, 0);

        private final float dx;
        private final float dy;

        private DIRECTION(float dx, float dy) {
            this.dx = dx; this.dy = dy;
        }
    }


}
