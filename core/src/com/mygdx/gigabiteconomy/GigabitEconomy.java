package com.mygdx.gigabiteconomy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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

    private Screen fromPause;

    private static final String MUSIC_BASE_PATH = "music/";
    private Music backgroundMusic;

    @Override
    public void create() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new ScreenViewport(camera);

        // Set initial active screen to main menu
        try {
            switchScreen("MenuScreen");
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

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void switchScreen(String name) throws ScreenException {
        Screen toSwitch;
        switch (name) {
            case "MenuScreen":
                toSwitch = new MenuScreen(this);
                setMusic("Menu");
                break;
            case "LevelSelectScreen":
                toSwitch = new LevelSelectScreen(this);
                break;
            case "TutorialScreen":
                toSwitch = new TutorialScreen(this);
                break;
            case "PauseMenu":
                fromPause = getScreen();
                toSwitch = new PauseMenu(this);
                break;
            case "LevelComplete":
                toSwitch = new LevelCompleteScreen(this);
                setMusic("Menu");
                break;
            case "LevelFailed":
                toSwitch = new LevelFailedScreen(this);
                setMusic("Menu");
                break;

            case "LevelOneScreen":
                if (fromPause != null) {
                    setScreen(fromPause);
                    this.fromPause = null;
                    return;
                } else {
                    toSwitch = new LevelOneScreen(this);
                    setMusic("LevelOne");
                }
                break;
            case "LevelTwoScreen":
                if (fromPause != null) {
                    setScreen(fromPause);
                    this.fromPause = null;
                    return;
                } else {
                    toSwitch = new LevelTwoScreen(this);
                    setMusic("LevelTwo");
                }
                break;
            case "LevelRatKing":
                if (fromPause != null) {
                    setScreen(fromPause);
                    this.fromPause = null;
                    return;
                } else {
                    toSwitch = new LevelRatKing(this);
                }
                break;

            default:
                throw new ScreenException(String.format("Tried to switch to invalid screen %s", name));
        }

        // if a LevelScreen, record as lastPlayedLevel
        if (toSwitch instanceof LevelScreen) {
            this.lastPlayedLevel = toSwitch.getClass().getSimpleName();
        }
        // if returned to menu, reset fromPause & lastPlayedLevel (prevents from being taken back to exited level)
        else if (toSwitch instanceof MenuScreen) {
            this.fromPause = null;
            this.lastPlayedLevel = null;
        }

        setScreen(toSwitch);
    }

    /**
     * Set the playing background music
     *
     * @param name the music file name in "[MUSIC_BASE_PATH]/[...].wav"
     */
    public void setMusic(String name) {

        if (this.backgroundMusic != null) {
            this.backgroundMusic.stop();
        }

        this.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(String.format("%s/%s.wav", MUSIC_BASE_PATH, name)));

        this.backgroundMusic.setLooping(true);
        enableMusic(true);
    }

    public void enableMusic(boolean enable) {
        if (enable == true) {

            System.out.println(isMusicPlaying());
            this.backgroundMusic.play();

        }
        if (enable == false) {
            System.out.println(isMusicPlaying());
            this.backgroundMusic.stop();

        }
    }

    public boolean isMusicPlaying() {
        try {
            return this.backgroundMusic.isPlaying();
        } catch (Exception ex) {
                Gdx.app.error("Exception", String.format("Audio could not be found playing"), ex);
        }
        return false;
        
    }

    public ScreenViewport getViewport() {
        return viewport;
    }

    /**
     * Get the recorded last played level
     *
     * @return the lastPlayedLevel (currently playing level)
     */
    public String getLastPlayedLevel() {
        return this.lastPlayedLevel;
    }

    /**
     * Get the next level to the recorded last played level
     *
     * @return the next level after lastPlayedLevel
     */
    public String getNextLevel() {
        switch (this.lastPlayedLevel) {
            case "LevelOneScreen":
                return "LevelTwoScreen";
            case "LevelTwoScreen":
                return "LevelThreeScreen";

            default:
                return "LevelSelectScreen";
        }
    }
}
