package deco2800.thomas.renderers.components;

import deco2800.thomas.Tickable;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This component renders floating damage numbers above the heads of
 * entities that have been hit by damage, or received a heal.
 */
public class FloatingDamageComponent implements Tickable {
    /* List of currently active floating damage instances. */
    private CopyOnWriteArrayList<FloatingDamageText> floatingDamageInstances;
    private CopyOnWriteArrayList<FloatingDamageText> floatingDamageInstancesToRemove;

    /**
     * Updates all floating damage text instances currently managed.
     * @param i Ticks since game start.
     */
    public void onTick(long i) {
        // Update all instances in list
        for (FloatingDamageText text : floatingDamageInstances) {
            text.onTick(i);
            if (text.getLife() <= 0) {
                remove(text);
            }
        }

        // Remove any instances queued for removal
        for (FloatingDamageText text : floatingDamageInstancesToRemove) {
            floatingDamageInstances.remove(text);
        }
        floatingDamageInstancesToRemove.clear();
    }

    /**
     * Queues a floating damage text instance to be removed from the list of
     * currently managed instances.
     * @param text FloatingDamageText to remove.
     */
    public void remove(FloatingDamageText text) {
        floatingDamageInstancesToRemove.add(text);
    }
}
