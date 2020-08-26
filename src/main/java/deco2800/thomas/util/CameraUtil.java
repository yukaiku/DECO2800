package deco2800.thomas.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import deco2800.thomas.entities.AbstractEntity;

/**
 * A utility class for controlling the camera.
 */
public class CameraUtil {
    /**
     * Set the camera's position to a specific target's position,
     * the function have to convert from row column unit to
     * world coordinate in order to use in camera position. It also
     * calculates the size of the target to set the camera at the center
     * of the target.
     * @param camera the camera we want to set
     * @param target the target entity that the camera will follow
     */
    public static void lockCameraOnTarget(OrthographicCamera camera, AbstractEntity target) {
        float[] targetPosition = WorldUtil.colRowToWorldCords(target.getCol(), target.getRow());
        float[] targetRenderSize = WorldUtil.colRowToWorldCords(target.getColRenderWidth(), target.getRowRenderLength());
        camera.position.set(targetPosition[0] + targetRenderSize[0] / 2, targetPosition[1] + targetRenderSize[1] / 2, 0);
        camera.update();
    }
}
