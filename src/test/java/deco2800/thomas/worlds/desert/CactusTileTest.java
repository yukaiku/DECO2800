package deco2800.thomas.worlds.desert;

import deco2800.thomas.BaseGDXTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CactusTileTest extends BaseGDXTest {

    CactusTile cactus1;
    CactusTile cactus2;
    CactusTile cactus3;


    @Before
    public void setUp() throws Exception {
        cactus1 = new CactusTile();
        cactus2 = new CactusTile("desert_1");
        cactus3 = new CactusTile("desert_2", 1f, 2f);
    }

    @Test
    public void getTypeNullConstructor() {
        Assert.assertEquals("Cactus", cactus1.getType());
    }

    @Test
    public void getTypeTexConstructor() {
        Assert.assertEquals("Cactus", cactus2.getType());
    }

    @Test
    public void getTypeTexRowColConstructor() {
        Assert.assertEquals("Cactus", cactus3.getType());
    }

    @Test
    public void getTextureNameNullConstructor() {
        Assert.assertNull(cactus1.getTextureName());
    }

    @Test
    public void getTextureNameTexConstructor() {
        Assert.assertEquals("desert_1", cactus2.getTextureName());
    }

    @Test
    public void getTextureNameTexRowColConstructor() {
        Assert.assertEquals("desert_2", cactus3.getTextureName());
    }

    @Test
    public void getCol() {
        Assert.assertEquals(1f, cactus3.getCol(), 0);
    }

    @Test
    public void getRow() {
        Assert.assertEquals(2f, cactus3.getRow(), 0);
    }
}