package deco2800.thomas.managers;

import deco2800.thomas.entities.CombatEntity;
import deco2800.thomas.util.BoundingBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all combat entities.
 */
public class CollisionManager extends TickableManager {
    /* Singleton reference to the instance of this manager */
    private static CollisionManager instance;
    /* All combat entities in game world are added to this list */
    private List<CombatEntity> entitiesInWorld;

    /**
     * Creates an instance of the CollisionManager class. This is private to prevent
     * misuse of the singleton instance.
     */
    private CollisionManager() {
        entitiesInWorld = new ArrayList<>();
    }

    /**
     * Gets the singleton instance of this manager, or creates one
     * if none exists.
     * @return reference to CollisionManager.
     */
    public static CollisionManager get() {
        if (instance == null) {
            instance = new CollisionManager();
        }

        return instance;
    }

    /**
     * Updates the spatial partitioning of collidable entities.
     * @param i
     */
    @Override
    public void onTick(long i) {
        // Currently this does nothing, however it is intended
        // that this be extended to update the spatial partitioning
        // when this is implemented.
    }

    /**
     * Gets an array list of all the entities contained within the given bounds.
     * @param bounds Bounding box to check within.
     * @return ArrayList of all entities within bounds.
     */
    public List<CombatEntity> getAllEntitiesInBounds(BoundingBox bounds) {
        List<CombatEntity> entitiesInBounds = new ArrayList<>();
        for (CombatEntity entity : entitiesInWorld) {
            if (boundingBoxesOverlap(bounds, entity.getBoundingBox())) {
                entitiesInBounds.add(entity);
            }
        }

        return entitiesInBounds;
    }

    /**
     * Returns whether two given bounding boxes overlap.
     * @param boxA First box to compare with.
     * @param boxB Second box to compare to.
     * @return True if boxes overlap, false otherwise.
     */
    private boolean boundingBoxesOverlap(BoundingBox boxA, BoundingBox boxB) {
        if (boxA.getLeft() > boxB.getRight() || boxA.getBottom() > boxB.getTop() ||
                boxA.getRight() < boxB.getLeft() || boxA.getTop() < boxB.getBottom()) {
            return false;
        }
        return true;
    }
}
