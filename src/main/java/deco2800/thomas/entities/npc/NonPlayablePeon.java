package deco2800.thomas.entities.npc;

import com.badlogic.gdx.Gdx;
import deco2800.thomas.ThomasGame;
import deco2800.thomas.entities.Interactable;
import deco2800.thomas.entities.agent.Peon;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.InputManager;
import deco2800.thomas.screens.GameScreen;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.Tile;

public class NonPlayablePeon extends Peon implements Interactable {

    protected PlayerPeon player;
    protected String name;
    private boolean hasFinishedSetup;

    public NonPlayablePeon(String name, SquareVector position, String texture) {
        super();
        this.setPosition(position.getCol(), position.getRow(), 1);
        this.name = name;
        setSave(false); // Don't save NPC's as they're loaded automatically.
        this.setTexture(texture);
        setup();
    }

    private void setup() {
        this.setObjectName("npcPeon");
        hasFinishedSetup = false;
        this.setSpeed(0f);
        // Listen for touch events
        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
    }

    /**
     * Obstruct the base (feet) of an NPC. This stops the player from trampling NPCs.
     * This is run after the world has been added to the GameManager, otherwise a NullPointerException
     * would be thrown.
     * @see GameScreen#GameScreen(ThomasGame, GameScreen.gameType)
     */
    private void setObstructions() {
        try {
            Tile feet = GameManager.get().getWorld().getTile(this.position.getCol(), this.position.getRow());
            feet.setObstructed(true);
            GameManager.get().getWorld().updateTile(feet);

            hasFinishedSetup = true;
        } catch (NullPointerException e) {
            // do nothing, it'll work eventually.
        }
    }

    @Override
    public void onTick(long i) {
        // Wait for GameManager to have a world before adding obstructive tiles above.
        if (GameManager.get().getWorld() != null && !hasFinishedSetup) {
            setObstructions();
        }
    }

    @Override
    public void interact() {
        //interaction with npc goes here
    }

    public String getName() {
        return name;
    }

    public void setPlayer(PlayerPeon player) {
        this.player = player;
    }

    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

        // Test if the
        boolean isCloseCol = clickedPosition[0] == this.getCol();
        boolean isCloseRow = clickedPosition[1] == this.getRow();

        if (isCloseCol && isCloseRow){
            if (this.player.getPosition().isCloseEnoughToBeTheSame(this.getPosition(), 2.0f)){
                interact();
            }
        }
    }
}
