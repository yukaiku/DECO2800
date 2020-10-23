package deco2800.thomas.managers;

import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.npc.NonPlayablePeon;
import deco2800.thomas.worlds.AbstractWorld;

import java.util.List;

public class NonPlayablePeonManager extends AbstractManager {

    private AbstractWorld world;
    private PlayerPeon player;
    private List<NonPlayablePeon> allNpcs;

    public NonPlayablePeonManager(AbstractWorld world, PlayerPeon player, List<NonPlayablePeon> npcs) {
        this.world = world;
        this.player = player;
        this.allNpcs = npcs;
        spawnAll();
    }

    /**
     * Iteratively initialise and spawn each NPC in the spawn map.
     */
    private void spawnAll() {
        allNpcs.forEach(this::spawnOne);
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
        npc.setHeight(height);
        if (!allNpcs.contains(npc)) {
            allNpcs.add(npc);
        }
        world.addEntity(npc);
    }

    public List<NonPlayablePeon> getAllNpcs() {
        return allNpcs;
    }

}
