package deco2800.thomas.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.items.Item;
import org.junit.Assert;
import org.junit.Test;

public class AbstractDialogBoxTest extends BaseGDXTest {
	
	@Test
	public void checkShowing(){
		Item item = new Item("health-potion",50);
		AbstractDialogBox tester = new AbstractDialogBox(item,"Health Potion"
				,"tutorial");
		Assert.assertEquals(false, tester.isShowing());
	}
	
	@Test 
	public void checkShowingChanges(){
		Item item = new Item("health-potion",50);
		AbstractDialogBox tester = new AbstractDialogBox(item,"Health Potion"
				,"tutorial");
		tester.setShowing(true);
		Assert.assertEquals(true, tester.isShowing());
	}
	
	@Test
	public void checkVisibleTimeIs0(){
		Item item = new Item("health-potion",50);
		AbstractDialogBox tester = new AbstractDialogBox(item,"Health Potion"
				,"tutorial");
		Assert.assertEquals(0, tester.getVisibleTime());
	}
	
	@Test 
	public void checkRemove(){
		Item item = new Item("health-potion",50);
		AbstractDialogBox tester = new AbstractDialogBox(item,"Health Potion"
				,"tutorial");
		Assert.assertEquals(false, tester.getRemove());
	}
	
	@Test 
	public void checkgetEntity(){
		Item item = new Item("health-potion",50);
		AbstractDialogBox tester = new AbstractDialogBox(item,"Health Potion"
				,"tutorial");
		Assert.assertEquals(item, tester.getEntity());
	}
}
