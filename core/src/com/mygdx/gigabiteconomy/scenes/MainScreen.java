package com.mygdx.gigabiteconomy.scenes;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.gigabiteconomy.ISprite;
import com.mygdx.gigabiteconomy.MySprite;

import java.util.ArrayList;

public class MainScreen implements Screen, ApplicationListener, InputProcessor {
    ISprite player;

    TextureAtlas textureAtlas;
    SpriteBatch batch;
    Texture p;
    int i=0;

    //final HashMap<String, ISprite> sprites = new HashMap<String, ISprite>();
    final ArrayList<ISprite> sprites = new ArrayList<ISprite>(); //First sprite is ALWAYS player


    public void show() {
        batch = new SpriteBatch();
        //p = new Texture("playeramazon.png");

        player = new MySprite("amzn_9iron.txt",0 , 0);
        sprites.add(player); //Creating player sprite

        Gdx.input.setInputProcessor(this);

    }

    public void render(float delta) {
        System.out.println("Rendering");
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
//
//        for (ISprite sprite : sprites) {
//            //sprite.getCurrSprite().draw(batch);
//            batch.draw(sprite.getTex(), 0, 0, 200f, 0);
//        }
//
        //batch.draw(new TextureRegion(new Texture("spritesheet.png"), 1, 1, 128, 128),0 ,0);
        for (ISprite sprite : sprites) {
            if (sprite.isMoving()) sprite.move();
            batch.draw(sprite.getCurrRegion(), sprite.getX(), sprite.getY());

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        batch.end();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) System.out.println("A pressed");
    }

    @Override
    public void create() {
        Gdx.input.setInputProcessor(this);
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
    public void hide() {
    }

    @Override
    public void dispose() {
        p.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {

        System.out.println("Key pressed");

        //Handles player movement on key press. Everything handled inside Sprite class
        if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
            player.setMoving(true);
            player.setDCoords(10, 0);
        }
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
            player.setMoving(true);
            //Need to flip
            player.setDCoords(-10, 0);
        }
        if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
            player.setMoving(true);
            player.setDCoords(0, 10);
        }
        if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            player.setMoving(true);
            player.setDCoords(0, -10);
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        player.setMoving(false);
        player.setDCoords(0, 0);
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
}
