package deco2800.thomas.entities.Agent;

import deco2800.thomas.entities.Orb;
import deco2800.thomas.managers.GameManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A QuestTracker class that extends PlayerPeon
 * Contains functions for tracking the quest progress from increasing to decreasing and resetting status
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/quest-tracker-counter
 */

public class QuestTracker extends PlayerPeon {

    //Orbs tracker
    private static int orbCount = 0;
    private static List<Orb> orbs = new ArrayList<Orb>();

    public QuestTracker(float row, float col, float speed, int health) {
        super(row, col, speed, health);
    }

    /**
     * Quest Tracker function that tracks the orbs the user currently has
     *
     * @return orbCount the number of orbs the user currently has
     */
    public static List<Orb> questTracker() {
        return orbs;
    }

    /**
     * Resets the number of orb user has
     * Notes:
     * To be used on when a new game is run or upon death
     */
    public static void resetQuest() {
        orbs.clear();
        orbCount = 0;
    }

    /**
     * Increase the number of orbs the user has
     * Notes:
     * To be used on when player picks up an orb
     */
    public static <Orb> void increaseOrbs(deco2800.thomas.entities.Orb orb) {
        if (orbCount < 5) {
            orbCount += 1;
            orbs.add(orb);
        }
        if(orbCount == 4){
            GameManager.get().state = GameManager.State.VICTORY;
        }
    }

    /**
     * Decrease the number of orbs the user has
     * Notes:
     * To be used on when player picks up an orb
     */
    public static void decreaseOrbs() {
        if (orbCount > 1) {
            orbCount -= 1;
            orbs.remove(orbCount);
        }
    }
}
