package deco2800.thomas.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import deco2800.thomas.GameScreen;
import deco2800.thomas.ThomasGame;
import deco2800.thomas.managers.GameManager;

public class LoadingScreen implements Screen {

    final ThomasGame game;
    private Stage stage;
    private Skin skin;
    Screen destScreen;
    Label logo;

    public LoadingScreen(ThomasGame game, final GameScreen.gameType startType) {
        this.game = game;
        destScreen = new GameScreen(game, startType);

        stage = new Stage(new ExtendViewport(1280, 720), game.batch);
        skin = GameManager.get().getSkin();

        logo = new Label("Loading...", skin);
        logo.setFontScale(1.5f);
        logo.setPosition(60, 60);
        stage.addActor(logo);
    }

    public LoadingScreen(ThomasGame game, Screen destScreen) {
        this.game = game;
        this.destScreen = destScreen;

        stage = new Stage(new ExtendViewport(1280, 720), game.batch);
        skin = GameManager.get().getSkin();

        logo = new Label("Loading...", skin);
        logo.setFontScale(1.5f);
        logo.setPosition(60, 60);
        stage.addActor(logo);
    }

    @Override
    public void show() {
        stage.getRoot().getColor().a = 0;
        SequenceAction sequenceAction = new SequenceAction();
        // will be parallel
        sequenceAction.addAction(Actions.fadeIn(0.3f));
        sequenceAction.addAction(Actions.run(() -> {
            // load textures here...

            logo.setText("Loading... 100%");
        }));
        sequenceAction.addAction(Actions.fadeOut(0.3f));
        sequenceAction.addAction(Actions.run(() -> game.setScreen(destScreen)));
        stage.getRoot().addAction(sequenceAction);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
//        this.dispose();
    }

    @Override
    public void dispose() {

    }
}
