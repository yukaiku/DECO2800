package deco2800.thomas.entities;

import deco2800.thomas.worlds.Tile;

public class Rock extends StaticEntity implements HasHealth {
	public static final String ENTITY_ID_STRING = "rock";
	private int health = 100;

	public Rock() {
		this.setObjectName(ENTITY_ID_STRING);
	}

	public Rock(Tile tile, boolean obstructed) {
		super(tile, RenderConstants.ROCK_RENDER, "rock", obstructed);
		this.setObjectName(ENTITY_ID_STRING);
	}

	@Override
	public void onTick(long i) {

	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Rock{");
		sb.append("health=").append(health);
		sb.append(", children=").append(children);
		sb.append(", position=").append(position);
		sb.append('}');
		sb.append("\n");
		return sb.toString();
	}
}
