package deco2800.thomas.worlds.volcano;

import com.badlogic.gdx.Game;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.managers.GameManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GameManager.class)
public class VolcanoBurnTileTest extends BaseGDXTest {

    @Before
    public void setup() {
        
    }

    /**
     * Tests whether burn tile's damage attribute can be updated successfully.
     */
    @Test
    public void BurnTileDamageSetTest() {

        VolcanoBurnTile tile = new VolcanoBurnTile("BurnTileTest.png", 25, 25, 5f);
        tile.setTileDamage(20f);
        Assert.assertEquals(tile.getTileDamage(), 20f, 0f);


    }

    /**
     * Tests whether the correct float value is returned by the BurnTile's getter method
     *
     */
    @Test
    public void BurnTileDamageGetTest() {

        VolcanoBurnTile tile = new VolcanoBurnTile("BurnTileTest.png", 25, 25, 5f);
        Assert.assertEquals(tile.getTileDamage(), 5f, 0f);
    }

    public void BurnTileConstructorTest() {
        VolcanoBurnTile tile = new VolcanoBurnTile("BurnTileTest.png", 25f, 25f, 5f);
        Assert.assertEquals(tile.getCol(), 25f, 0f);
        Assert.assertEquals(tile.getRow(), 25f, 0f);
        Assert.assertEquals(tile.getTextureName(), "BurnTileTest.png");
    }
}