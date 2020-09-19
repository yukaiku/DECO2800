package deco2800.thomas.entities.items;

import deco2800.thomas.entities.agent.LoadedPeon;
import deco2800.thomas.worlds.Tile;
import org.junit.Assert;
import org.junit.Test;
import deco2800.thomas.BaseGDXTest;
import deco2800.thomas.entities.agent.PlayerPeon;
import deco2800.thomas.util.SquareVector;

import deco2800.thomas.entities.enemies.EnemyPeon;
import deco2800.thomas.entities.enemies.Goblin;
import deco2800.thomas.entities.enemies.Orc;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ItemTest extends BaseGDXTest{

    @Test
    public void getItemName() {
        Item testPotion = new Item("health_potion",50);
        Assert.assertEquals("health_potion",testPotion.getItemName());
    }

    @Test
    public void getCurrencyValue() {
        Item testPotion = new Item("health_potion",50);
        Assert.assertEquals(50,testPotion.getCurrencyValue());
    }

    @Test
    public void randomItemPositionGenerator() {
        List<Integer> numListX = new ArrayList<>();
        List<Integer> numListY = new ArrayList<>();
        int height = 25;
        int width = 50;
        int randomX = Item.randomItemPositionGenerator(height);
        int randomY = Item.randomItemPositionGenerator(width);
        for (int i = -height ; i < height; i++){
            numListX.add(i);
        }
        for (int i = -width; i < width; i++){
            numListY.add(i);
        }
        Assert.assertEquals(true,numListX.contains(randomX));
        Assert.assertEquals(true,numListY.contains(randomY));
    }


    @Test
    public void chargePlayer() {
        Item testPotion = new Item("health_potion",50);
        LoadedPeon testPlayer = new LoadedPeon();
        testPlayer.addMoney(100);
        testPlayer.takeMoney(50);
        testPotion.chargePlayer();
        Assert.assertEquals(50,testPlayer.getWallet());
    }

    @Test
    public void interact() {

    }

    @Test
    public void notifyTouchDown() {
    }
}