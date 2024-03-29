package com.mygdx.gigabiteconomy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gigabiteconomy.GigabitEconomy;

/**
 * Screen shown when user accesses "help" via. menu screen or pause menu
 */
public class TutorialScreen implements Screen {
    private final static String DESCRIPTION = "You are a delivery driver aiming to do your round. Deliver to all the houses you can without falling victim to the dangers of suburbia. If worst comes to worst though you can always borrow what the content of your parcels. Just remember in the gig-economy the deliveries you make (and the ones you don't) affect the score your company gives you at the end of the day.";

    private GigabitEconomy director;

    private Stage stage;
    private Table informationScreenTable;

    /**
     * Create a new tutorial screen
     *
     * @param director the game's director class instance
     */
    public TutorialScreen(GigabitEconomy director) {
        this.director = director;
        this.stage = new Stage(director.getViewport());
    }

    /**
     * Show the game complete screen.
     * Called by LibGDX when setScreen() is called to this screen.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Import UI skin (commodore)
        Skin style = new Skin(Gdx.files.internal("ui_elements/ui_skin/uiskin.json"));

        informationScreenTable = new Table();
        informationScreenTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Label headerControls = new Label("Controls", style);
        Label ctrlMovement = new Label("Movement: ", style);
        TextField ctrlMovementBind = new TextField(" W/A/S/D [-or-] arrow-keys", style);
        ctrlMovementBind.setDisabled(true);
        Label ctrlAttack = new Label("Attack: ", style);
        TextField ctrlAttackBind = new TextField("space-bar", style);
        ctrlAttackBind.setDisabled(true);
        Label ctrlOpenParcel = new Label("Open Parcels: ", style);
        TextField ctrlOpenParcelBind = new TextField("tab", style);
        ctrlOpenParcelBind.setDisabled(true);
        Label ctrlParcelDelivery = new Label("Parcel collection & delivery: ", style);
        TextField ctrlParcelDeliveryBind = new TextField("space-bar", style);
        ctrlParcelDeliveryBind.setDisabled(true);
        Label ctrPauseGame = new Label("Pause Game: ", style);
        TextField ctrlPauseGameBind = new TextField("esc", style);
        ctrlPauseGameBind.setDisabled(true);

        Label headerDescription = new Label("Description", style);
        TextArea bodyDescriptions = new TextArea(DESCRIPTION, style);
        bodyDescriptions.setDisabled(true);

        String lastPlayedLevel = director.getLastPlayedLevel();
        TextButton screenTableReturnToLevelButton = new TextButton("RETURN TO LEVEL", style);
        screenTableReturnToLevelButton.setName(lastPlayedLevel);
        TextButton screenTableMainMenuButton = new TextButton("MAIN MENU", style);
        screenTableMainMenuButton.setName("MenuScreen");

        informationScreenTable = new Table();
        informationScreenTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        informationScreenTable.top();

        informationScreenTable.add(headerControls).pad(50, 0, 20, 0).center().colspan(2);
        informationScreenTable.row();

        informationScreenTable.add(ctrlMovement).left().spaceLeft(500);
        informationScreenTable.add(ctrlMovementBind).left().width(150);
        informationScreenTable.row();
        informationScreenTable.add(ctrlAttack).left().spaceLeft(500);
        informationScreenTable.add(ctrlAttackBind).left().width(150);
        informationScreenTable.row();
        informationScreenTable.add(ctrlOpenParcel).left().spaceLeft(500);
        informationScreenTable.add(ctrlOpenParcelBind).left().width(150);
        informationScreenTable.row();
        informationScreenTable.add(ctrlParcelDelivery).left().spaceLeft(500);
        informationScreenTable.add(ctrlParcelDeliveryBind).left().width(150);
        informationScreenTable.row();
        informationScreenTable.add(ctrPauseGame).left().spaceLeft(500);
        informationScreenTable.add(ctrlPauseGameBind).width(150);
        informationScreenTable.row();

        informationScreenTable.add(headerDescription).pad(50, 0, 20, 0).center().colspan(2);
        informationScreenTable.row();
        informationScreenTable.add(bodyDescriptions).width(630).height(300).colspan(2);
        informationScreenTable.row();
        informationScreenTable.row();
        if (lastPlayedLevel != null && screenTableReturnToLevelButton != null) {
            informationScreenTable.add(screenTableReturnToLevelButton).padTop(50).bottom().colspan(2);
        }
        else {
            informationScreenTable.add(screenTableMainMenuButton).padTop(50).bottom().colspan(2);
        }

        // Add click listener for buttons
        ClickListener buttonsListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String buttonName = event.getListenerActor().getName();

                // Switch to selected level screen via. director
                try {
                    director.switchScreen(buttonName);
                } catch (Exception ex) {
                    Gdx.app.error("Exception", String.format("Error switching screen to %s", buttonName), ex);
                    System.exit(-1);
                }
            }
        };
        screenTableReturnToLevelButton.addListener(buttonsListener);
        screenTableMainMenuButton.addListener(buttonsListener);

        stage.addActor(informationScreenTable);
    }

    /**
     * Render the game complete screen
     *
     * @param delta the time elapsed since the previous render (in seconds)
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        informationScreenTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Resize the window
     *
     * @param width the new screen width
     * @param height the new screen height
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Hide the screen.
     * Called by LibGDX when setScreen()'ed away from the screen.
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resume() {}

    @Override
    public void pause() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
