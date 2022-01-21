package com.mygdx.gigabiteconomy.scenes;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.gigabiteconomy.Enemy;
import com.mygdx.gigabiteconomy.GameObject;
import com.mygdx.gigabiteconomy.Player;

import java.util.ArrayList;

/**
 * Class from which the game is played from. Contains game loop and other important stuff
 */
public class MainScreen implements Screen, ApplicationListener {
    GameObject player; GameObject testEnemy;

    OrthographicCamera camera;

    TextureAtlas textureAtlas;
    SpriteBatch batch;
    Sprite backgroundSprite;
    Texture p;
    Texture backgroundTexture;
    int i=0;

    //final HashMap<String, GameObject> sprites = new HashMap<String, GameObject>();
    final ArrayList<GameObject> sprites = new ArrayList<GameObject>(); //First sprite is ALWAYS player


    public void show() {

        batch = new SpriteBatch();
        //p = new Texture("playeramazon.png");

        player = new Player("amzn_9iron.txt",0 , 0);
        sprites.add(player); //Creating player sprite

        //Adding enemy to screen to test collision detection
        testEnemy = new Enemy("amzn_9iron.txt", 500, 500);
        sprites.add(testEnemy);

        backgroundTexture = new Texture("finished_assets/levels/level1.png");
        backgroundSprite = new Sprite(backgroundTexture);

        camera = new OrthographicCamera(1920, 1080);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();

        // drawing the background
        backgroundSprite.draw(batch);

        // this thing makes the camera work XD
        batch.setProjectionMatrix(camera.combined);

        // update camera position
        camera.position.set(player.getActorX(), player.getActorY(), 0);
        camera.update();


//
//        for (GameObject sprite : sprites) {
//            //sprite.getCurrSprite().draw(batch);
//            batch.draw(sprite.getTex(), 0, 0, 200f, 0);
//        }

        //batch.draw(new TextureRegion(new Texture("spritesheet.png"), 1, 1, 128, 128),0 ,0);
        for (GameObject sprite : sprites) {
            if (sprite.isMoving()) sprite.move();
            batch.draw(sprite.getCurrRegion(), sprite.getActorX(), sprite.getActorY());

        }



        batch.end();




        playerCollisionCheck();
    }

    public void playerCollisionCheck() {
        if (player.getRectangle().overlaps(testEnemy.getRectangle())) {
            System.out.println("Enemy collision detected");
        }
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
    public void hide() {
    }

    @Override
    public void dispose() {
        p.dispose();
    }

}
