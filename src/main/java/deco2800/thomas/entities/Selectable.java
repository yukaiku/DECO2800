package deco2800.thomas.entities;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by timhadwen on 26/7/17.
 */
public interface Selectable {

	/**
	 * Returns if the object is selected
	 * @return true if the object is selected, false if not
     */
	boolean isSelected();

	/**
	 * Deselects an object
	 */
	void deselect();

	/**
	 * Getter for the button of the object
	 * @return the button of the object
     */
	Button getButton();

	/**
	 * Called when the button is pressed
	 */
	void buttonWasPressed();

	/**
	 * Gets the help text for the object
	 * @return the help text for the object
     */
	Label getHelpText();
}
