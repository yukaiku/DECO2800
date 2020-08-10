package deco2800.thomas.observers;

/**
 * Created by woody on 30-Jul-17.
 */
@FunctionalInterface
public interface KeyUpObserver {

	/**
	 * notifies the observer of a key being released
	 * @param keycode the key being released
     */
	void notifyKeyUp(int keycode);

}
