package deco2800.thomas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import deco2800.thomas.entities.items.*;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.worlds.Tile;

public class AbstractDialogBox {
	String name;
	Item item; 
	Window box;
	boolean show; 
	int time; 
	TextButton button;
	
	public AbstractDialogBox (Item item, String name, String price,
			String description, String styleType) {
		this.name = name;
		this.item = item;
		Skin skin2 = new Skin(Gdx.files.internal("resources/uiskin.skin"));
		this.box = new Window(item.getItemName(), skin2, styleType);
		box.isModal();
		box.add(description).expand().center();
		box.row();
		box.add("Price:" + price);
		button = new TextButton("Buy", skin2);
		button.addListener(a);
		button.pad(1,10,1,10);
		box.setKeepWithinStage(true);
		box.row();
		box.add(button).expand().center();
		show = false;
		time = 0; 
		box.pack();
	}
	
	ChangeListener a = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			System.out.print("Item Bought");
			// if bought - remove item and remove currency 
		}
	};
	
	public String getName() {
		return name;
	}
	
	public Item getItem() { 
		return item; 
	}
	
	// returns if you can see the dialog box or not. 
	public boolean isShowing(){
		return show; 
	}
	
	public void setShowing(boolean value){
		show = value;
	}
	
	public void setVisibleTime(int time){
		this.time = time + 1;
	}
	
	public int getVisibleTime(){
		return time; 
	}
	
	public Window getBox() {
		return box; 
	}
}
