package com.mygdx.gigabiteconomy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.HashMap;

public class MainScreen implements Screen {
    TextureAtlas textureAtlas;
    SpriteBatch batch;
    Texture p;

    //final HashMap<String, ISprite> sprites = new HashMap<String, ISprite>();
    final ArrayList<ISprite> sprites = new ArrayList<ISprite>();


    public void show() {
        batch = new SpriteBatch();
        //p = new Texture("playeramazon.png");

        sprites.add(new Player(new Texture("playeramazon.png")));

    }

    public void render(float delta) {
        System.out.println("Rendering");
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        for (ISprite sprite : sprites) {
            batch.draw(sprite.getTex(), 0, 0, 200f, 300f);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
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
}
