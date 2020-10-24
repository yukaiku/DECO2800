package deco2800.thomas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.items.*;

import java.util.Random;

public class ItemBox extends AbstractDialogBox {

	TextButton button;
	TextButton close;
	Item item;

	/**
	 * Subclass of AbstractDialogBox for all items. 
	 * @param item Item to create a dialog box for. 
	 * @param name name of item 
	 * @param price price of item 
	 * @param description description of item to display in box. 
	 * @param styleType environment-dependent styleType of item 
	 */
	public ItemBox(Item item, String name, String price, String description, String styleType) {
		super(item, name, styleType);
		this.item = item;
		button = new TextButton("", skin);
		close = new TextButton("Close",skin);
		
		if (item.getClass() == Treasure.class){
			box.add("Open the box to retrieve a random amount of gold.").expand().center();
			box.row();
			button.setText("Open");
		} else {
			setup(description, price);
		}
		if (item.getClass() == HealthPotionSmall.class || item.getClass() == WoodenArmour.class
		|| item.getClass() == Poison.class){
			box.row();
			button.setText("Use");
		}
		button.addListener(primary);
		close.addListener(secondary);
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

	/**
	 * Returns the primary text button. Displays Open if Item is Treasure, 
	 * and Buy otherwise.
	 */
	public TextButton getButton(){
		return button; 
	}

	/**
	 * Returns the close button.
	 */
	public TextButton getCloseButton(){
		return close; 
	}

	/**
	 * Sets up Window and Button widgets with correct information. 
	 * @param description description of item 
	 * @param price price of item. 
	 */
	private void setup(String description, String price) {
		box.add(description).expand().center();
		box.row();
		box.add("Price:" + price);
		button.setText("Buy");
	}

	/**
	 * Returns the ChangeListener for button TextButton.
	 */
	public ChangeListener getA(){
		return primary;
	}

	/**
	 * Returns the ChangeListener for close TextButton. 
	 */
	public ChangeListener getB(){
		return secondary;
	}

	public Item getItem(){
		return this.item;
	}

	/**
	 * ChangeListener for the primary button which displays "Buy" or "Open"
	 */
	ChangeListener primary = new ChangeListener() {
		/**
		 * Opens the treasure box. If other type of item and player has 
		 * enough funds, buys item and removes from map. Hides ItemBox from 
		 * Game Screen. 
		 * @param event ChangeEvent of button being pushed
		 * @param actor button member variable, which was the button pushed. 
		 */
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			if ((PlayerPeon.checkBalance() > 0) && (PlayerPeon.checkBalance() >= item.getCurrencyValue())) {
				ItemBox.super.setShowing(false);
				ItemBox.super.setRemove(true);
				box.remove();
				item.chargePlayer();
			}
			if (item.getClass() == Treasure.class){
				ItemBox.super.setShowing(false);
				ItemBox.super.setRemove(true);
				box.remove();
				int randomCredit = new Random().nextInt(100-50) + 50;
				PlayerPeon.credit(randomCredit);
			}
		}
	};

	/**
	 * ChangeListener for the primary button which displays "Close"
	 */
	ChangeListener secondary = new ChangeListener() {
		/**
		 * Hides ItemBox because Close button has been pressed. 
		 * @param event ChangeEvent of button being pushed
		 * @param actor button member variable, which was the button pushed. 
		 */
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			ItemBox.super.setShowing(false);
			box.remove();
		}
	};
	
}
