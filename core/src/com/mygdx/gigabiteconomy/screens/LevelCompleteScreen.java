package com.mygdx.gigabiteconomy.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gigabiteconomy.GigabitEconomy;
import com.mygdx.gigabiteconomy.ScoreSystem;

/**
 * Class that represents the LevelCompleteScreen which is shown right after successfully completing a level.
 * @param levelCompletedTable a table where all visual elements of the screen are stored
 * @param levelScores object used to access scores of last played level
 */
public class LevelCompleteScreen implements Screen {
    private GigabitEconomy director;

    private Stage stage;
    private Table levelCompletedTable;

    private ScoreSystem levelScores;

    public LevelCompleteScreen(GigabitEconomy director) {
        this.director = director;
        this.stage = new Stage(director.getViewport());
    }

    /**
     * @param congratulationsLabel a label which congratulates user on completing the level (1/2)
     * @param passedThisLevelLabel a label which congratulates user on completing the level (2/2)
     * @param yourScoreWasLabel a label which shows last score
     * @param highestScoreText a label which shows record score
     * @param lastFewScores an array where values of past scores are stored
     * @param levelCompletedScoreList list which shows past scores
     * @param levelCompletedMainMenuButton a button which allows user to go back to MenuScreen
     * @param levelCompletedNextLevelButton a button which allows user to proceed to the next level
     */
    @Override
    public void show() {
        this.levelScores = new ScoreSystem(director.getLastPlayedLevel());

        Gdx.input.setInputProcessor(stage);
    
        Skin style = new Skin(Gdx.files.internal("ui_elements/ui_skin/uiskin.json"));

        levelCompletedTable = new Table();
        levelCompletedTable.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Label congratulationsLabel = new Label("CONGRATULATIONS!", style);
        levelCompletedTable.add(congratulationsLabel).colspan(2);
        levelCompletedTable.row();

        Label passedThisLevelLabel = new Label("YOU HAVE COMPLETED THIS LEVEL!", style);
        levelCompletedTable.add(passedThisLevelLabel).colspan(2).padBottom(50);
        levelCompletedTable.row();

        Label yourScoreWasLabel = new Label("YOUR SCORE WAS: " + levelScores.getLatestScore(), style);
        levelCompletedTable.add(yourScoreWasLabel).colspan(2).pad(30);
        levelCompletedTable.row();

        String highestScoreText = String.format("HIGHEST SCORE: %d", levelScores.getHighestScore());
        Label yourHighestScoreLabel = new Label(highestScoreText, style);
        levelCompletedTable.add(yourHighestScoreLabel).colspan(2).padBottom(10);
        levelCompletedTable.row();

        Label yourPastScoresLabel = new Label("PREVIOUS SCORES:", style);
        levelCompletedTable.add(yourPastScoresLabel).colspan(2);
        levelCompletedTable.row();

        String[] prevScores = levelScores.getAllScores();
        String[] lastFewScores = {"0", "0", "0", "0", "0"};

        for (int i = 0; i < 5; i++) {
            lastFewScores[i] = prevScores [i];
        }
        List<String> levelCompletedScoreList = new List<String>(style);
        levelCompletedScoreList.setItems(lastFewScores);
        levelCompletedTable.add(levelCompletedScoreList).colspan(2);
        levelCompletedTable.row();

        TextButton levelCompletedMainMenuButton = new TextButton("MAIN MENU", style);
        levelCompletedMainMenuButton.setName("MenuScreen");
        levelCompletedTable.add(levelCompletedMainMenuButton).bottom().padTop(50);

        TextButton levelCompletedNextLevelButton = new TextButton("NEXT LEVEL", style);
        levelCompletedNextLevelButton.setName(director.getNextLevel());
        levelCompletedTable.add(levelCompletedNextLevelButton).bottom().padTop(50);
        levelCompletedTable.row();

        // Add click listener for buttons
        ClickListener buttonsListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String buttonName = event.getListenerActor().getName();

                try {
                    director.switchScreen(buttonName);
                } catch (Exception ex) {
                    Gdx.app.error("Exception", String.format("Error switching screen to %s", buttonName), ex);
                    System.exit(-1);
                }
            }
        };
        levelCompletedNextLevelButton.addListener(buttonsListener);
        levelCompletedMainMenuButton.addListener(buttonsListener);
        stage.addActor(levelCompletedTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();

        levelCompletedTable.setBounds(0, 0, Gdx.graphics.getWidth() + 180, Gdx.graphics.getHeight());
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
