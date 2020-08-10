package deco2800.thomas.managers;

import deco2800.thomas.GameScreen;

public class ScreenManager extends AbstractManager {

    /* Represents the current screen displayed in the game */
    private GameScreen currentScreen;

    /**
     * @return the current screen
     */
    public GameScreen getCurrentScreen() {
        return currentScreen;
    }

    /**
     * Sets the current screen
     * @param screen to set
     */
    public void setCurrentScreen(GameScreen screen) {
        currentScreen = screen;
    }
}
