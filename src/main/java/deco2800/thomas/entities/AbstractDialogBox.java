package deco2800.thomas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class AbstractDialogBox {
	Object entity;
	Window box;
	Skin skin; 
	boolean show; 
	int time;
	boolean remove;

	/**
	 * Constructs AbstractDialogBox to display information about an entity
	 * @param entity Entity to display information about. 
	 * @param name Name of entity to display. 
	 * @param styleType Style type for window. Dependent upon environment. 
	 */
	public AbstractDialogBox(Object entity, String name, String styleType) {
		this.entity = entity;
		this.skin = new Skin(Gdx.files.internal("resources/uiskin.skin"));
		this.box = new Window(name, skin, styleType);
		this.remove = false;
	}

	/**
	 * Returns true if the AbstractDialogBox is displayed on Game Screen. 
	 */
	// returns if you can see the dialog box or not. 
	public boolean isShowing(){
		return show; 
	}

	/**
	 * Sets whether AbstractDialogBox is displayed on GameScreen or not. 
	 * @param value true if displayed, false if not. 
	 */
	public void setShowing(boolean value){
		show = value;
	}

	/**
	 * Increases count of how long AbstractDialogBox has been displayed on 
	 * GameScreen. 
	 * @param time value to add to current time. 
	 */
	public void setVisibleTime(int time){
		this.time++;
	}

	/**
	 * Returns how long AbstractDialogbox has been displayed on GameScreen.
	 */
	public int getVisibleTime(){
		return time; 
	}

	/**
	 * Returns Window widget. 
	 */
	public Window getBox() {
		return box;
	}

	/**
	 * Sets boolean value checking whether to remove entity or not. 
	 * If true, entity is removed.
	 */
	public void setRemove(boolean rem){
		this.remove = rem; 
	}

	/**
	 * Returns remove value. If true, entity to be removed from GameScreen.
	 */
	public boolean getRemove(){
		return remove;
	}

	/**
	 * Returns the entity (Item or NPC) connected to this AbstractDialogBox. 
	 */
	public Object getEntity(){
		return entity;
	}
}
