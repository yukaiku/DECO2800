package deco2800.thomas.entities;

public class NonPlayablePlayerPeon extends Peon implements Interactable {

    private PlayerPeon player;

    @Override
    public void interact() {
        this.player.moveTowards(this.position);
    }

    public void setPlayer(PlayerPeon player) {
        this.player = player;
    }
}
