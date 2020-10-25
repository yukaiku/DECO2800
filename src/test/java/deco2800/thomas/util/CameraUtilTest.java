package deco2800.thomas.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.entities.Rock;
import deco2800.thomas.worlds.AbstractWorld;
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

        CameraUtil.zoomableCamera(spyCamera, zoomInKey, zoomOutKey, 0.5f, true);

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

        CameraUtil.zoomableCamera(spyCamera, zoomInKey, zoomOutKey, 0.6f, true);

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

        CameraUtil.zoomableCamera(spyCamera, zoomInKey, zoomOutKey, 0.5f, true);

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

        CameraUtil.zoomableCamera(spyCamera, zoomInKey, zoomOutKey, 30f, true);

        assertEquals(10f, spyCamera.zoom, 0.1f);
    }

    @Test
    public void testLeftBoundaryInWorld() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.viewportWidth = 100;
        camera.viewportHeight = 50;
        OrthographicCamera spyCamera = spy(camera);

        AbstractWorld world = mock(AbstractWorld.class);
        // Width = 25 * 96 * 0.75 * 2 = 3600 (Convert in world util)
        when(world.getWidth()).thenReturn(25);
        // Height = 20 * 83.4 * 2 = 3336 (Convert in world util)
        when(world.getHeight()).thenReturn(20);

        camera.position.x = 0 - 1800 + 10;

        doNothing().when(spyCamera).update();

        CameraUtil.cameraBoundaryInWorld(spyCamera, world);

        assertEquals(0 - 1800 + 50, spyCamera.position.x, 0.1f);
        verify(spyCamera, times(1)).update();
    }

    @Test
    public void testRightBoundaryInWorld() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.viewportWidth = 100;
        camera.viewportHeight = 50;
        OrthographicCamera spyCamera = spy(camera);

        AbstractWorld world = mock(AbstractWorld.class);
        // Width = 25 * 96 * 0.75 * 2 = 3600 (Convert in world util)
        when(world.getWidth()).thenReturn(25);
        // Height = 20 * 83.4 * 2 = 3336 (Convert in world util)
        when(world.getHeight()).thenReturn(20);

        camera.position.x = 0 + 1800 * 2 - 10;

        doNothing().when(spyCamera).update();

        CameraUtil.cameraBoundaryInWorld(spyCamera, world);

        assertEquals(0 + 1800 - 50, spyCamera.position.x, 0.1f);
        verify(spyCamera, times(1)).update();
    }

    @Test
    public void testBottomBoundaryInWorld() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.viewportWidth = 100;
        camera.viewportHeight = 50;
        OrthographicCamera spyCamera = spy(camera);

        AbstractWorld world = mock(AbstractWorld.class);
        // Width = 25 * 96 * 0.75 * 2 = 3600 (Convert in world util)
        when(world.getWidth()).thenReturn(25);
        // Height = 20 * 83.4 * 2 = 3336 (Convert in world util)
        when(world.getHeight()).thenReturn(20);

        camera.position.y = 0 - 1668 + 10;

        doNothing().when(spyCamera).update();

        CameraUtil.cameraBoundaryInWorld(spyCamera, world);

        assertEquals(0 - 1668 + 25, spyCamera.position.y, 0.1f);
        verify(spyCamera, times(1)).update();
    }

    @Test
    public void testTopBoundaryInWorld() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.viewportWidth = 100;
        camera.viewportHeight = 50;
        OrthographicCamera spyCamera = spy(camera);

        AbstractWorld world = mock(AbstractWorld.class);
        // Width = 25 * 96 * 0.75 * 2 = 3600 (Convert in world util)
        when(world.getWidth()).thenReturn(25);
        // Height = 20 * 83.4 * 2 = 3336 (Convert in world util)
        when(world.getHeight()).thenReturn(20);

        camera.position.y = 0 + 1668 * 2 - 10;

        doNothing().when(spyCamera).update();

        CameraUtil.cameraBoundaryInWorld(spyCamera, world);

        assertEquals(0 + 1668 - 25, spyCamera.position.y, 0.1f);
        verify(spyCamera, times(1)).update();
    }
}
