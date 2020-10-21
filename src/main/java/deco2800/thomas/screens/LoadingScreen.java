package deco2800.thomas.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import deco2800.thomas.ThomasGame;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;

public class LoadingScreen implements Screen {

    final ThomasGame game;
    private final Stage stage;
    private final Label loadingText;

    public LoadingScreen(ThomasGame game) {
        this.game = game;
        stage = new Stage(new ExtendViewport(1920, 1080), game.getBatch());

        Texture titleTex = new Texture(Gdx.files.internal("resources/fonts/title.png"), true);
        titleTex.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        BitmapFont titleFont = new BitmapFont(Gdx.files.internal("resources/fonts/title.fnt"),
                new TextureRegion(titleTex), false);
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = titleFont;
        titleStyle.fontColor = Color.valueOf("#cccccc");
        Label loadingTitle = new Label("LOADING...", titleStyle);
        loadingTitle.setFontScale(1.3f);
        loadingTitle.setPosition(120, 140);

        Texture textTex = new Texture(Gdx.files.internal("resources/fonts/times.png"), true);
        textTex.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        BitmapFont textFont = new BitmapFont(Gdx.files.internal("resources/fonts/times.fnt"),
                new TextureRegion(textTex), false);
        Label.LabelStyle textStyle = new Label.LabelStyle();
        textStyle.font = textFont;
        textStyle.fontColor = Color.valueOf("#cccccc");
        loadingText = new Label("", textStyle);
        loadingText.setFontScale(0.9f);
        loadingText.setPosition(122, 120);

        stage.addActor(loadingTitle);
        stage.addActor(loadingText);
    }

    public void setLoadingText(String message) {
        loadingText.setText(message);
    }

    @Override
    public void show() {
        stage.getRoot().getColor().a = 0;
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.fadeIn(0.3f));
        sequenceAction.addAction(Actions.run(() -> {
            setLoadingText("Loading textures");
            game.loadMainMenuScreen();
        }));
        sequenceAction.addAction(Actions.run(() -> {
            setLoadingText("Loading enemies");
            GameManager.get().getManager(TextureManager.class).addEnemyTextures();
        }));
        sequenceAction.addAction(Actions.run(() -> {
            setLoadingText("Loading health");
            GameManager.get().getManager(TextureManager.class).addHealthTextures();
        }));
        sequenceAction.addAction(Actions.run(() -> {
            setLoadingText("Loading environment");
            GameManager.get().getManager(TextureManager.class).addEnvironmentTextures();
        }));
        sequenceAction.addAction(Actions.run(() -> setLoadingText("")));
        sequenceAction.addAction(Actions.fadeOut(0.2f));
        sequenceAction.addAction(Actions.run(game::setMainMenuScreen));
        stage.getRoot().addAction(sequenceAction);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        System.out.println("yo");
    }

    @Override
    public void resize(int width, int height) {
        // do nothing
    }

    @Override
    public void pause() {
        // do nothing
    }

    @Override
    public void resume() {
        // do nothing
    }

    @Override
    public void hide() {
        // do nothing
    }

    @Override
    public void dispose() {
        // do nothing
    }
}
