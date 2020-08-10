package deco2800.thomas.observers;

/**
 * Created by woody on 30-Jul-17.
 */
@FunctionalInterface
public interface MouseMovedObserver {

	/**
	 * Notifies the observer of the mouse being moved
	 * @param screenX the x position of the mouse
	 * @param screenY the y position of the mouse
     */
	void notifyMouseMoved(int screenX, int screenY);

}
