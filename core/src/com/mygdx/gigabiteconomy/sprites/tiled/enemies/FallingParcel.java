package com.mygdx.gigabiteconomy.sprites.tiled.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.screens.TileManager;
import com.mygdx.gigabiteconomy.sprites.tiled.Enemy;
import com.mygdx.gigabiteconomy.sprites.tiled.MovingAnimation;
import com.mygdx.gigabiteconomy.sprites.tiled.Player;
import com.mygdx.gigabiteconomy.sprites.tiled.TiledObject;

import java.util.LinkedList;

public class FallingParcel extends TiledObject {

    private MovingAnimation falling;
    private TextureRegion currentRegion;
    private float[] tilePosition;

    private Tile tileOn;
    private int x; private int y;

    public FallingParcel(int x, int y) {
        super(40, y, 1, 1); //Setting occupied tile beyond RK
        System.out.println("Creating new parcel");

        this.x = x; this.y = y;

        Array<TextureAtlas.AtlasRegion> regions = new TextureAtlas("finished_assets/enemies/ratking/fallingBox.txt").getRegions();
        falling = new MovingAnimation(1/14f, regions, false);
        currentRegion = regions.get(0);


    }

    @Override
    public void drawOn(SpriteBatch batch, float delta) {
        if (tileOn == null) {
            tileOn = getTileManager().getTile(x, y);
            tileOn.setOwned(this);
            tilePosition = tileOn.getTileCoords();
        }
        if (falling.isFinished(delta)) dispose();

        currentRegion = (TextureRegion) falling.runAnimation(delta);

        batch.draw(currentRegion, tilePosition[0], tilePosition[1]);
    }

    public Tile getOwnedTile() {
        return tileOn;
    }

    @Override
    public void dispose() {
        tileOn.setOwned(null);
        getTileManager().removeFromRows(this);

    }
}
