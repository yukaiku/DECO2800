package deco2800.thomas.renderers;

import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Special camera with added implementation for user-controlled camera movement.
 */

public class PotateCamera extends OrthographicCamera {

private boolean potate = false;
private boolean goMoveEnabled = true;

	public PotateCamera(int i, int j) {
		super(i, j);
	}

	public boolean isGoMoveEnabled() {
		return goMoveEnabled;
	}
	public void setGoMoveEnabled(boolean goMoveEnabled) {
		this.goMoveEnabled = goMoveEnabled;
	}

	public boolean isPotate() {
		return potate;
	}
	public void setPotate(boolean potate) {
		this.potate = potate;
	}
}