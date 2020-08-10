package deco2800.thomas.worlds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import deco2800.thomas.entities.AbstractEntity;

@SuppressWarnings("unused")
public class LoadGameWorld extends AbstractWorld {
	private final Logger logger = LoggerFactory.getLogger(TestWorld.class);


	public LoadGameWorld() {
		super();
	}

	@Override
	protected void generateWorld() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTick(long i) {
		super.onTick(i);
		for (AbstractEntity e : this.getEntities()) {
			e.onTick(0);
		}
	}

}
