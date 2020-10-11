package deco2800.thomas.entities.agent;

import deco2800.thomas.entities.Orb;
import deco2800.thomas.managers.GameManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A QuestTracker class
 * Contains functions for tracking the quest progress
 * An example would be tracking of the overall completion of the games(orbs)
 * More quest can be inserted in here and a quest tracker popup can be used to display the quests status of misc quest.
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/quest-tracker-counter
 */

public class QuestTracker{
    //Orbs tracker
    private static List<Orb> orbs = new ArrayList<>();

    /**
     * Orb Tracker function that tracks the orbs the user currently has
     *
     * @return orbCount the number of orbs the user currently has
     */
    public static List<Orb> orbTracker() {
        return orbs;
    }

    /**
     * Resets the number of orb user has
     * Notes:
     * To be used on when a new game is run or upon death
     * @return returns number of orbs in orbs list after reset
     */
    public static int resetOrbs() {
        orbs.clear();
        return orbs.size();
    }

    /**
     * Increase the number of orbs the user has
     * Notes:
     * To be used on when player picks up an orb
     * @return returns the number of orbs currently in orbs list
     */
    public static int increaseOrbs(deco2800.thomas.entities.Orb orb) {
        int orbCount = orbs.size();
        if (orbCount < 4) {
            orbs.add(orb);
            orbCount = orbs.size();
        }
        if(orbCount == 4){
            GameManager.get().victory();
        }
        return orbCount;
    }

    /**
     * Decrease the number of orbs the user has
     * Notes:
     * To be used on when player picks up an orb
     * @return return the number of orbs currently in orbs list
     */
    public static int decreaseOrbs() {
        if (!orbs.isEmpty()) {
            orbs.remove(orbs.size()-1);
        }
        return orbs.size();
    }
}
