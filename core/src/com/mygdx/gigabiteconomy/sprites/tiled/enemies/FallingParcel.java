package com.mygdx.gigabiteconomy.sprites.tiled.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gigabiteconomy.sprites.tiled.Enemy;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingAnimation;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;
import com.mygdx.gigabiteconomy.sprites.tiled.TiledObject;

import java.util.LinkedList;

public class FallingParcel extends TiledObject {

    private MovingAnimation falling;
    private TextureRegion currentRegion;
    private float[] tilePosition;

    public FallingParcel(float x, float y, int height, int width) {
        super(x, y, height, width);

        Array<TextureAtlas.AtlasRegion> regions = new TextureAtlas("finished_assets/enemies/ratking/fallingBox.txt").getRegions();
        falling = new MovingAnimation(1/14f, regions, false);
        currentRegion = regions.get(0);

        tilePosition = getCurrentTiles().get(0).getTileCoords();
    }

    @Override
    public void drawOn(SpriteBatch batch, float delta) {
        if (falling.isFinished(delta)) dispose();

        currentRegion = (TextureRegion) falling.runAnimation(delta);

        batch.draw(currentRegion, tilePosition[0], tilePosition[1]);
    }

    @Override
    public void dispose() {
        getCurrentTiles();

    }
}
