package com.mygdx.gigabiteconomy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Player extends MySprite {
    private SpriteBatch batch;
    private TiledMap map;
    private Texture ptexture;

    public Player(Texture ptexture) {
        super(ptexture);

        this.ptexture = ptexture;
        //this.setSize(100, 100);
    }

    @Override
    public Texture getTex() {
        return ptexture;
    }
}
