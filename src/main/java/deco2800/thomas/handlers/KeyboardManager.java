package deco2800.thomas.handlers;

import com.badlogic.gdx.InputProcessor;
import deco2800.thomas.managers.TickableManager;
import deco2800.thomas.observers.KeyDownObserver;
import deco2800.thomas.observers.KeyTypedObserver;

import java.util.ArrayList;
import java.util.List;

public class KeyboardManager extends TickableManager implements InputProcessor {
    List<KeyTypedObserver> keyTypedObserverList = new ArrayList<>();
    List<KeyDownObserver> keyDownObserverList = new ArrayList<>();

    public void registerForKeyTyped(KeyTypedObserver observer) {
        keyTypedObserverList.add(observer);
    }
    public void registerForKeyDown(KeyDownObserver observer) {
        keyDownObserverList.add(observer);
    }

    @Override
    public void onTick(long i) {
        // Do nothing
    }

    @Override
    public boolean keyDown(int keycode) {
        for (KeyDownObserver o : keyDownObserverList) {
            o.notifyKeyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        for (KeyTypedObserver o : keyTypedObserverList) {
            o.notifyKeyTyped(character);
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
