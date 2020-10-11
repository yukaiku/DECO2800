package deco2800.thomas.tasks.combat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.attacks.CombatEntity;
import deco2800.thomas.entities.attacks.PlayerFireball;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.util.BoundingBox;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class FireballAttackTaskTest extends BaseGDXTest {
    private GameManager gameManager;
    private CombatEntity combatEntity;
    private AbstractWorld abstractWorld;
    private Peon peon;

    @Before
    public void setup() {
        // Mock game manager
        gameManager = mock(GameManager.class);
        mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(gameManager);

        // Mock texture manager
        TextureManager textureManager = mock(TextureManager.class);
        Texture testTexture = mock(Texture.class);
        Array<TextureRegion> playerStand = new Array<>();
        playerStand.add(new TextureRegion(new Texture("resources/combat/fireball_left.png"), 262, 256));
        when(textureManager.getAnimationFrames(anyString())).thenReturn(playerStand);
        when(testTexture.getWidth()).thenReturn(10);
        when(testTexture.getHeight()).thenReturn(10);
        when(textureManager.getTexture(anyString())).thenReturn(testTexture);
        when(GameManager.getManagerFromInstance(TextureManager.class)).thenReturn(textureManager);

        // Mock combat entity and agent entity (so that they're on different factions)
        combatEntity = mock(CombatEntity.class);
        when(combatEntity.getFaction()).thenReturn(EntityFaction.EVIL);
        when(combatEntity.getBounds()).thenReturn(new BoundingBox(new SquareVector(0, 0), 10, 10));
        peon = mock(Peon.class);
        when(peon.getFaction()).thenReturn(EntityFaction.ALLY);

        // Mock abstract world
        abstractWorld = mock(AbstractWorld.class);
        when(GameManager.get().getWorld()).thenReturn(abstractWorld);
        when(abstractWorld.getEntitiesInBounds(any(BoundingBox.class)))
                .thenReturn(new ArrayList<>(Arrays.asList(combatEntity, peon)));
    }

    /**
     * Verifies that the fireball is spawned correctly.
     */
    @Test
    public void fireballSpawnTest() {
        FireballAttackTask task = new FireballAttackTask(combatEntity, 0,0,10,0.25f,10);
        task.spawn(0,0,10,10,10,0.25f,10,EntityFaction.EVIL);
        verify(abstractWorld, times(1)).addEntity(any(PlayerFireball.class));
    }
}