package com.mygdx.gigabiteconomy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.gigabiteconomy.exceptions.ScreenException;
import com.mygdx.gigabiteconomy.screens.MenuScreen;
import com.mygdx.gigabiteconomy.screens.LevelOneScreen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.lang.Exception;
import java.lang.String;

public class GigabitEconomy extends Game {
    private OrthographicCamera camera;
    private ScreenViewport viewport;

    private static HashMap<String, Screen> screens = new HashMap<>();

    @Override
    public void create() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        viewport = new ScreenViewport(camera);

        // Define screens
        screens.put("menu", new MenuScreen(this));
        screens.put("level1", new LevelOneScreen(this));

        // Set active screen to main menu
        setScreen(screens.get("menu"));
    }

    public Vector3 getCameraPos() {
        return camera.position;
    }

     public void updateCameraPos(float x, float y) {
        // Map start and end locations
        float startX = 950;
        float startY = 515;

        float endX = 4820;
        float endY = 565;
       
        // If next camera position is out of borders just assign map borders to camera.
        if(y > startY) {
            if(y < endY) {
            } else y=endY;
        } else y=startY;

        if(x > startX) {
            if(x < endX) {
            } else x=endX;
        } else x=startX;
       
        camera.position.set(x, y, 0);
        camera.update();
    }

    public Matrix4 getCameraCombined() {
        // So batch can use camera coords
        return camera.combined;
    }

    public void switchScreen(String name) throws ScreenException {
        Screen toSwitch = screens.get(name);
        if (toSwitch == null) {
            throw new ScreenException(String.format("Tried to switch to invalid screen %s", name));
        }

        setScreen(toSwitch);
    }

    public ScreenViewport getViewport () {
        return viewport;
    }
}
