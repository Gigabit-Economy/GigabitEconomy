package com.mygdx.gigabiteconomy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gigabiteconomy.GigabitEconomy;

public class TutorialScreen implements Screen {
    private final static String DESCRIPTION = "You are a delivery driver aiming to do your round. Deliver to all the houses you can without falling victim to the dangers of suburbia. If worst comes to worst though you can always borrow what the content of your parcels. Just remember in the gig-economy the deliveries you make (and the ones you don't) affect the score your company gives you at the end of the day.";

    private GigabitEconomy director;

    private Stage stage;
    private Table informationScreenTable;

    public TutorialScreen(GigabitEconomy director) {
        this.director = director;
        this.stage = new Stage(director.getViewport());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Skin defined in UI skin (Commodore)
        Skin style = new Skin(Gdx.files.internal("uiskin.json"));

        informationScreenTable = new Table();
        informationScreenTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Label headerControls = new Label("Controls", style);
        Label ctrlMovement = new Label("Movement: ", style);
        TextField ctrlMovementBind = new TextField(" W/A/S/D [-or-] arrow-keys", style);
        Label ctrlAttack = new Label("Attack: ", style);
        TextField ctrlAttackBind = new TextField("space-bar", style);
        Label ctrlOpenParcel = new Label("Open Parcels: ", style);
        TextField ctrlOpenParcelBind = new TextField("tab", style);
        Label ctrlParcelDelivery = new Label("Parcel collection & delivery: ", style);
        TextField ctrlParcelDeliveryBind = new TextField("space-bar", style);
        Label ctrPauseGame = new Label("Pause Game: ", style);
        TextField ctrlPauseGameBind = new TextField("esc", style);

        Label headerDescription = new Label("Description", style);
        TextArea bodyDescriptions = new TextArea(DESCRIPTION, style);

        String lastPlayedLevel = director.getLastPlayedLevel();
        TextButton screenTableReturnToLevelButton = new TextButton("RETURN TO LEVEL", style);
        screenTableReturnToLevelButton.setName(lastPlayedLevel);
        TextButton screenTableMainMenuButton = new TextButton("MAIN MENU", style);
        screenTableMainMenuButton.setName("menu");

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
            informationScreenTable.add(screenTableReturnToLevelButton).padTop(50).bottom().colspan(1);
        }
        informationScreenTable.add(screenTableMainMenuButton).padTop(50).bottom().colspan(2);

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

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        informationScreenTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

    }

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
