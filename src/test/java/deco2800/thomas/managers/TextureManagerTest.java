package deco2800.thomas.managers;

import com.badlogic.gdx.graphics.Texture;
import deco2800.thomas.BaseGDXTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Texture.class, TextureManager.class, HashMap.class})
public class TextureManagerTest extends BaseGDXTest {
    // Stores state of missing files during test
    public class FileExistsResult {
        private boolean allFilesExist = true;
        private int fileCount = 0;
        private String missingFiles = "Missing Files:\n";

        public void recordMissingFile(String filename) {
            allFilesExist = false;
            missingFiles += filename + "\n";
        }

        public void recordExistingFile() {
            fileCount++;
        }

        public boolean testAllFilesExist() {
            return allFilesExist;
        }

        public String getMissingFiles() {
            return missingFiles;
        }

        public int getFileCount() {
            return fileCount;
        }
    }

    /* Track file exist state in class field */
    private FileExistsResult result;

    /* Setup mocks to check files exist */
    @Before
    public void setup() throws Exception {
        result = new FileExistsResult();

        // Mock texture manager so that it tests files exist
        // when loading, rather than trying to load resource
        Texture t = mock(Texture.class);
        PowerMockito.whenNew(Texture.class).withArguments(anyString()).then(invocation -> {
            File f = new File((String)invocation.getArgument(0));
            if (!f.exists()) {
                result.recordMissingFile(invocation.getArgument(0));
            } else {
                result.recordExistingFile();
            }
            return t;
        });
    }

    /**
     * Tests all base textures exist.
     */
    @Test
    public void testLoadBaseTextures() {
        // Try load base textures
        TextureManager m = new TextureManager();
        m.loadBaseTextures();

        // Verify all exist and were added to map
        assertTrue(result.getMissingFiles(), result.testAllFilesExist());
        assertNotNull(m.getTexture("player_right"));
        assertEquals(result.getFileCount(), m.getSize());
    }

    /**
     * Tests all enemy textures exist.
     */
    @Test
    public void testLoadEnemyTextures() {
        TextureManager m = new TextureManager();
        m.loadEnemyTextures();

        assertTrue(result.getMissingFiles(), result.testAllFilesExist());
        assertNotNull(m.getTexture("dummy"));
        assertEquals(result.getFileCount(), m.getSize());
    }

    /**
     * Tests all combat textures exist.
     */
    @Test
    public void testLoadCombatTextures() {
        TextureManager m = new TextureManager();
        m.loadCombatTextures();

        assertTrue(result.getMissingFiles(), result.testAllFilesExist());
        assertNotNull(m.getTexture("fireball_right"));
        assertEquals(result.getFileCount(), m.getSize());
    }

    /**
     * Tests all storyline textures exist.
     */
    @Test
    public void testLoadStorylineTextures() {
        TextureManager m = new TextureManager();
        m.loadStorylineTextures();

        assertTrue(result.getMissingFiles(), result.testAllFilesExist());
        assertNotNull(m.getTexture("stone-1"));
        assertEquals(result.getFileCount(), m.getSize());
    }

    /**
     * Tests all NPC textures exist.
     */
    @Test
    public void testLoadNPCTextures() {
        TextureManager m = new TextureManager();
        m.loadNPCTextures();

        assertTrue(result.getMissingFiles(), result.testAllFilesExist());
        assertNotNull(m.getTexture("tutorial_npc"));
        assertEquals(result.getFileCount(), m.getSize());
    }

    /**
     * Tests all inventory textures exist.
     */
    @Test
    public void testLoadInventoryTextures() {
        TextureManager m = new TextureManager();
        m.loadInventoryTextures();

        assertTrue(result.getMissingFiles(), result.testAllFilesExist());
        assertNotNull(m.getTexture("potion_small"));
        assertEquals(result.getFileCount(), m.getSize());
    }

    /**
     * Tests all mini map textures exist.
     */
    @Test
    public void testLoadMinimapTextures() {
        TextureManager m = new TextureManager();
        m.loadMinimapTextures();

        assertTrue(result.getMissingFiles(), result.testAllFilesExist());
        assertNotNull(m.getTexture("iconDefault"));
        assertEquals(result.getFileCount(), m.getSize());
    }

    /**
     * Tests all health textures exist.
     */
    @Test
    public void testLoadHealthTextures() {
        TextureManager m = new TextureManager();
        m.loadHealthTextures();

        assertTrue(result.getMissingFiles(), result.testAllFilesExist());
        assertNotNull(m.getTexture("health0"));
        assertEquals(result.getFileCount(), m.getSize());
    }

    /**
     * Tests all environment textures exist.
     */
    @Test
    public void testLoadEnvironmentTextures() {
        TextureManager m = new TextureManager();
        m.loadEnvironmentTextures();

        assertTrue(result.getMissingFiles(), result.testAllFilesExist());
        assertNotNull(m.getTexture("Volcano_1"));
        assertNotNull(m.getTexture("tundra-tile-1"));
        assertNotNull(m.getTexture("swamp_1"));
        assertNotNull(m.getTexture("dungeon-black"));
        assertNotNull(m.getTexture("desert_1"));
        assertEquals(result.getFileCount(), m.getSize());
    }

    /**
     * Tests all animation textures exist.
     */
    @Test
    public void testLoadAnimationFrames() {
        TextureManager m = new TextureManager();
        m.loadAnimationFrames();

        assertTrue(result.getMissingFiles(), result.testAllFilesExist());
    }

    /**
     * Tests that valid getTexture does not return null.
     */
    @Test
    public void testGetTexture() {
        TextureManager m = new TextureManager();
        assertNotNull(m.getTexture("spacman_ded"));

        m.loadBaseTextures();
        assertNotNull(m.getTexture("player_left"));

        m.loadCombatTextures();
        assertNotNull(m.getTexture("fireball_right"));

        m.loadEnemyTextures();
        assertNotNull(m.getTexture("enemyDefault"));

        m.loadEnvironmentTextures();
        assertNotNull(m.getTexture("Volcano_1"));
        assertNotNull(m.getTexture("tundra-tile-1"));
        assertNotNull(m.getTexture("swamp_1"));
        assertNotNull(m.getTexture("dungeon-black"));
        assertNotNull(m.getTexture("desert_1"));

        m.loadInventoryTextures();
        assertNotNull(m.getTexture("potion_small"));

        m.loadHealthTextures();
        assertNotNull(m.getTexture("health0"));

        m.loadMinimapTextures();
        assertNotNull(m.getTexture("iconDefault"));

        m.loadNPCTextures();
        assertNotNull(m.getTexture("tutorial_npc"));

        m.loadStorylineTextures();
        assertNotNull(m.getTexture("portal"));
    }

    /**
     * Tests that invalid getTexture does not return null.
     */
    @Test
    public void testInvalidGetTexture() {
        TextureManager m = new TextureManager();
        m.loadBaseTextures();
        assertNotNull(m.getTexture("invalid_spacman_ded"));
    }

    /**
     * Tests that valid getAnimationFrame does not return null.
     */
    @Test
    public void testGetAnimationFrames() {
        TextureManager m = new TextureManager();
        m.loadAnimationFrames();
        assertNotNull(m.getAnimationFrames("dragonVolcanoWalk"));
    }

    /**
     * Tests that an invalid animation frame returns null and does not
     * throw an exception.
     */
    @Test
    public void testInvalidGetAnimationFrames() {
        TextureManager m = new TextureManager();
        m.loadAnimationFrames();
        assertNull(m.getAnimationFrames("invalid_dragonVolcanoWalk"));
    }
}
