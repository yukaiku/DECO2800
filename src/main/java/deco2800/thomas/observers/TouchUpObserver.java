package deco2800.thomas.observers;

/**
 * Created by woody on 30-Jul-17.
 */
@FunctionalInterface
public interface TouchUpObserver {

	/**
	 * Called when the the mouse button is released and notifies the observers
 	 * @param screenX the x position on the screen that the mouse was released
	 * @param screenY the y position on the screen that the mouse was released
	 * @param pointer
	 * @param button the button that was released
     */
    void notifyTouchUp(int screenX, int screenY, int pointer, int button);

}
