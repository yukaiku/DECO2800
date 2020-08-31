package deco2800.thomas.worlds;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Server world acts as the husk of an actual world, controlled by a server connection.
 * <p>
 * Clients will not call onTick()
 */
public class ServerWorld extends AbstractWorld {

	public ServerWorld() {
		tiles = new CopyOnWriteArrayList<Tile>();
	}

	@Override
	protected void generateWorld() {

	}

	/**
	 * Do not tick entities in this onTick method, entities will be ticked by the server.
	 */
	@Override
	public void onTick(long i) {
		// TODO: Lerping etc
	}

	@Override
	protected void generateTiles() {
	}
}
