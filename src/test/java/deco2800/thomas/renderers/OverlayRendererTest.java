package deco2800.thomas.renderers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import deco2800.thomas.renderers.components.DebugComponent;
import deco2800.thomas.renderers.components.GuidelineComponent;
import deco2800.thomas.renderers.components.HotbarComponent;
import deco2800.thomas.renderers.components.QuestTrackerComponent;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Test the OverlayRenderer
 */
public class OverlayRendererTest {
    /**
     * Test render with some components
     */
    @Test
    public void testRenderComponents() {
        OverlayRenderer overlayRenderer = new OverlayRenderer();

        DebugComponent debugComponent = mock(DebugComponent.class);
        doNothing().when(debugComponent).render(any(SpriteBatch.class));
        HotbarComponent hotbarComponent = mock(HotbarComponent.class);
        doNothing().when(hotbarComponent).render(any(SpriteBatch.class));
        QuestTrackerComponent questTrackerComponent = mock(QuestTrackerComponent.class);
        doNothing().when(questTrackerComponent).render(any(SpriteBatch.class));
        GuidelineComponent guidelineComponent = mock(GuidelineComponent.class);
        doNothing().when(guidelineComponent).render(any(SpriteBatch.class));
        SpriteBatch batch = mock(SpriteBatch.class);
        OrthographicCamera camera = new OrthographicCamera();
        camera.position.x = 10;
        camera.position.y = 5;
        camera.viewportWidth = 1234;
        camera.viewportHeight = 800;

        overlayRenderer.getComponents().add(debugComponent);
        overlayRenderer.getComponents().add(hotbarComponent);
        overlayRenderer.getComponents().add(questTrackerComponent);
        overlayRenderer.getComponents().add(guidelineComponent);

        overlayRenderer.render(batch, camera);

        assertEquals(-607f, overlayRenderer.getX(), 0.1f);
        assertEquals(-395f, overlayRenderer.getY(), 0.1f);
        assertEquals(1234f, overlayRenderer.getWidth(), 0.1f);
        assertEquals(800f, overlayRenderer.getHeight(), 0.1f);
        verify(debugComponent, times(1)).render(batch);
        verify(hotbarComponent, times(1)).render(batch);
        verify(questTrackerComponent, times(1)).render(batch);
        verify(guidelineComponent, times(1)).render(batch);
    }
}
