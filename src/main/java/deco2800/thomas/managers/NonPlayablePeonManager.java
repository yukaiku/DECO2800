package deco2800.thomas.managers;

import deco2800.thomas.entities.NonPlayablePeon;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.util.SpawnList;
import deco2800.thomas.worlds.AbstractWorld;

public class NonPlayablePeonManager extends AbstractManager {

    private AbstractWorld world;
    private PlayerPeon player;
    private SpawnList<NonPlayablePeon> spawns;

    public NonPlayablePeonManager(AbstractWorld world, PlayerPeon player, SpawnList<NonPlayablePeon> spawnMap) {
        this.world = world;
        this.player = player;
        this.spawns = spawnMap;
        spawnAll();
    }

    /**
     * Iteratively initialise and spawn each NPC in the spawn map.
     */
    private void spawnAll() {
        spawns.forEach(this::spawnOne);
    }

    /**
     * Spawn an individual NPC with height 1.
     * @param npc The npc to spawn.
     */
    public void spawnOne(NonPlayablePeon npc) {
        spawnOne(npc, 1);
    }

    /**
     * Spawn an individual NPC.
     * TODO: Handle errors where the npc is being spawned out of the map.
     * @param npc The npc to spawn.
     * @param height The height of the world that the npc is spawn at.
     */
    public void spawnOne(NonPlayablePeon npc, int height) {
        npc.setPlayer(player);
        world.addEntity(npc);
    }

}
