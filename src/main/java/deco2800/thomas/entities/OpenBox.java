package deco2800.thomas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.thomas.entities.AbstractDialogBox;
import deco2800.thomas.entities.items.Item;

public class OpenBox extends AbstractDialogBox {

	Item item;
	TextButton open;
	TextButton close;
	
	public OpenBox(Item item, String name, String Styletype) {
		super(item, name, Styletype);
		this.item = item;
		box.add("Open the box!").expand().center();
		box.row();
		open = new TextButton("Open", skin);
		close = new TextButton("Close",skin);
		close.addListener(b);
		open.addListener(a);
		box.setKeepWithinStage(true);
		box.row();
		box.add(open).center();
		box.row();
		box.add(close).center();
		show = false;
		time = 0;
		box.pack();
		box.setPosition((Gdx.graphics.getWidth() - box.getWidth())/2,
				(Gdx.graphics.getHeight() - box.getHeight())/2 );
	}
	
	ChangeListener a = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			// increase currency.
			OpenBox.super.setShowing(false);
			OpenBox.super.setRemove(true);
		}
	};

	ChangeListener b = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			OpenBox.super.setShowing(false);
			box.remove();
		}
	};
}
