package com.mygdx.gigabiteconomy.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gigabiteconomy.GigabitEconomy;

public class MenuScreen implements Screen {
    private GigabitEconomy director;
    private Stage stage = new Stage(director.getViewport());

    public MenuScreen(GigabitEconomy director) {
        this.director = director;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        //Guessing this is one of the Scene2D.ui 'groups' which hold actors
        Table table = new Table();

        //Methods to errr make it work haha
        table.setFillParent(true);
        table.top();
        
        //Skin defined in UI skin (commodore - hopefully we can use, looks really cool)
        Skin style = new Skin(Gdx.files.internal("uiskin.json"));
        //Creating a new button is as easy as this
        TextButton play = new TextButton("Play", style);

        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Ask director for playing screen
                director.switchScreen("main");
            }
        });

        //Add it to the table, where you can reorganise it
        table.add(play);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //True since camera position with UI is rarely changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
