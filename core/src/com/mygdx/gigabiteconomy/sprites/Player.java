package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Class for separating Player functionality from general Sprite functionality
 * Such as:
 *  > Attacking (detecting collisions for certain sprites only)
 */
public class Player extends MySprite implements ApplicationListener, InputProcessor {

    //Deprecated variable for determining if player has to finish movement to centre of next Tile on keyUp()
    boolean stillMoving = false;
    //How much player should move vertically and horizontally every move() respectively
    private static float deltaVert = 1.5F;
    private static float deltaHoriz = 1.75F;

    public Player(String config, int x, int y) {
        super(config, x, y);

        Gdx.input.setInputProcessor(this);
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
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
            // Move left
            deltaMove.add(-deltaHoriz, 0);
        }
        else if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
            // Move right
            deltaMove.add(deltaHoriz, 0);
        }
        else if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
            // Move up
            deltaMove.add(0, deltaVert);
        }
        else if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            // Move down
            deltaMove.add(0, -deltaVert);
        } else {
            return false;
        }
        System.out.println("delta set to: " + deltaMove.toString());

        stillMoving = true;
        setMoving(true);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        /**
         * Simply removes change from deltaMove vector made in keyDown()
         */
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
            // Move left
            deltaMove.sub(-deltaHoriz, 0);
        }
        else if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
            // Move right
            deltaMove.sub(deltaHoriz, 0);
        }
        else if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
            // Move up
            deltaMove.sub(0, deltaVert);
        }
        else if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            // Move down
            deltaMove.sub(0, -deltaVert);
        } else {
            return false;
        }
        if (deltaMove.epsilonEquals(0, 0)) {
            setMoving(false); //Should stop as soon as possible
        }

        System.out.println("delta removed to: " + deltaMove.toString());

        return false;
    }

    @Override
    public boolean move(float delta) {
        boolean ret = super.move(delta);

        /**
         * Makes sure to centre Player on the Tile the user stopped on
         * -> This is what stillMoving was for, if we keep Tiles small then the user won't see much 'snap' (teleporting of Player to centre)
         */
        if (!ret && stillMoving) {
            snap();
            stillMoving = false;
        }
        return true;

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
