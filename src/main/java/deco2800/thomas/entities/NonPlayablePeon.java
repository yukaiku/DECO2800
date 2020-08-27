package deco2800.thomas.entities;

import java.util.Objects;

public class NonPlayablePeon extends Peon implements Interactable {

    private PlayerPeon player;
    private NonPlayablePeonType type;
    private String name;

    public NonPlayablePeon(NonPlayablePeonType type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public void interact() {
        this.player.moveTowards(this.position);
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
}
