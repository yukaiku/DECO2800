package deco2800.thomas.worlds;

import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.managers.DatabaseManager;

import java.util.List;

public class EnvTeamWorld extends AbstractWorld {
	public static final String SAVE_LOCATION_AND_FILE_NAME = "resources/environment/tundra/tundra-map.json";

	public EnvTeamWorld() {
		this(AbstractWorld.DEFAULT_WIDTH, AbstractWorld.DEFAULT_HEIGHT);
	}

	public EnvTeamWorld(int width, int height) {
		DatabaseManager.loadWorld(this, SAVE_LOCATION_AND_FILE_NAME);
//		System.out.println(this.entities);
		System.out.flush();
	}

	@Override
	protected void generateTiles() {
	}

	@Override
	public void onTick(long i) {
		super.onTick(i);
	}

	@Override
	public List<AbstractDialogBox> returnAllDialogues() {
		return null;
	}
}
