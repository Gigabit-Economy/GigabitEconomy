package com.mygdx.gigabiteconomy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.gigabiteconomy.scenes.MenuScreen;
import com.mygdx.gigabiteconomy.scenes.LevelOneScreen;

import java.util.HashMap;
import java.lang.Exception;
import java.lang.String;

public class GigabitEconomy extends Game {
    private OrthographicCamera camera;
    private ScreenViewport viewport;

    private static HashMap<String, Screen> screens = new HashMap<>();

    @Override
    public void create() {
        camera = new OrthographicCamera(1920, 1080);
        viewport = new ScreenViewport(camera);

        // Define screens
        screens.put("menu", new MenuScreen(this));
        screens.put("level1", new LevelOneScreen(this));

        // Set active screen to main menu
        setScreen(screens.get("menu"));
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

    public void switchScreen(String name) throws Exception {
        Screen toSwitch = screens.get(name);
        if (toSwitch == null) {
            throw new Exception(String.format("Tried to switch to invalid screen %s", name));
        }

        setScreen(toSwitch);
    }

    public ScreenViewport getViewport () {
        return viewport;
    }
}
