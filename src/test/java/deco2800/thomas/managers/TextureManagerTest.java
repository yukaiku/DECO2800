package deco2800.thomas.managers;

import deco2800.thomas.BaseGDXTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TextureManagerTest extends BaseGDXTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Tests the constructor creates a valid instance of TextureManager.
     * This test exists to prevent typo's when adding new resources to the TextureManager.
     */
    @Test
    public void testValidConstructor() {
        TextureManager textureManager = new TextureManager();
    }
}
