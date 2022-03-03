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

    /**
     * Create the Game instance, setup the camera and set the starting screen to the menu (MenuScreen).
     * Called by LibGDX when an instance of the Game is instantiated.
     */
    @Override
    public void create() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new ScreenViewport(camera);

        // Set initial active screen to menu
        try {
            switchScreen("MenuScreen");
        } catch (ScreenException ex) {
            Gdx.app.error("Exception", "The screen could not be switched when level failed", ex);
            System.exit(-1);
        }
    }

    /**
     * Get the game's orthographic camera instance (which displays the game world)
     *
     * @return the game's OrthographicCamera instance
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Get the game camera's combined value
     *
     * @return the game camera's combined value
     */
    public Matrix4 getCameraCombined() {
        return camera.combined;
    }

    public Vector3 getCameraPos() {
        return camera.position;
    }

    /**
     * Update the position of the game's camera
     *
     * @param x the x coordinate to focus on
     * @param y the y coordinate to focus on
     */
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

    /**
     * Switch the game's currently showing screen
     *
     * @param name the name identifier of the screen to be switched to - usually matches class name (i.e. LevelOneScreen)
     * @throws ScreenException if an invalid screen name is passed
     */
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

            case "LevelOnePlotScreen":
                toSwitch = new LevelOnePlotScreen(this);
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
            case "LeveTwoPlotScreen":
                toSwitch = new LevelOnePlotScreen(this);
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
            case "LevelThreePlotScreen":
                toSwitch = new LevelRatKingPlotScreen(this);
                break;
            case "LevelThreeScreen":
                if (fromPause != null) {
                    setScreen(fromPause);
                    this.fromPause = null;
                    return;
                } else {
                    toSwitch = new LevelThreeScreen(this);
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
     * Reset the currently paused level instance (fromPause)
     */
    public void resetPausedLevel() {
        this.fromPause = null;
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

    /**
     * Enable/disable the currently selected music
     *
     * @param enable if music should be enabled (true => enabled; false => disabled)
     */
    public void enableMusic(boolean enable) {
        if (enable == true) {
            this.backgroundMusic.play();
        }
        if (enable == false) {
            this.backgroundMusic.stop();
        }
    }

    /**
     * Get if the music is playing
     *
     * @return if music is playing (true => is playing; false => not playing)
     */
    public boolean isMusicPlaying() {
        try {
            return this.backgroundMusic.isPlaying();
        } catch (Exception ex) {
            Gdx.app.error("Exception", String.format("Audio could not be found playing"), ex);
        }

        return false;
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

    /**
     * Get the screen viewport of the game
     *
     * @return the game's ScreenViewport instance
     */
    public ScreenViewport getViewport() {
        return viewport;
    }
}
