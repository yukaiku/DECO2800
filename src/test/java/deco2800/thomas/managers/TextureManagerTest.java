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
     * It will fail the build if the filepath for a resource has been misconfigured
     * which causes a texture bug where only spacman_dead textures can be used
     */
    @Test
    public void testValidConstructor() {
        TextureManager textureManager = new TextureManager();
    }
}
