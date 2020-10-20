package deco2800.thomas.worlds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.thomas.entities.AbstractEntity;

@SuppressWarnings("unused")
public class LoadGameWorld extends AbstractWorld {
	private final Logger logger = LoggerFactory.getLogger(LoadGameWorld.class);


	public LoadGameWorld() {
		super();
	}

	/**
	 * TODO Implement this function
	 */
	@Override
	protected void generateTiles() {
	}

	@Override
	public void onTick(long i) {
		super.onTick(i);
		for (AbstractEntity e : this.getEntities()) {
			e.onTick(0);
		}
	}

}
