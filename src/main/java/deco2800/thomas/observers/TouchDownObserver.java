package deco2800.thomas.observers;

/**
 * Created by woody on 30-Jul-17.
 */
@FunctionalInterface
public interface TouchDownObserver {

	/**
	 * Notifies the observers of the mouse button being pushed down
	 * @param screenX the x position the mouse was pressed at
	 * @param screenY the y position the mouse was pressed at
	 * @param pointer
     * @param button the button which was pressed
     */
	void notifyTouchDown(int screenX, int screenY, int pointer, int button);


}
