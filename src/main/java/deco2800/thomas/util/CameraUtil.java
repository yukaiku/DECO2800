package deco2800.thomas.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import deco2800.thomas.entities.AbstractEntity;
import deco2800.thomas.worlds.AbstractWorld;

/**
 * A utility class for controlling the camera.
 */
public class CameraUtil {
    private static final float minimumZoom = 0.5f;
    private static final float maximumZoom = 10f;

    /**
     * Set the camera's position to a specific target's position,
     * the function have to convert from row column unit to
     * world coordinate in order to use in camera set position function. It
     * also calculates the render size of the target to set the camera at the center
     * of the target.
     *
     * @param camera the camera we want to set
     * @param target the target entity that the camera will follow
     */
    public static void lockCameraOnTarget(OrthographicCamera camera, AbstractEntity target) {
        float[] targetPosition = WorldUtil.colRowToWorldCords(target.getCol(), target.getRow());
        float[] targetRenderSize = WorldUtil.colRowToWorldCords(target.getColRenderLength(), target.getRowRenderLength());
        camera.position.set(targetPosition[0] + targetRenderSize[0] / 2, targetPosition[1] + targetRenderSize[1] / 2, 0);
        camera.update();
    }

    /**
     * Set the limit boundary for the camera in a world.
     *
     * @param camera the target camera
     * @param world  the world we would like to set boundary
     */
    public static void cameraBoundaryInWorld(OrthographicCamera camera, AbstractWorld world) {
        float[] worldSize = WorldUtil.colRowToWorldCords(world.getWidth(), world.getHeight());
        float startX = 0 - worldSize[0];
        float startY = 0 - worldSize[1];
        float width = worldSize[0] * 2;
        float height = worldSize[1] * 2;

        if (camera.viewportWidth <= width) {
            // Left bound
            if (camera.position.x - (camera.viewportWidth / 2) < startX) {
                camera.position.set(startX + camera.viewportWidth / 2, camera.position.y, camera.position.z);
            }

            // Right bound
            if (camera.position.x + (camera.viewportWidth / 2) > startX + width) {
                camera.position.set(startX + width - camera.viewportWidth / 2, camera.position.y, camera.position.z);
            }
        }


        if (camera.viewportHeight <= height) {
            // Bottom bound
            if ((camera.position.y - (camera.viewportHeight / 2)) < startY) {
                camera.position.set(camera.position.x, startY + (camera.viewportHeight / 2), camera.position.z);
            }

            // Top bound
            if (camera.position.y + (camera.viewportHeight / 2) > startY + height) {
                camera.position.set(camera.position.x, startY + height - camera.viewportHeight / 2, camera.position.z);
            }
        }
        camera.update();
    }

    /**
     * Listen on 2 specific keys to make a camera is able to zoom in and out
     * with a specific speed
     *
     * @param camera     the camera we want to set
     * @param zoomInKey  the key that we listen to zoom in
     * @param zoomOutKey the key that we listen to zoom out
     * @param speed      the zoom speed
     * @param allowed    Whether or not player can zoom on the current world.
     */
    public static void zoomableCamera(OrthographicCamera camera, int zoomInKey, int zoomOutKey, float speed, boolean allowed) {
        if (allowed) {
            if (Gdx.input.isKeyPressed(zoomInKey)) {
                camera.zoom -= speed;
                if (camera.zoom < minimumZoom) {
                    camera.zoom = minimumZoom;
                }
            } else if (Gdx.input.isKeyPressed(zoomOutKey)) {
                camera.zoom += speed;
                if (camera.zoom > maximumZoom) {
                    camera.zoom = maximumZoom;
                }
            }
        }

        camera.update();
    }
}
