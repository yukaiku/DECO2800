package deco2800.thomas.entities;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import deco2800.thomas.entities.items.*;
import deco2800.thomas.managers.GameManager;

public class AbstractDialogBox {
	String name; 
	String description;
	String price;
	Item item; 
	Window box;
	boolean show; 
	
	public AbstractDialogBox (Item item, String name, String description,
			String price) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.item = item;
		this.box = new Window("Item", GameManager.get().getSkin());
		show = false;
		// all the information to add into the window.
		// item object also added to access image etc. 
		
	}
	
	public String getName() {
		return name;
	}
	
	// returns if you can see the dialog box or not. 
	public boolean getVisible(){
		return show; 
	}
	
	public Window getBox() {
		return box; 
	}
}
