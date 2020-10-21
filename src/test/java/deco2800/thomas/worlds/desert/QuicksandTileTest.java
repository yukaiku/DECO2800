package deco2800.thomas.worlds.desert;

import deco2800.thomas.BaseGDXTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the QuicksandTile class and its methods.
 */
public class QuicksandTileTest extends BaseGDXTest {

    // the QuicksandTile instances being used for testing
    QuicksandTile quicksand1;
    QuicksandTile quicksand2;
    QuicksandTile quicksand3;

    /**
     * Sets up the quicksand tile instances for testing.
     */
    @Before
    public void setUp() throws Exception {
        quicksand1 = new QuicksandTile();
        quicksand2 = new QuicksandTile("desert_1");
        quicksand3 = new QuicksandTile("desert_2", 1f, 2f);
    }

    /**
     * Tests that quicksand created with a null constructor correctly returns
     * "Quicksand" as its type from getType().
     */
    @Test
    public void getTypeNullConstructor() {
        Assert.assertEquals("Quicksand", quicksand1.getType());
    }

    /**
     * Tests that quicksand created with the texture-only constructor correctly returns
     * "Quicksand" as its type from getType().
     */
    @Test
    public void getTypeTexConstructor() {
        Assert.assertEquals("Quicksand", quicksand2.getType());
    }

    /**
     * Tests that quicksand created with the full constructor correctly returns
     * "Quicksand" as its type from getType().
     */
    @Test
    public void getTypeTexRowColConstructor() {
        Assert.assertEquals("Quicksand", quicksand3.getType());
    }

    /**
     * Tests that quicksand created with a null constructor correctly returns
     * null from getTextureName().
     */
    @Test
    public void getTextureNameNullConstructor() {
        Assert.assertNull(quicksand1.getTextureName());
    }

    /**
     * Tests that quicksand created with a texture-only constructor returns the
     * correct name from getTextureName().
     */
    @Test
    public void getTextureNameTexConstructor() {
        Assert.assertEquals("desert_1", quicksand2.getTextureName());
    }

    /**
     * Tests that quicksand created with a full constructor returns the
     * correct name from getTextureName().
     */
    @Test
    public void getTextureNameTexRowColConstructor() {
        Assert.assertEquals("desert_2", quicksand3.getTextureName());
    }

    /**
     * Tests that getCol() correctly returns the column of the quicksand tile.
     */
    @Test
    public void getCol() {
        Assert.assertEquals(1f, quicksand3.getCol(), 0);
    }

    /**
     * Tests that getRow() correctly returns the row of the quicksand tile.
     */
    @Test
    public void getRow() {
        Assert.assertEquals(2f, quicksand3.getRow(), 0);
    }
}