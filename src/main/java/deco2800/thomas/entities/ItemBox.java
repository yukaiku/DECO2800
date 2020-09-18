package deco2800.thomas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.thomas.entities.items.Item;

public class ItemBox extends AbstractDialogBox{

	TextButton button;
	
	public ItemBox(Item item, String name, String price, String description, String styleType) {
		super(item, name, styleType);
		box.add(description).expand().center();
		box.row();
		box.add("Price:" + price);
		button = new TextButton("Buy", skin);
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
			ItemBox.super.setShowing(false);
		}
	};
	
}
