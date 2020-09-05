package deco2800.thomas.entities;

import deco2800.thomas.entities.Agent.PlayerPeon;
import deco2800.thomas.util.WorldUtil;
import deco2800.thomas.worlds.Tile; 
import deco2800.thomas.entities.NPC.NonPlayablePeon;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import deco2800.thomas.util.SquareVector; 
import deco2800.thomas.worlds.TestWorld;
import deco2800.thomas.entities.Agent.PlayerPeon;

import java.util.ArrayList;
import java.util.List;

public class NonPlayablePeonTest {
	SquareVector position1 = new SquareVector(1,1); 
	SquareVector position2 = new SquareVector(2,3);
	PlayerPeon current_player = new PlayerPeon(5,3,2);
	
	@Test 
	public void checkPosition(){
		// check that it spawns.
	// check name is correct 
	// check ends up in the same place. 
		NonPlayablePeon basicNPC= new NonPlayablePeon("NPC1",position1);
		Assert.assertEquals(basicNPC.position, position1);
	}
	
	@Test 
	public void checkName(){
		NonPlayablePeon basicNpc = new NonPlayablePeon("NPC1", position2);
		Assert.assertEquals(basicNpc.getName(), "NPC1");
	}
	
	
	@Test 
	// check that feet actually obstructed 
	public void checkTileObstructed(){
		Tile A = new Tile ("square2"); 
		A.setCol(2);
		A.setRow(3);
		// probably not important 
		SquareVector position2 = new SquareVector(2,3);
		NonPlayablePeon basicNpc = new NonPlayablePeon("NPC1", position2);
		List<NonPlayablePeon> npnSpawns = new ArrayList<>();
		npnSpawns.add(basicNpc);
		npnSpawns.add(new NonPlayablePeon("Fred", current_player.getPosition()));
		current_player.setPosition(2,3,1);
		
		// after world has been added to game manager.
		// shouldn't be allowed to spawn there?
		// testing -> create new game, new game world, ENUM test world, do 
		// that, create player etc, and then check tile. 
	}
	
	@Test 
	public void checkInteraction(){
		SquareVector position2 = new SquareVector(2,3);
		NonPlayablePeon basicNpc = new NonPlayablePeon("NPC1", position2);
		
		//basicNpc.notifyTouchDown(2,3,1,);
		
		
	}
	
	// another test - mock a mouse click
	// another test - mock mouse click, where not sufficiently close enough 
	// to interact yet. 
	// boundary case. 
	
	
	
	
	
	
	
	
	
	
	
}
