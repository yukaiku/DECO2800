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
import deco2800.thomas.managers.SoundManager;
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
        loadingTitle.setFontScale(1.4f);
        loadingTitle.setPosition(120, 150);

        Texture textTex = new Texture(Gdx.files.internal("resources/fonts/times.png"), true);
        textTex.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        BitmapFont textFont = new BitmapFont(Gdx.files.internal("resources/fonts/times.fnt"),
                new TextureRegion(textTex), false);
        Label.LabelStyle textStyle = new Label.LabelStyle();
        textStyle.font = textFont;
        textStyle.fontColor = Color.valueOf("#cccccc");
        loadingText = new Label("", textStyle);
        loadingText.setFontScale(1f);
        loadingText.setPosition(122, 120);

        stage.addActor(loadingTitle);
        stage.addActor(loadingText);
    }

    public void setLoadingText(String message) {
        loadingText.setText(message);
    }

    /**
     * Load game resources.
     */
    @Override
    public void show() {
        TextureManager textureManager = GameManager.get().getManager(TextureManager.class);
        SoundManager soundManager = GameManager.get().getManager(SoundManager.class);
        stage.getRoot().getColor().a = 0;
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.fadeIn(0.3f));
        sequenceAction.addAction(Actions.run(() -> setLoadingText("Loading Game")));
        sequenceAction.addAction(Actions.run(game::loadMainMenuScreen));

        sequenceAction.addAction(Actions.run(() -> setLoadingText("Loading Player 1/9")));
        sequenceAction.addAction(Actions.run(textureManager::loadBaseTextures));
        sequenceAction.addAction(Actions.run(textureManager::loadInventoryTextures));

        sequenceAction.addAction(Actions.run(() -> setLoadingText("Loading Enemies 2/9")));
        sequenceAction.addAction(Actions.run(textureManager::loadEnemyTextures));

        sequenceAction.addAction(Actions.run(() -> setLoadingText("Loading Combats 3/9")));
        sequenceAction.addAction(Actions.run(textureManager::loadCombatTextures));

        sequenceAction.addAction(Actions.run(() -> setLoadingText("Loading Storyline 4/9")));
        sequenceAction.addAction(Actions.run(textureManager::loadStorylineTextures));

        sequenceAction.addAction(Actions.run(() -> setLoadingText("Loading NPC 5/9")));
        sequenceAction.addAction(Actions.run(textureManager::loadNPCTextures));

        sequenceAction.addAction(Actions.run(() -> setLoadingText("Loading Health 6/9")));
        sequenceAction.addAction(Actions.run(textureManager::loadHealthTextures));

        sequenceAction.addAction(Actions.run(() -> setLoadingText("Loading Environment 7/9")));
        sequenceAction.addAction(Actions.run(textureManager::loadEnvironmentTextures));
        sequenceAction.addAction(Actions.run(textureManager::loadMinimapTextures));

        sequenceAction.addAction(Actions.run(() -> setLoadingText("Loading Animation 8/9")));
        sequenceAction.addAction(Actions.run(textureManager::loadAnimationFrames));

        sequenceAction.addAction(Actions.run(() -> setLoadingText("Loading Sounds & Music 9/9")));
        sequenceAction.addAction(Actions.run(soundManager::loadSound));

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
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
