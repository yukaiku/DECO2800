package deco2800.thomas.networking;

import deco2800.thomas.entities.AbstractEntity;

import java.util.concurrent.CopyOnWriteArrayList;

@Deprecated
// Don't use this, should be SingleEntityUpdateMessage to avoid buffer overflow
public class EntityListMessage {
	private CopyOnWriteArrayList<AbstractEntity> entityList;

	public CopyOnWriteArrayList<AbstractEntity> getEntityList() {
		return entityList;
	}

	public void setEntityList(CopyOnWriteArrayList<AbstractEntity> entityList) {
		this.entityList = entityList;
	}
}
