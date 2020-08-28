package deco2800.thomas.entities.Agent;

import com.badlogic.gdx.Gdx;
import deco2800.thomas.managers.GameManager;
import deco2800.thomas.managers.InputManager;
import deco2800.thomas.observers.TouchDownObserver;
import deco2800.thomas.tasks.MovementTask;
import deco2800.thomas.util.SquareVector;
import deco2800.thomas.util.WorldUtil;

public class PlayerPeon extends Peon implements TouchDownObserver {
    /*Variable for orb count*/
    int orbCount = 0;

    public PlayerPeon(float row, float col, float speed) {
        super(row, col, speed);
        this.setObjectName("playerPeon");
        GameManager.getManagerFromInstance(InputManager.class).addTouchDownListener(this);
    }


    /**
     *
     * Quest Tracker function that tracks the orbs the user currently has
     *
     * @return orbCount the number of orbs the user currently has
     */
    public int questTracker(){
        return orbCount;
    }

    /**
     *
     * Resets the number of orb user has
     * Notes:
     * To be used on when a new game is run or upon death
     *
     */
    public void resetQuest(){
        orbCount = 0;
    }

    /**
     *
     * Increase the number of orbs the user has
     * and run the questTracker function to update progress
     * Notes:
     * To be used on when player picks up an orb
     *
     */
    public void increaseOrbs(){
        orbCount += 1;
        questTracker();
    }

	@Override
	public void onTick(long i) {
		if (getTask() != null && getTask().isAlive()) {
			getTask().onTick(i);

			if (getTask().isComplete()) {
				setTask(null);
			}
		}
	}

	@Override
	public void notifyTouchDown(int screenX, int screenY, int pointer, int button) {
		float[] mouse = WorldUtil.screenToWorldCoordinates(Gdx.input.getX(), Gdx.input.getY());
		float[] clickedPosition = WorldUtil.worldCoordinatesToColRow(mouse[0], mouse[1]);

		System.out.printf("mouse: %.2f %.2f%n", mouse[0], mouse[1]);
		System.out.printf("clickedPosition: %.2f %.2f%n", clickedPosition[0], clickedPosition[1]);
		setTask(new MovementTask(this, new SquareVector(clickedPosition[0], clickedPosition[1])));
	}
}
