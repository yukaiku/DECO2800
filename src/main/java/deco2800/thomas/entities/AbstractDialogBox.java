package deco2800.thomas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class AbstractDialogBox {

	Object entity;
	Window box;
	boolean show; 
	int time; 
	Skin skin;
	boolean remove; 
	
	public AbstractDialogBox (Object entity, String name, String styleType) {
		this.entity = entity;
		this.skin = new Skin(Gdx.files.internal("resources/uiskin.skin"));
		this.box = new Window(name, skin, styleType);
		this.remove = false;
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
	
	public void setRemove(boolean rem){
		remove = rem; 
	}
	
	public boolean getRemove(){
		return remove;
	}
	
	public Object getEntity(){
		return entity;
	}
}
