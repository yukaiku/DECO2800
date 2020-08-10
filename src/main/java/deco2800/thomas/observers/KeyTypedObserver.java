package deco2800.thomas.observers;

/**
 * Created by woody on 30-Jul-17.
 */
@FunctionalInterface
public interface KeyTypedObserver {

	/**
	 * Notifies the observer of the key that is pressed
	 * @param character the character of the key
     */
	void notifyKeyTyped(char character);
}
