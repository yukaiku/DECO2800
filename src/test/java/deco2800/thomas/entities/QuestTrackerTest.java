package deco2800.thomas.entities;

import com.badlogic.gdx.Input;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.entities.Agent.QuestTracker;
import deco2800.thomas.worlds.Tile;
import deco2800.thomas.worlds.volcano.VolcanoBurnTile;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test the QuestTracker Class
 */
public class QuestTrackerTest extends BaseGDXTest {


    /**
     * Test increase orb function
     */
    @Test
    public void testOrbFunctions() {
        QuestTracker q = new QuestTracker();
        assertEquals(QuestTracker.questTracker().size(),0);
        Tile t = new Tile("stone-1", 1, 1);
        Orb o = new Orb(t, "orb_1" );
        //Testing increase function
        assertEquals(QuestTracker.increaseOrbs(o),1);
        assertEquals(QuestTracker.increaseOrbs(o),2);
        assertEquals(QuestTracker.increaseOrbs(o),3);
        assertEquals(QuestTracker.increaseOrbs(o),4);
        //Testing to see if value will go over 4
        assertEquals(QuestTracker.increaseOrbs(o),4);
        //Testing the questTracker storing
        assertEquals(QuestTracker.questTracker().size(),4);
        //Testing decrease function
        assertEquals(QuestTracker.decreaseOrbs(),3);
        //Testing reset function
        assertEquals(QuestTracker.resetQuest(),0);
    }

}
