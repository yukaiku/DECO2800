package deco2800.thomas.worlds;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@SuppressWarnings("unused")
public class LoadGameWorld extends AbstractWorld {
	private final Logger logger = LoggerFactory.getLogger(LoadGameWorld.class);
	private List<AbstractDialogBox> dialogues; 


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
	
	public List<AbstractDialogBox> returnAllDialogues(){
		return dialogues;
	};

}
