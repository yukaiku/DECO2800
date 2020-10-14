package deco2800.thomas.networking;

import deco2800.thomas.entities.AbstractEntity;

/**
 * Updates (or creates) a single entity in the game.
 */
public class SingleEntityUpdateMessage {
	private AbstractEntity entity;

	public AbstractEntity getEntity() {
		return entity;
	}

	public void setEntity(AbstractEntity entity) {
		this.entity = entity;
	}
}