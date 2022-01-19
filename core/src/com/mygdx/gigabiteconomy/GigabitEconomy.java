package com.mygdx.gigabiteconomy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.gigabiteconomy.scenes.MainScreen;
import com.mygdx.gigabiteconomy.scenes.MenuScreen;
import jdk.tools.jmod.Main;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

import java.util.HashMap;

public class GigabitEconomy extends Game {

    OrthographicCamera camera;
    FillViewport vp;

    HashMap<String, Screen> screens = new HashMap<>();

    @Override
    public void create() {
        camera = new OrthographicCamera(1920, 1080);
        vp = new FillViewport(5760, 1080, camera);


        System.out.println("Creating screen");
        //Change to MenuScreen()
        //Passing director (this class) to menu screen so we can call swap screen method
        screens.put("menu", new MenuScreen(this));
        screens.put("main", new MainScreen(this));

        setScreen(screens.get("main"));
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


    @Override
    public void dispose() {

    }

    public void updateCameraPos(int x, int y) {
        camera.position.set(x, y, 0);
        camera.update();

    }

    public Matrix4 getCombined() {
        //So batch can use camera coords
        return camera.combined;
    }
}
