package deco2800.thomas.entities;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.StringBuilder;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.items.Item;
import deco2800.thomas.entities.items.Treasure;
import deco2800.thomas.managers.TextureManager;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.worlds.Tile;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

public class ItemBoxTest extends BaseGDXTest {
	
	@Test
	public void ItemBoxSetUp(){
		Item item = new Item("health-potion",50);
		ItemBox tester = new ItemBox(item,"Health Potion"
				,"0","some description","volcano");
		StringBuilder string = new StringBuilder("Buy");
		StringBuilder string2 = new StringBuilder("Close");
		Assert.assertEquals(string, tester.getButton().getText());
		Assert.assertEquals(string2, tester.getCloseButton().getText());
	}

	// test set up of of a treasure box. 
	@Test 
	public void TestTreasureBox(){
		// use get texture manager and create tile. 
		TextureManager mockTM = new TextureManager();
		Tile tile = new Tile("treasure-box");
		PlayerPeon player = mock(PlayerPeon.class);
		Item item = new Treasure(tile,false, player, "volcano");
		ItemBox tester = new ItemBox(item,"Treasure Box"
				,"0","some description","volcano");
		StringBuilder string = new StringBuilder("Open");
		StringBuilder string2 = new StringBuilder("Close");
		Assert.assertEquals(string, tester.getButton().getText());
		Assert.assertEquals(string2, tester.getCloseButton().getText());
	}
	
	@Test 
	public void CloseBox(){
		Item item = new Item("health-potion",50);
		ItemBox tester = new ItemBox(item,"Health Potion"
				,"0","some description","volcano");
		ChangeListener.ChangeEvent event = new ChangeListener.ChangeEvent();
		tester.getB().changed(event, tester.getBox());
		Assert.assertFalse(tester.isShowing());
	}

	// check box is closed and player is deducted. 
	@Test 
	public void BuyBox(){
		//PlayerPeon player = mock(PlayerPeon.class);
		//Item item = new Item("health-potion",50);
		//ItemBox tester = new ItemBox(item,"Health Potion"
				//,"0","some description","volcano");
		//ChangeListener.ChangeEvent event = new ChangeListener.ChangeEvent();
		//tester.getA().changed(event, tester.getBox());
		//Assert.assertFalse(tester.isShowing());
		//Assert.assertTrue(tester.getRemove());
		// add deduction here. 
	}

	@Test
	public void CloseTreasureBox(){
		TextureManager mockTM = new TextureManager();
		Tile tile = new Tile("treasure-box");
		PlayerPeon player = mock(PlayerPeon.class);
		Item item = new Treasure(tile,false, player, "volcano");
		ItemBox tester = new ItemBox(item,"Treasure Box"
				,"0","some description","volcano");
		ChangeListener.ChangeEvent event = new ChangeListener.ChangeEvent();
		tester.getB().changed(event, tester.getBox());
		Assert.assertFalse(tester.isShowing());
	}

	@Test
	public void OpenTreasureBox(){
		// make sure player only has 0 money here --> can open with no money. 
		TextureManager mockTM = new TextureManager();
		Tile tile = new Tile("treasure-box");
		PlayerPeon player = mock(PlayerPeon.class);
		Item item = new Treasure(tile,false, player, "volcano");
		ItemBox tester = new ItemBox(item,"Treasure Box"
				,"0","some description","volcano");
		ChangeListener.ChangeEvent event = new ChangeListener.ChangeEvent();
		tester.getB().changed(event, tester.getBox());
		Assert.assertFalse(tester.isShowing());
		// add deduction here. 
	}
}
