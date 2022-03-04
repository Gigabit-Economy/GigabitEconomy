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

public class PlotScreen implements Screen{

    private GigabitEconomy director;
    private Stage stage;
    private Table storyTable;
    private String titleText;
    private String bodyText;
    private String levelName;
    private boolean enableControl = false;

    /**
     * A constructor for adding the details into a plotscreen.
     * @param director  the instance of the game director
     */
    
    public PlotScreen(GigabitEconomy director) {
        this.director = director;
        this.stage = new Stage(director.getViewport());
        
        //default header
        titleText = "[ Header ]";
        bodyText = "[ Body ]";

    }
    /**
     * Add the title/header for the plot screen.
     * @param title  text that makes up the title for a level
     */
    public void addTitle(String title) {
        titleText = title;
    }
    /**
     * Add the title/header for the plot screen.
     * @param body  text that makes up the body for a level
     */
    public void addBody(String body) {
        bodyText = body;
    }
    /**
     * Button should be used to transition from the plotscreen to the actual leve
     * @param body  String name, used in directory, to transition to level screen
     */
    public void addButton(String nextLevelName) {
        levelName = nextLevelName;
    }


    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render(float delta) {
        
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        storyTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        
    }

    /**
     * Method to enable the controls to be loaded into the table
     * @param enable boolean for choosing to enable or disable controls being displayed on a plotscreen
     */
    public void addControls(boolean enable) {
        enableControl = enable;
    }
    
    private void insertControls(Table table, Skin style) {
        
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

        table.add(headerControls).center().top().pad(20);
        table.row();
        table.add(ctrlMovement).left();
        table.add(ctrlMovementBind);
        table.row();
        table.add(ctrlAttack).left();
        table.add(ctrlAttackBind);
        table.row();
        table.add(ctrlOpenParcel).left();
        table.add(ctrlOpenParcelBind);
        table.row();
        table.add(ctrlParcelDelivery).left();
        table.add(ctrlParcelDeliveryBind);
        table.row();
        table.add(ctrPauseGame).left();
        table.add(ctrlPauseGameBind);
        table.row();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Skin style = new Skin(Gdx.files.internal("ui_elements/ui_skin/uiskin.json"));

        storyTable = new Table();
        storyTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Label title = new Label(titleText, style);
        storyTable.add(title).top().padBottom(20);
        storyTable.row();

        TextArea body = new TextArea(bodyText, style);
        body.setDisabled(true);
        storyTable.add(body).width(Gdx.graphics.getWidth()-500).height(Gdx.graphics.getHeight()-700);
        storyTable.row();

        if(enableControl == true) {
            insertControls(storyTable, style);
        }

        TextButton toLevelButton = new TextButton("Continue", style);
        toLevelButton.setName(levelName);
        storyTable.add(toLevelButton).bottom().padTop(50);
        storyTable.row();

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
        toLevelButton.addListener(buttonsListener);

        stage.addActor(storyTable);


    }
    
}