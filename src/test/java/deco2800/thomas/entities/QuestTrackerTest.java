package deco2800.thomas.entities;

import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.QuestTracker;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.worlds.Tile;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test the QuestTracker Class
 */
public class QuestTrackerTest extends BaseGDXTest {


    /**
     * Test orb functions
     */
    @Test
    public void testOrbFunctions() {
        QuestTracker q = new QuestTracker();
        assertEquals(QuestTracker.orbTracker().size(),0);
        Tile t = new Tile("stone-1", 1, 1);
        Orb o = new Orb(t, "orb_1" );
        //Testing increase orb function
        assertEquals(q.increaseOrbs(o),1);
        assertEquals(q.increaseOrbs(o),2);
        assertEquals(q.increaseOrbs(o),3);
        assertEquals(GameManager.get().getState(),GameManager.State.RUN);
        assertEquals(q.increaseOrbs(o),4);
        //Testing to see if value will go over 4
        assertEquals(q.increaseOrbs(o),4);
        //Testing the orb storing
        assertEquals(q.orbTracker().size(),4);
        assertEquals(GameManager.get().getState(),GameManager.State.VICTORY);
        assertEquals(q.orbTracker().get(0).getTexture(),"orb_1");
        //Testing decrease orb function
        assertEquals(q.decreaseOrbs(),3);
        //Testing reset orb function
        assertEquals(q.resetOrbs(),0);
    }

}
