package deco2800.thomas.observers;

/**
 * Created by woody on 30-Jul-17.
 */
public interface InputObserver {

	/**
	 * Notifies the observers of a KeyDown action
	 *
	 * This method is overridden in the class that implements InputObserver
	 *
	 * @param keycode the key that is pressed down
     */
	void notifyKeyDown(int keycode);

	/**
	 * Notifies the observers of a KeyUp action
	 *
	 * This method is overridden in the class that implements InputObserver
	 *
	 * @param keycode the key that is released
	 */
	void notifyKeyUp(int keycode);

	/**
	 * Notifies the observers of a key being typed action
	 *
	 * This method is overridden in the class that implements InputObserver
	 *
	 * @param character the character that is typed
	 */
	void notifyKeyTyped(char character);

	/**
	 * Notifies the observers of a mousing clicking down action
	 *
	 * This method is overridden in the class that implements InputObserver
	 *
	 * @param screenX the x coordinate that is clicked on the screen
	 * @param screenY the y coordinate that is clicked on the screen
	 * @param pointer
     * @param button the button that is touchedDown
     */
	void notifyTouchDown(int screenX, int screenY, int pointer, int button);

	/**
	 * Notifies the observers of a mousing buton releasing action
	 *
	 * This method is overridden in the class that implements InputObserver
	 *
	 * @param screenX the x coordinate that is clicked on the screen
	 * @param screenY the y coordinate that is clicked on the screen
	 * @param pointer
	 * @param button the button that is released
	 */
	void notifyTouchUp(int screenX, int screenY, int pointer, int button);

	/**
	 * Notifies the observers of a mousing dragging action
	 *
	 * This method is overridden in the class that implements InputObserver
	 *
	 * @param screenX the x coordinate that is dragged on the screen
	 * @param screenY the y coordinate that is dragged on the screen
	 * @param pointer
     */
	void notifyTouchDragged(int screenX, int screenY, int pointer);

	/**
	 * Notifies the observers of a mousing moving action
	 *
	 * This method is overridden in the class that implements InputObserver
	 *
	 * @param screenX the x coordinate that is hovered over on the screen
	 * @param screenY the y coordinate that is hovered on the screen
     */
	void notifyMouseMoved(int screenX, int screenY);

	/**
	 * Notifies the observers of a scrolling action
	 *
	 * This method is overridden in the class that implements InputObserver
	 *
	 * @param amount the amount that is scrolled
     */
	void notifyScrolled(int amount);

}
