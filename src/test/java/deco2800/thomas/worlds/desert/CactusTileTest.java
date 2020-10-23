package deco2800.thomas.worlds.desert;

import deco2800.thomas.BaseGDXTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the CactusTile class and its methods.
 */
public class CactusTileTest extends BaseGDXTest {

    // three CactusTile instances being used for testing
    CactusTile cactus1;
    CactusTile cactus2;
    CactusTile cactus3;


    /**
     * Sets up the cactus tile instances for testing.
     */
    @Before
    public void setUp() throws Exception {
        cactus1 = new CactusTile();
        cactus2 = new CactusTile("desert_1");
        cactus3 = new CactusTile("desert_2", 1f, 2f);
    }

    /**
     * Tests that a cactus created with a null constructor correctly returns
     * "Cactus" as its type from getType().
     */
    @Test
    public void getTypeNullConstructor() {
        Assert.assertEquals("Cactus", cactus1.getType());
    }

    /**
     * Tests that a cactus created with the texture-only constructor correctly returns
     * "Cactus" as its type from getType().
     */
    @Test
    public void getTypeTexConstructor() {
        Assert.assertEquals("Cactus", cactus2.getType());
    }

    /**
     * Tests that a cactus created with the full constructor correctly returns
     * "Cactus" as its type from getType().
     */
    @Test
    public void getTypeTexRowColConstructor() {
        Assert.assertEquals("Cactus", cactus3.getType());
    }

    /**
     * Tests that a cactus created with a null constructor correctly returns
     * null from getTextureName().
     */
    @Test
    public void getTextureNameNullConstructor() {
        Assert.assertNull(cactus1.getTextureName());
    }

    /**
     * Tests that a cactus created with a texture-only constructor returns the
     * correct name from getTextureName().
     */
    @Test
    public void getTextureNameTexConstructor() {
        Assert.assertEquals("desert_1", cactus2.getTextureName());
    }

    /**
     * Tests that a cactus created with a full constructor returns the
     * correct name from getTextureName().
     */
    @Test
    public void getTextureNameTexRowColConstructor() {
        Assert.assertEquals("desert_2", cactus3.getTextureName());
    }

    /**
     * Tests that getCol() correctly returns the column of the cactus tile.
     */
    @Test
    public void getCol() {
        Assert.assertEquals(1f, cactus3.getCol(), 0);
    }

    /**
     * Tests that getRow() correctly returns the row of the cactus tile.
     */
    @Test
    public void getRow() {
        Assert.assertEquals(2f, cactus3.getRow(), 0);
    }
}