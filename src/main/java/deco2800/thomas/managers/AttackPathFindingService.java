package deco2800.thomas.managers;

import deco2800.thomas.tasks.AttackPathFindingServiceThread;
import deco2800.thomas.tasks.RangedAttackTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class AttackPathFindingService extends TickableManager {

	private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(25);

	public String toString() {
		return String.format("[%d/%d] Active: %d, Completed: %d, Task: %d, %n Shutdown: %s, Terminated: %s",
				executor.getPoolSize(),
				executor.getCorePoolSize(),
				executor.getActiveCount(),
				executor.getCompletedTaskCount(),
				executor.getTaskCount(),
				executor.isShutdown(),
				executor.isTerminated());
	}

	public void addPath(RangedAttackTask movementTask) {
		executor.submit(new AttackPathFindingServiceThread(movementTask), "AttackPathFindingServiceThread");
	}

	public void stop() {
		executor.shutdownNow();
	}

	@Override
	public void onTick(long i) {
		// TODO Auto-generated method stub
		
	}
}
