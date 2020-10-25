package deco2800.thomas.renderers.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import deco2800.thomas.combat.skills.AbstractSkill;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.renderers.OverlayComponent;
import deco2800.thomas.renderers.OverlayRenderer;

import java.util.List;

/**
 * A component in overlay renderer to display the skill hotbar for player
 */
public class HotbarComponent extends OverlayComponent {
    private Sprite wizardIcon;
    private Sprite knightIcon;
    private Sprite activeSelector;
    private Sprite lastRenderedSprite = null;
    private PlayerPeon playerEntity;
    private ShapeRenderer coolDownBox;

    public HotbarComponent(OverlayRenderer overlayRenderer) {
        super(overlayRenderer);
        wizardIcon = new Sprite(GameManager.get().getManager(TextureManager.class).getTexture("wizard_icon"));
        knightIcon = new Sprite(GameManager.get().getManager(TextureManager.class).getTexture("knight_icon"));
        activeSelector = new Sprite(GameManager.get().getManager(TextureManager.class).getTexture("active_selector"));
        coolDownBox = new ShapeRenderer();
    }

    /**
     * Render each detail for the hot bar step by step.
     * 1. Render wizardIcon
     * 2. Render each skill in wizard skill list
     * 3. Render knight icon
     * 4. Render the mech ultimate skill
     *
     * @param batch the sprite batch to draw into
     */
    @Override
    public void render(SpriteBatch batch) {
        this.playerEntity = (PlayerPeon) GameManager.get().getWorld().getPlayerEntity();

        batch.begin();

        wizardIcon.setPosition(overlayRenderer.getX() + 0.15f * overlayRenderer.getWidth(), overlayRenderer.getY());
        wizardIcon.draw(batch);
        lastRenderedSprite = wizardIcon;

        this.renderSkills(batch, this.playerEntity.getWizardSkills());

        knightIcon.setPosition(lastRenderedSprite.getX() + lastRenderedSprite.getWidth() + 0.05f * overlayRenderer.getWidth(), lastRenderedSprite.getY());
        knightIcon.draw(batch);
        lastRenderedSprite = knightIcon;

        this.renderMechSkill(batch, this.playerEntity.getMechSkill());

        batch.end();
    }

    /**
     * Loop through the skill list and render each skill holder (skill box), the skill it self
     * as well as the active border for the active one. Currently, we only render the first 5 skill
     * , if we have more than 5 skill the system will need to be changed.
     *
     * @param batch  the sprite batch to draw into
     * @param skills the skill list to loop
     */
    private void renderSkills(SpriteBatch batch, List<AbstractSkill> skills) {
        for (int i = 0; i < 5; i++) {
            Sprite skillHolder = new Sprite(GameManager.get().getManager(TextureManager.class).getTexture("knight_hotbar"));
            skillHolder.setPosition(lastRenderedSprite.getX() + lastRenderedSprite.getWidth(), lastRenderedSprite.getY());
            skillHolder.draw(batch);
            if (i < skills.size()) {
                renderSkill(batch, skills.get(i), skillHolder);
            }
            if (i == playerEntity.getActiveWizardSkill()) {
                activeSelector.setPosition(lastRenderedSprite.getX() + lastRenderedSprite.getWidth(), lastRenderedSprite.getY());
                activeSelector.draw(batch);
            }
            lastRenderedSprite = skillHolder;
        }
    }

    /**
     * Get the skill texture and render it in to the skill holder box. Also check
     * the cooldown to render cooldown box.
     *
     * @param batch       the sprite batch to draw into
     * @param skill       the skill we want to render
     * @param skillHolder the skill holder where the skill will be put
     */
    private void renderSkill(SpriteBatch batch, AbstractSkill skill, Sprite skillHolder) {
        float skillIconWidth = 0.7f * skillHolder.getWidth();
        float skillIconHeight = 0.7f * skillHolder.getHeight();
        float skillIconX = skillHolder.getX() + 0.5f * skillHolder.getWidth() - 0.5f * skillIconWidth;
        float skillIconY = skillHolder.getY() + 0.5f * skillHolder.getHeight() - 0.5f * skillIconHeight;

        Sprite skillIcon = new Sprite(GameManager.get().getManager(TextureManager.class).getTexture(skill.getTexture()));
        skillIcon.setSize(skillIconWidth, skillIconHeight);
        skillIcon.setPosition(skillIconX, skillIconY);
        skillIcon.draw(batch);

        if (skill.getCooldownRemaining() > 0) {
            batch.end();
            renderCoolDownBox(batch, skill, skillIconX, skillIconY, skillIcon.getWidth(), skillIcon.getHeight());
            batch.begin();
        }
    }

    /**
     * Render a black box to cover the skill when the skill are
     * cooling down. The height of the box will be reduced based on the remaining
     * cool down time
     *
     * @param batch     the batch to get the projection matrix
     * @param skill     the target skill
     * @param x         the x position for the black box
     * @param y         the y position for the black box
     * @param width     width of the black box
     * @param maxHeight the maximum height of the black box
     */
    private void renderCoolDownBox(SpriteBatch batch, AbstractSkill skill, float x, float y, float width, float maxHeight) {
        float height = (float) skill.getCooldownRemaining() / skill.getCooldownMax() * maxHeight;

        coolDownBox.setProjectionMatrix(batch.getProjectionMatrix());
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        coolDownBox.begin(ShapeRenderer.ShapeType.Filled);
        coolDownBox.setColor(new Color(0, 0, 0, 0.7f));
        coolDownBox.rect(x, y, width, height);
        coolDownBox.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    /**
     * Render the mech ultimate skill
     *
     * @param batch the sprite batch to draw into
     * @param skill the ultimate skill to render
     */
    private void renderMechSkill(SpriteBatch batch, AbstractSkill skill) {
        if (skill != null) {
            Sprite skillHolder = new Sprite(GameManager.get().getManager(TextureManager.class).getTexture("knight_hotbar"));
            skillHolder.setPosition(lastRenderedSprite.getX() + lastRenderedSprite.getWidth(), lastRenderedSprite.getY());
            skillHolder.draw(batch);
            renderSkill(batch, skill, skillHolder);
            lastRenderedSprite = skillHolder;
        }
    }
}
