package deco2800.thomas.entities.Agent;

import deco2800.thomas.entities.Orb;
import deco2800.thomas.managers.GameManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A QuestTracker class
 * Contains functions for tracking the quest progress from increasing to decreasing and resetting status
 *
 * Wiki: https://gitlab.com/uqdeco2800/2020-studio-2/2020-studio2-henry/-/wikis/quest-tracker-counter
 */

public class QuestTracker{

    //Orbs tracker
    private static List<Orb> orbs = new ArrayList<Orb>();

    public QuestTracker() {
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
     * @return returns number of orbs in orbs list after reset
     */
    public static int resetQuest() {
        orbs.clear();
        return orbs.size();
    }

    /**
     * Increase the number of orbs the user has
     * Notes:
     * To be used on when player picks up an orb
     * @return returns the number of orbs currently in orbs list
     */
    public static <Orb> int increaseOrbs(deco2800.thomas.entities.Orb orb) {
        if (orbs.size() < 4) {
            orbs.add(orb);
        }
        if(orbs.size() == 4){
            GameManager.get().state = GameManager.State.VICTORY;
        }
        return orbs.size();
    }

    /**
     * Decrease the number of orbs the user has
     * Notes:
     * To be used on when player picks up an orb
     * @return return the number of orbs currently in orbs list
     */
    public static int decreaseOrbs() {
        if (orbs.size() != 0) {
            orbs.remove(orbs.size()-1);
        }
        return orbs.size();
    }
}
