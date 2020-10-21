package deco2800.thomas.screens;

import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import deco2800.thomas.ThomasGame;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import org.lwjgl.Sys;

import java.util.logging.ConsoleHandler;

public class LoadingScreen implements Screen {

    private static LoadingScreen instance = null;

    final ThomasGame game;
    private Stage stage;
    private Label loadingTitle;
    private Label loadingText;

    public LoadingScreen(ThomasGame game) {
        this.game = game;
        stage = new Stage(new ExtendViewport(1920, 1080), game.getBatch());

        Texture menuTex = new Texture(Gdx.files.internal("resources/fonts/title.png"), true);
        menuTex.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        BitmapFont menuFont = new BitmapFont(Gdx.files.internal("resources/fonts/title.fnt"),
                new TextureRegion(menuTex), false);
        Label.LabelStyle menuStyle = new Label.LabelStyle();
        menuStyle.font = menuFont;
        menuStyle.fontColor = Color.valueOf("#cccccc");

        Texture menuTex2 = new Texture(Gdx.files.internal("resources/fonts/times.png"), true);
        menuTex2.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        BitmapFont menuFont2 = new BitmapFont(Gdx.files.internal("resources/fonts/times.fnt"),
                new TextureRegion(menuTex2), false);
        Label.LabelStyle menuStyle2 = new Label.LabelStyle();
        menuStyle2.font = menuFont2;
        menuStyle2.fontColor = Color.valueOf("#cccccc");

        loadingTitle = new Label("LOADING...", menuStyle);
        loadingTitle.setFontScale(1.3f);
        loadingTitle.setPosition(120, 140);
        stage.addActor(loadingTitle);

        loadingText = new Label("", menuStyle2);
        loadingText.setFontScale(0.9f);
        loadingText.setPosition(122, 120);
        stage.addActor(loadingText);
    }

    public void setLoadingText(String message) {
        loadingText.setText(message);
    }

    @Override
    public void show() {
        stage.getRoot().getColor().a = 0;
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.fadeIn(0.4f));
        sequenceAction.addAction(Actions.run(() -> {
            game.loadMainMenuScreen();

        }));
        sequenceAction.addAction(Actions.run(() -> {
            setLoadingText("Loading environment");
            GameManager.get().getManager(TextureManager.class).addEnvironmentTextures();
        }));
        sequenceAction.addAction(Actions.run(() -> {
            setLoadingText("Loading animation");
            GameManager.get().getManager(TextureManager.class).addAnimationFrames();
//            stage.getRoot().getColor().a = 0;
        }));
        sequenceAction.addAction(Actions.fadeOut(0.5f));
        sequenceAction.addAction(Actions.run(() -> {
//            stage.getRoot().getColor().a = 1;
           game.setMainMenuScreen();
        }));
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
