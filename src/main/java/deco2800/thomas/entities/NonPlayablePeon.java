package deco2800.thomas.entities;

import com.badlogic.gdx.Gdx;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.InputManager;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;

import java.util.Objects;

public class NonPlayablePeon extends Peon implements Interactable {

    private PlayerPeon player;
    private NonPlayablePeonType type;
    private String name;

    public NonPlayablePeon(NonPlayablePeonType type, String name, SquareVector position) {
        super(position.getRow(), position.getCol(), 0);
        this.setObjectName(String.format("%sNPCPeon", name));
        this.type = type;
        this.name = name;
        // Listen for touch events
        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
    }

    @Override
    public void interact() {
        SquareVector pos = new SquareVector(10, 30);
        this.player.moveTowards(pos);
    }

    public void setPlayer(PlayerPeon player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NonPlayablePeon)) return false;
        if (!super.equals(o)) return false;
        NonPlayablePeon that = (NonPlayablePeon) o;
        return Objects.equals(player, that.player) &&
                type == that.type &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), player, type, name);
    }

    @Override
    public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
        float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
        float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

        System.out.println(String.format("Current position of NPC: (%f, %f)", this.getCol(), this.getRow()));
        System.out.println(String.format("Click position: (%f, %f)", clickedPosition[0], clickedPosition[1]));

        boolean isCloseCol = clickedPosition[0] == this.getCol();
        boolean isCloseRow = clickedPosition[1] == this.getRow() ||
                             clickedPosition[1] == this.getRow() - 1 ||
                             clickedPosition[1] == this.getRow() + 1;
        if (isCloseCol && isCloseRow) {
            System.out.println("Interacting!");
            interact();
        }
    }
}
