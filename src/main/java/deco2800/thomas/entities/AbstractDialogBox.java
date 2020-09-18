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

	Object entity;
	Window box;
	boolean show; 
	int time; 
	Skin skin;
	
	public AbstractDialogBox (Object entity, String name, String styleType) {
		this.entity = entity;
		this.skin = new Skin(Gdx.files.internal("resources/uiskin.skin"));
		this.box = new Window(name, skin, styleType);
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
