package com.mygdx.gigabiteconomy;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

/**
 * Class for separating Player functionality from general Sprite functionality
 * Such as:
 *  > Attacking (detecting collisions for certain sprites only)
 */
public class Player extends MySprite implements ApplicationListener, InputProcessor {


    public Player(String config, int x, int y) {
        super(config, x, y);
        Gdx.input.setInputProcessor(this);


        //this.setSize(100, 100);
    }


    @Override
    public boolean keyDown(int keycode) {
        System.out.println("Key pressed");

        //Handles player movement on key press. Everything handled inside Sprite class
        if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
            //Find x,y of right tile and move
            setMoving(true);
            setDCoords(10, 0);
        }
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
            //Find x,y of left tile and move
            setMoving(true);
            //Need to flip
            setDCoords(-10, 0);
        }
        if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
            //Find x,y of top tile and move
            setMoving(true);
            setDCoords(0, 10);
        }
        if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            //Find x,y of bottom tile and move
            setMoving(true);
            setDCoords(0, -10);
        }

        return false;
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
}
