package deco2800.thomas.entities.NPC;

import deco2800.thomas.util.SquareVector;

public class TutorialNPC extends NonPlayablePeon {
    public TutorialNPC(String name, SquareVector position) {
        super(name, position);
    }

    @Override
    public void interact() {
        // tutorial vibes
    }
}
