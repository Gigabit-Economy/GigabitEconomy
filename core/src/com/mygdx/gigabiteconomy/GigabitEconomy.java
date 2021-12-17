package com.mygdx.gigabiteconomy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import java.util.HashMap;

public class GigabitEconomy extends Game {
    final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    TextureAtlas textureAtlas;
    SpriteBatch batch;
    Player p;

    @Override
    public void create() {
        System.out.println("Creating screen");
        setScreen(new MainScreen());



    }

    private void addSprites() {

    }

    @Override
    public void render() {
        super.render();
    }
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    private void drawSprite(String name, float x, float y) {

    }

    @Override
    public void dispose() {

    }

}
