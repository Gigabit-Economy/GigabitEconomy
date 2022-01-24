package com.mygdx.gigabiteconomy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.gigabiteconomy.scenes.MenuScreen;
import com.mygdx.gigabiteconomy.scenes.LevelOneScreen;

import java.util.HashMap;

public class GigabitEconomy extends Game {
    private OrthographicCamera camera;
    private ScreenViewport vp;

    private final static HashMap<String, Screen> screens = new HashMap<>();

    @Override
    public void create() {
        camera = new OrthographicCamera(1920, 1080);
        vp = new ScreenViewport(camera);

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

    public void switchScreen(String name) {
        Screen toSwitch = screens.get(name);
        if (toSwitch == null) {
            //Maybe we can get a fancy exception going here?
            System.out.println(">>> SCREEN NOT FOUND <<<");
            return;
        }
        setScreen(toSwitch);
    }

    public ScreenViewport getViewport () {
        return vp;
    }
}
