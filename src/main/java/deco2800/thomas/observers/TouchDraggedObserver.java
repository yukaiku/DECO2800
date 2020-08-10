package deco2800.thomas.observers;

/**
 * Created by woody on 30-Jul-17.
 */
@FunctionalInterface
public interface TouchDraggedObserver {

	/**
	 * Called when the mouse is pressed and dragged
	 * @param screenX the x position on the screen that the mouse is at
	 * @param screenY the y position on the screen that the mouse is at
	 * @param pointer
     */
	void notifyTouchDragged(int screenX, int screenY, int pointer);

}
