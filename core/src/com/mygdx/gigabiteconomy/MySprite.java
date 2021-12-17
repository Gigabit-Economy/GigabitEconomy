package com.mygdx.gigabiteconomy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class MySprite extends Sprite implements ISprite {

    public MySprite(Texture ptexture) {}

    public MySprite(TextureAtlas ptextureAtlas) {}

    @Override
    public Texture getTex() {
        return null;
    }
}
