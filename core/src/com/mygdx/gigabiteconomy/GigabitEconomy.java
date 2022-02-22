package com.mygdx.gigabiteconomy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.gigabiteconomy.exceptions.ScreenException;
import com.mygdx.gigabiteconomy.screens.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Gdx;

import java.lang.String;

public class GigabitEconomy extends Game {
    private OrthographicCamera camera;
    private ScreenViewport viewport;

    private String lastPlayedLevel;

    @Override
    public void create() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new ScreenViewport(camera);

        // Set initial active screen to main menu
        try {
            switchScreen("menu");
        } catch (ScreenException ex) {
            Gdx.app.error("Exception", "The screen could not be switched when level failed", ex);
            System.exit(-1);
        }
    }

    public Vector3 getCameraPos() {
        return camera.position;
    }

    public void updateCameraPos(float x, float y) {
        // Map start and end locations
        float startX = viewport.getWorldWidth() / 2;
        float startY = viewport.getScreenHeight() / 2;

        float endX = 5760 - viewport.getWorldWidth() / 2;
        float endY = 1080 - viewport.getScreenHeight() / 2;

        // If next camera position is out of borders just assign map borders to camera.
        if (y > startY) {
            if (y < endY) {
            } else
                y = endY;
        } else
            y = startY;

        if (x > startX) {
            if (x < endX) {
            } else
                x = endX;
        } else
            x = startX;

        camera.position.set(x, y, 0);
        camera.update();
    }

    public Matrix4 getCameraCombined() {
        // So batch can use camera coords
        return camera.combined;
    }

    public void switchScreen(String name) throws ScreenException {
        Screen toSwitch;

        switch (name) {
            case "menu":
                toSwitch = new MenuScreen(this);
                break;
            case "pausemenu":
                toSwitch = new PauseMenu(this);
                break;
            case "levelcomplete":
                toSwitch = new LevelCompleteScreen(this);
                break;
            case "levelfailed":
                toSwitch = new LevelFailedScreen(this);
                break;

            case "LevelOneScreen":
                toSwitch = new LevelOneScreen(this);
                break;
            case "levelSelectScreen":
                toSwitch = new LevelSelectScreen(this);
                break;    

            default:
                throw new ScreenException(String.format("Tried to switch to invalid screen %s", name));
        }

        // if a LevelScreen, record as lastPlayedLevel
        if (toSwitch instanceof LevelScreen) {
            this.lastPlayedLevel = toSwitch.getClass().getSimpleName();
        }

        setScreen(toSwitch);
    }

    public ScreenViewport getViewport() {
        return viewport;
    }

    public String getLastPlayedLevel() {
        return this.lastPlayedLevel;
    }
}
