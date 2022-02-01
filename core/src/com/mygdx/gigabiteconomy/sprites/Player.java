package com.mygdx.gigabiteconomy.sprites;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import org.graalvm.compiler.lir.aarch64.AArch64Move;

/**
 * Class for separating Player functionality from general Sprite functionality
 * Such as:
 *  > Attacking (detecting collisions for certain sprites only)
 */
public class Player extends MySprite implements ApplicationListener, InputProcessor {
    public Player(String config, int x, int y) {
        super(config, x, y);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("Key pressed");
        setMoving(true); //Run animation

        //Handles player movement on key press. Everything handled inside Sprite class
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
            // Move left
            direction = MoveDirection.LEFT;
        }
        else if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
            // Move right
            direction = MoveDirection.RIGHT;
        }
        else if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
            // Move up
            direction = MoveDirection.UP;
        }
        else if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            // Move down
            direction = MoveDirection.DOWN;
        }

        if (direction != null) currentTile = tm.moveFromTile(currentTile, direction.name());
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        //setMoving just handles the animation
        //setMoving(false);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        keyDown(character);
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
