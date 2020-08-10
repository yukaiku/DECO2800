package deco2800.thomas.managers;

import deco2800.thomas.Tickable;

public abstract class TickableManager extends AbstractManager implements Tickable{

	 public abstract void onTick(long i);
}
