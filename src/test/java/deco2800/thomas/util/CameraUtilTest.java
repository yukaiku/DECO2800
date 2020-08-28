package deco2800.thomas.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import deco2800.thomas.entities.PlayerPeon;
import deco2800.thomas.entities.Rock;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Test for CameraUtil class
 */
public class CameraUtilTest {
    /**
     * Test lockCameraOnTarget function on a normal player peon (agent entity)
     */
    @Test
    public void testLockCameraOnPlayerPeon() {
        PlayerPeon playerPeon = mock(PlayerPeon.class);
        OrthographicCamera camera = new OrthographicCamera();
        OrthographicCamera spyCamera = spy(camera);

        when(playerPeon.getCol()).thenReturn(10f);
        when(playerPeon.getRow()).thenReturn(5f);
        when(playerPeon.getColRenderLength()).thenReturn(1f);
        when(playerPeon.getRowRenderLength()).thenReturn(1f);
        doNothing().when(spyCamera).update();

        CameraUtil.lockCameraOnTarget(spyCamera, playerPeon);

        assertEquals(756.0, spyCamera.position.x, 0.1f);
        assertEquals(458.7, spyCamera.position.y, 0.1f);
        verify(spyCamera, atLeastOnce()).update();
    }

    /**
     * Test lockCameraOnTarget function on a rock (static entity)
     */
    @Test
    public void testLockCameraOnRock() {
        Rock rock = mock(Rock.class);
        OrthographicCamera camera = new OrthographicCamera();
        OrthographicCamera spyCamera = spy(camera);

        when(rock.getCol()).thenReturn(3f);
        when(rock.getRow()).thenReturn(20f);
        when(rock.getColRenderLength()).thenReturn(1f);
        when(rock.getRowRenderLength()).thenReturn(1f);
        doNothing().when(spyCamera).update();

        CameraUtil.lockCameraOnTarget(spyCamera, rock);

        assertEquals(252.0, spyCamera.position.x, 0.1f);
        assertEquals(1709.7, spyCamera.position.y, 0.1f);
        verify(spyCamera, atLeastOnce()).update();
    }

    /**
     * Test zoom-in camera
     */
    @Test
    public void testZoomInCamera() {
        Gdx.input = mock(Input.class);
        OrthographicCamera camera = new OrthographicCamera();
        OrthographicCamera spyCamera = spy(camera);
        int zoomInKey = Input.Keys.NUM_1;
        int zoomOutKey = Input.Keys.NUM_2;

        when(Gdx.input.isKeyPressed(zoomInKey)).thenReturn(true);
        doNothing().when(spyCamera).update();

        CameraUtil.zoomableCamera(spyCamera, zoomInKey, zoomOutKey, 0.5f);

        assertEquals(0.5f, spyCamera.zoom, 0.1f);
    }

    /**
     * Test zoom-in camera reach minimum zoom limit
     */
    @Test
    public void testZoomInCameraReachMinimumLimit() {
        Gdx.input = mock(Input.class);
        OrthographicCamera camera = new OrthographicCamera();
        OrthographicCamera spyCamera = spy(camera);
        int zoomInKey = Input.Keys.NUM_1;
        int zoomOutKey = Input.Keys.NUM_2;

        when(Gdx.input.isKeyPressed(zoomInKey)).thenReturn(true);
        doNothing().when(spyCamera).update();

        CameraUtil.zoomableCamera(spyCamera, zoomInKey, zoomOutKey, 0.6f);

        assertEquals(0.5f, spyCamera.zoom, 0.1f);
    }

    /**
     * Test zoom-out camera
     */
    @Test
    public void testZoomOutCamera() {
        Gdx.input = mock(Input.class);
        OrthographicCamera camera = new OrthographicCamera();
        OrthographicCamera spyCamera = spy(camera);
        int zoomInKey = Input.Keys.NUM_1;
        int zoomOutKey = Input.Keys.NUM_2;

        when(Gdx.input.isKeyPressed(zoomOutKey)).thenReturn(true);
        doNothing().when(spyCamera).update();

        CameraUtil.zoomableCamera(spyCamera, zoomInKey, zoomOutKey, 0.5f);

        assertEquals(1.5f, spyCamera.zoom, 0.1f);
    }

    /**
     * Test zoom-out camera reach maximum zoom limit
     */
    @Test
    public void testZoomOutCameraReachMaximumLimit() {
        Gdx.input = mock(Input.class);
        OrthographicCamera camera = new OrthographicCamera();
        OrthographicCamera spyCamera = spy(camera);
        int zoomInKey = Input.Keys.NUM_1;
        int zoomOutKey = Input.Keys.NUM_2;

        when(Gdx.input.isKeyPressed(zoomOutKey)).thenReturn(true);
        doNothing().when(spyCamera).update();

        CameraUtil.zoomableCamera(spyCamera, zoomInKey, zoomOutKey, 30f);

        assertEquals(10f, spyCamera.zoom, 0.1f);
    }
}
