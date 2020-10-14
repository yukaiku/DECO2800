package deco2800.thomas.networking;

import deco2800.thomas.worlds.Tile;

public class TileUpdateMessage {
	private Tile tile;

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}
}
