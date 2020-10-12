package deco2800.thomas.tasks.combat;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.combat.DamageType;
import deco2800.thomas.entities.EntityFaction;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.attacks.CombatEntity;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.tasks.status.TornadoStatus;
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

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class ApplyTornadoOnCollisionTaskTest extends BaseGDXTest {

    private GameManager gameManager;
    private CombatEntity combatEntity;
    private Peon peon;
    private AbstractWorld abstractWorld;
    private ApplyTornadoOnCollisionTask task;

    @Before
    public void setUp() throws Exception {
        // Mock game manager
        gameManager = mock(GameManager.class);
        mockStatic(GameManager.class);
        when(GameManager.get()).thenReturn(gameManager);

        // Mock combat entity and agent entity (so that they're on different factions)
        combatEntity = mock(CombatEntity.class);
        when(combatEntity.getFaction()).thenReturn(EntityFaction.EVIL);
        when(combatEntity.getBounds()).thenReturn(new BoundingBox(new SquareVector(0, 0), 10, 10));
        peon = mock(PlayerPeon.class);
        when(peon.getFaction()).thenReturn(EntityFaction.ALLY);

        // Mock abstract world
        abstractWorld = mock(AbstractWorld.class);
        when(GameManager.get().getWorld()).thenReturn(abstractWorld);
        when(abstractWorld.getEntitiesInBounds(any(BoundingBox.class)))
                .thenReturn(new ArrayList<>(Arrays.asList(combatEntity, peon)));

        // Start task
        task = new ApplyTornadoOnCollisionTask(combatEntity, 1);
    }

    @Test
    public void applyingTornadoOnCollisionTest() {
        task.onTick(1);

        // Verify getDamage and reduceHealth are called
        verify(combatEntity).getDamage();
        verify(peon).applyDamage(anyInt(), any(DamageType.class));

        // Verify addEffect is called
        verify(peon).addEffect(any(TornadoStatus.class));

        // Verify state change
        assertTrue(task.isComplete());
    }

    @Test
    public void isAlive() {
    }

    @Test
    public void onTick() {
    }
}