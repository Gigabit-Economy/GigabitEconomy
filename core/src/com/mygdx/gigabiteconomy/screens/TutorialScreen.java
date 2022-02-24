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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gigabiteconomy.GigabitEconomy;

public class TutorialScreen implements Screen {

    private GigabitEconomy director;
    private Stage stage;
    private Table informationScreenTable;

    public TutorialScreen(GigabitEconomy director) {
        this.director = director;
        this.stage = new Stage(director.getViewport());
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
        informationScreenTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        
    }

    private String DESCRIPTION = "You are a delivery driver aiming to do your round. Deliver to all the houses you can without falling victim to the dangers of suburbia. If worst comes to worst though you can always borrow what the content of your parcels. Just remember in the gig-economy the deliveries you make (and the ones you don't) affect the score your company gives you at the end of the day.";
    private String CONTROLS = "move using W/A/S/D or the arrow-keys\n attack with spacebar\n open parcels with tab\n open the pause menu with esc\n"; 
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        // Skin defined in UI skin (commodore - hopefully we can use, looks really cool)
        Skin style = new Skin(Gdx.files.internal("uiskin.json"));

        informationScreenTable = new Table();
        informationScreenTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Label headerControls = new Label("Controls", style);
        TextArea bodyControls = new TextArea(CONTROLS, style);
        Label headerDescription = new Label("Description", style);
        TextArea bodyDescriptions = new TextArea(DESCRIPTION, style);
    
        TextButton screenTableMainMenuButton = new TextButton("MAIN MENU", style);
        screenTableMainMenuButton.setName("menu");

        informationScreenTable = new Table();
        informationScreenTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        informationScreenTable.top();

        informationScreenTable.add(headerControls).pad(10);
        informationScreenTable.row();
        informationScreenTable.add(bodyControls).size(50).width(600).height(100);
        informationScreenTable.row();
        informationScreenTable.add(headerDescription).pad(10);
        informationScreenTable.row();
        informationScreenTable.add(bodyDescriptions).size(50).width(Gdx.graphics.getWidth()).height(500);
        informationScreenTable.row();
        informationScreenTable.add(screenTableMainMenuButton).bottom();
        
    
        
        // Add click listener for buttons
        ClickListener buttonsListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            String buttonName = event.getListenerActor().getName();
            System.out.println(buttonName);

            // Switch to selected level screen via. director
            try {
                director.switchScreen(buttonName);
            } catch (Exception ex) {
                Gdx.app.error("Exception", String.format("Error switching screen to %s", buttonName), ex);
                System.exit(-1);
            }
            }
        };
        screenTableMainMenuButton.addListener(buttonsListener);
        stage.addActor(informationScreenTable);
        
    }
    
}
