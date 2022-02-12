package com.mygdx.gigabiteconomy.sprites;

import java.lang.System.Logger.Level;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.screens.LevelOneScreen;
import com.mygdx.gigabiteconomy.screens.Tile;

import javax.sound.midi.SysexMessage;

/**
 * Class for separating Player functionality from general Sprite functionality
 * Such as:
 * > Attacking (detecting collisions for certain sprites only)
 */
public class Player extends MovingSprite implements ApplicationListener, InputProcessor {
    // Deprecated variable for determining if player has to finish movement to
    // centre of next Tile on keyUp()
    boolean stillMoving = false;
    
    public Player(String move_config, String attack_config, int x, int y) {
        super(move_config, attack_config, x, y);

        Gdx.input.setInputProcessor(this);
        LevelOneScreen LevelScreenObj = new LevelOneScreen(LevelOneScreen);
    }

    /**
     * Method to handle movement
     * 
     * @param keycode
     */
    public void handleMovement(int keycode) {
        if (targetTile != null && !isMoving() || directionMoving != null) {
            System.out.println("Not finished with previous movement");
            return; // Not finished with previous movement
        }

        /**
         * Sets direction enum which defines the velocity vector (which speed and
         * direction to move in with each move() call)
         */
        Tile toTarget = null;

        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
            // Move left
            directionMoving = DIRECTION.WEST;
        } else if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
            // Move right
            directionMoving = DIRECTION.EAST;
        } else if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
            // Move up
            directionMoving = DIRECTION.NORTH;
        } else if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            // Move down
            directionMoving = DIRECTION.SOUTH;
        } else {
            System.out.println(keycode + " not accounted for in movement logic");
            return;
        }

        /**
         * Uses tile manager to get adjecentTile
         * Sets velocity vector based on value of direction set above
         */
        toTarget = tm.getAdjecentTile(currentTile, directionMoving.toString(), 1);
        setDeltaMove(directionMoving.dx, directionMoving.dy);

        // Checks if Player can move to Tile
        if (toTarget != null && toTarget.getOccupiedBy() == null) {
            targetTile = toTarget;
            setMoving(true);
            System.out.println("Moving true to " + targetTile.getTileCoords()[0] + " " + targetTile.getTileCoords()[1]);
        } else {
            targetTile = null;
            //setMoving(false);
        }
    }

    @Override
    public boolean keyDown(int keycode) {

        System.out.println("key pressed: " + keycode);

        // Might have to move movement handling into other method if we add more
        // functions for key presses

        /**
         * Movement calculated by:
         * -> Adding values defined above to deltaMove vector
         * -> Every time move() is called, deltaMove is added to position vector
         * -> This allows for diagonal movement
         */
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT || keycode == Input.Keys.D ||
                keycode == Input.Keys.RIGHT || keycode == Input.Keys.W ||
                keycode == Input.Keys.UP || keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {

            handleMovement(keycode);

        } else if (keycode == Input.Keys.P || keycode == Input.Keys.ESCAPE) {
            pause();
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
        // Player no longer WANTS to be moving, but we must finish animation to centre
        // of target square
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
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {
        //if (Gdx.input.isKeyPressed(Keys.P)) {
           // LevelScreen levelScreenObj = new LevelScreen();
           //LevelScreenObj.setPauseMenuStatus(true);
            System.out.println("P or ESQ was pressed");
       // }

    }


}
