package deco2800.thomas.managers;

public class CollisionManager extends TickableManager {
    /* Singleton reference to the instance of this manager */
    private static CollisionManager instance;

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
}
