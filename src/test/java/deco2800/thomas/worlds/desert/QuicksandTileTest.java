package deco2800.thomas.worlds.desert;

import deco2800.thomas.BaseGDXTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QuicksandTileTest extends BaseGDXTest {

    QuicksandTile quicksand1;
    QuicksandTile quicksand2;
    QuicksandTile quicksand3;


    @Before
    public void setUp() throws Exception {
        quicksand1 = new QuicksandTile();
        quicksand2 = new QuicksandTile("desert_1");
        quicksand3 = new QuicksandTile("desert_2", 1f, 2f);
    }

    @Test
    public void getTypeNullConstructor() {
        Assert.assertEquals("Quicksand", quicksand1.getType());
    }

    @Test
    public void getTypeTexConstructor() {
        Assert.assertEquals("Quicksand", quicksand2.getType());
    }

    @Test
    public void getTypeTexRowColConstructor() {
        Assert.assertEquals("Quicksand", quicksand3.getType());
    }

    @Test
    public void getTextureNameNullConstructor() {
        Assert.assertNull(quicksand1.getTextureName());
    }

    @Test
    public void getTextureNameTexConstructor() {
        Assert.assertEquals("desert_1", quicksand2.getTextureName());
    }

    @Test
    public void getTextureNameTexRowColConstructor() {
        Assert.assertEquals("desert_2", quicksand3.getTextureName());
    }

    @Test
    public void getCol() {
        Assert.assertEquals(1f, quicksand3.getCol(), 0);
    }

    @Test
    public void getRow() {
        Assert.assertEquals(2f, quicksand3.getRow(), 0);
    }
}