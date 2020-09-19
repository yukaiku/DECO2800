package deco2800.thomas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.thomas.entities.agent.LoadedPeon;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.items.Item;
import deco2800.thomas.entities.items.Treasure;

public class ItemBox extends AbstractDialogBox {

	TextButton button;
	TextButton close;
	Item item;
	
	public ItemBox(Item item, String name, String price, String description, String styleType) {
		super(item, name, styleType);
		this.item = item;
		button = new TextButton("", skin);
		close = new TextButton("Close",skin);
		
		if (item.getClass() == Treasure.class){
			box.add("Open the box!").expand().center();
			box.row();
			button.setText("Open");
		}
		else{
			setup(description, price);
		}
		button.addListener(a);
		close.addListener(b);
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
	
	public TextButton getButton(){
		return button; 
	}
	
	public TextButton getCloseButton(){
		return close; 
	}
	
	private void setup(String description, String price) {
		box.add(description).expand().center();
		box.row();
		box.add("Price:" + price);
		button.setText("Buy");
	}
	
	ChangeListener a = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			System.out.print("Before statement\n");
			System.out.print(LoadedPeon.checkBalance());
			System.out.print(ItemBox.super.getEntity());
			System.out.print(ItemBox.super.isShowing());
			System.out.print(ItemBox.super.getRemove());
			if (PlayerPeon.checkBalance() < 0 || item.getClass() == Treasure.class ) {
				System.out.print("After statement\n");
				System.out.print(ItemBox.super.getEntity());
				if (ItemBox.super.getEntity() == null){
					System.out.print("item null");
				}
				System.out.print(ItemBox.super.isShowing());
				System.out.print(ItemBox.super.getRemove());
				ItemBox.super.setShowing(false);
				ItemBox.super.setRemove(true);
				box.remove();
				item.chargePlayer();
				System.out.print("end of statement \n");
			}
		}
	};
	
	public ChangeListener getA(){
		return a; 
	}
	
	public ChangeListener getB(){
		return b; 
	}

	ChangeListener b = new ChangeListener() {
		@Override
		public void changed(ChangeEvent event, Actor actor) {
			ItemBox.super.setShowing(false);
			box.remove();
		}
	};
	
}
