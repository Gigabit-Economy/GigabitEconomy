package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.screens.Tile;

import java.util.Locale;

/**
 * Class for separating Player functionality from general Sprite functionality
 * Such as:
 *  > Attacking (detecting collisions for certain sprites only)
 */
public class Player extends MySprite implements ApplicationListener, InputProcessor {

    boolean stillMoving = false;
    private static float deltaVert = 1.5F;
    private static float deltaHoriz = 1.75F;

    public Player(String config, int x, int y) {
        super(config, x, y);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        //if (isMoving()) return false;

        System.out.println("key pressed: " + keycode);

        //Might have to move movement handling into other method if we add more functions for key presses

        //Handles player movement on key press. Everything handled inside Sprite class
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
            direction = null;
            return false;
        }
        System.out.println("delta set to: " + deltaMove.toString());

        stillMoving = true;
        setMoving(true);
        return false;
//        System.out.println("Key press detected, moving sprite: " + direction + " " + keycode);
//
//        if (direction != null) {
//            Tile newCurrentTile = tm.moveFromTile(currentTile, direction.name(), 2);
//
//            if (newCurrentTile != currentTile) {
//                currentTile = newCurrentTile;
//                setMoving(true); //Run animation
//            } else {
//                System.out.println("Not running since new direction is unavailable");
//            }
//        }
    }

    @Override
    public boolean keyUp(int keycode) {
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
            direction = null;
            return false;
        }
        if (deltaMove.epsilonEquals(0, 0)) {
            setMoving(false); //Should stop as soon as possible
        }

        System.out.println("delta removed to: " + deltaMove.toString());
        //setMoving just handles the animation
        //setMoving(false);
        return false;
    }

    @Override
    public boolean move(float delta) {
        boolean ret = super.move(delta); //Move to new pos if isMoving() == true

        if (!ret && stillMoving) {
            pos.x = currentTile.getTileCoords()[0];
            pos.y = currentTile.getTileCoords()[1];
        }
        return true;

    }

    @Override
    public boolean keyTyped(char character) {
        //keyDown(character);
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
