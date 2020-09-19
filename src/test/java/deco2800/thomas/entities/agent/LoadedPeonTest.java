package deco2800.thomas.entities.agent;

import deco2800.thomas.BaseGDXTest;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoadedPeonTest extends BaseGDXTest {

    @Test
    public void getWallet() {
        LoadedPeon testPeon = new LoadedPeon();
        Assert.assertEquals(0, testPeon.getWallet(),0.5);
    }

    @Test
    public void takeMoney() {
        LoadedPeon testPeon = new LoadedPeon();
        testPeon.addMoney(500);
        testPeon.takeMoney(300);
        Assert.assertEquals(200,testPeon.getWallet(),0.5);
    }

    @Test
    public void addMoney() {
        LoadedPeon testPeon = new LoadedPeon();
        testPeon.addMoney(400);
        Assert.assertEquals(400,testPeon.getWallet(),0.5);
    }
}