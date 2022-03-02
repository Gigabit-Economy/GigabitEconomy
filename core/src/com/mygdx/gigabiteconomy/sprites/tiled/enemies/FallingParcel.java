package com.mygdx.gigabiteconomy.sprites.tiled.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.gigabiteconomy.screens.Tile;
import com.mygdx.gigabiteconomy.screens.TileManager;
import com.mygdx.gigabiteconomy.sprites.tiled.*;

import java.util.LinkedList;

public class FallingParcel extends TiledObject implements Disposable {

    private MovingAnimation falling;
    private TextureRegion currentRegion;
    private TextureAtlas ta;
    private Array<TextureAtlas.AtlasRegion> regions;
    private float[] tilePosition;

    private Tile tileOn;
    private int x; private int y;

    public FallingParcel(int x, int y) {
        super(40, y, 1, 1); //Setting occupied tile beyond RK
        System.out.println("Creating new parcel");

        this.x = x; this.y = y;

        ta = new TextureAtlas("finished_assets/enemies/ratking/fallingBox.txt");
        regions = ta.getRegions();

        falling = new MovingAnimation<TextureRegion>(1/24f, regions, true);
        currentRegion = regions.get(0);


    }

    @Override
    public void drawOn(SpriteBatch batch, float delta) {
        if (tileOn == null) {
            tileOn = getTileManager().getTile(x, y);
            tileOn.setOwned(this);
            tilePosition = tileOn.getTileCoords();
        }
        if (falling.isFinished(delta)) destroy();

        currentRegion = (TextureRegion) falling.runAnimation(delta);

        batch.draw(currentRegion, tilePosition[0], tilePosition[1]);
    }

    public Tile getOwnedTile() {
        return tileOn;
    }

    public void destroy() {
        TiledObject occupiedBy = tileOn.getOccupiedBy();
        if (occupiedBy instanceof MovingSprite) {
            ((MovingSprite) occupiedBy).attack(MovingSprite.Weapon.GOLF);
        }
        tileOn.setOwned(null);
        getTileManager().removeFromRows(this);

        dispose();


    }


    @Override
    public void dispose() {
        ta.dispose();
    }
}
