package deco2800.thomas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.items.Item;

public class ItemBox extends AbstractDialogBox {

	TextButton button;
	TextButton close;
	Item item;
	
	public ItemBox(Item item, String name, String price, String description, String styleType) {
		super(item, name, styleType);
		this.item = item;
		setup(description, price);
	}

	private void setup(String description, String price) {
		box.add(description).expand().center();
		box.row();
		box.add("Price:" + price);
		button = new TextButton("Buy", skin);
		close = new TextButton("Close",skin);
		close.addListener(b);
		button.addListener(a);
		button.pad(1,10,1,10);
		box.setKeepWithinStage(true);
		box.row();
		box.add(button).center();
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
			if (PlayerPeon.checkBalance() > 0) {
				System.out.print("Item Bought");
				// decrease currency.
				item.chargePlayer();
				ItemBox.super.setShowing(false);
				ItemBox.super.setRemove(true);
			}
		}
	};

	ChangeListener b = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			ItemBox.super.setShowing(false);
			box.remove();
		}
	};
	
}
