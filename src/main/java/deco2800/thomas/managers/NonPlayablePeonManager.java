package deco2800.thomas.managers;

import deco2800.thomas.entities.NonPlayablePeon;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.util.SpawnMap;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.Map;

public class NonPlayablePeonManager {

    private AbstractWorld world;
    private PlayerPeon player;
    private SpawnMap<NonPlayablePeon> spawnMap;

    public NonPlayablePeonManager(AbstractWorld world, PlayerPeon player, SpawnMap<NonPlayablePeon> spawnMap) {
        this.world = world;
        this.player = player;
        this.spawnMap = spawnMap;
    }

    /**
     * Iteratively initialise and spawn each NPC in the spawn map.
     */
    private void spawnAll() {
        for (Map.Entry<NonPlayablePeon, SquareVector> entry : spawnMap.entrySet()) {
            NonPlayablePeon npc = entry.getKey();
            SquareVector position = entry.getValue();
            spawnOne(npc, position);
        }
    }

    /**
     * Spawn an individual NPC with height 1.
     * @param npc The npc to spawn.
     * @param position The square vector to spawn the npc at.
     */
    public void spawnOne(NonPlayablePeon npc, SquareVector position) {
        spawnOne(npc, position, 1);
    }

    /**
     * Spawn an individual NPC.
     * TODO: Handle errors where the npc is being spawned out of the map.
     * @param npc The npc to spawn.
     * @param position The square vector to spawn the npc at.
     * @param height The height of the world that the npc is spawn at.
     */
    public void spawnOne(NonPlayablePeon npc, SquareVector position, int height) {
        npc.setPlayer(player);
        npc.setPosition(position.getCol(), position.getRow(), height);

        world.addEntity(npc);
    }

}
