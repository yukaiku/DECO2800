package deco2800.thomas.managers;

import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.attacks.Fireball;
import deco2800.thomas.entities.enemies.bosses.Dragon;
import deco2800.thomas.entities.enemies.monsters.Orc;
import deco2800.thomas.entities.environment.tundra.TundraCampfire;
import deco2800.thomas.entities.environment.tundra.TundraRock;
import deco2800.thomas.entities.environment.tundra.TundraTreeLog;
import deco2800.thomas.worlds.Tile;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * This class manages Entity types and Tile types for the
 * loadWorldFromJsonFile and the saveWorldToJsonFile methods of
 * the DatabaseManager class
 */
public class TypeManager extends AbstractManager {
	/**
	 * A map to query type based on the string representation of the type
	 */
	private Map<String, Type> map;

	public TypeManager() {
		map = new HashMap<>();

		// PlayerPeon
		addType(PlayerPeon.class);

		// Enemies
		addType(Orc.class);
		addType(Dragon.class);

		// Projectiles
		addType(Fireball.class);

		// TundraWorld entities
		addType(TundraTreeLog.class);
		addType(TundraRock.class);
		addType(TundraCampfire.class);

		// Tiles
		addType(Tile.class);
	}

	/**
	 * Add a type
	 * @param type
	 */
	private void addType(Type type) {
		map.put(type.toString(), type);
	}

	/**
	 * Get a type
	 * @param typeString the string representation of the type
	 * @return the corresponding type
	 */
	public Type getType(String typeString) {
		return map.get(typeString);
	}
}
