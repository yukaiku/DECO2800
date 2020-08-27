package deco2800.thomas;

/**
 * Allows Entities to be ticked at a constant rate allowing for AI and other tasks to occur.
 *
 * @author Tim Hadwen
 */
@FunctionalInterface
public interface Tickable {

	/**
	 * On tick is called periodically (time dependant on the world settings).
	 *
	 * @param tick Current game tick
	 */
	void onTick(long tick);
}
