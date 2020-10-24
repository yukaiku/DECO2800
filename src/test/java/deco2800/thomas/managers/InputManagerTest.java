package deco2800.thomas.managers;

import deco2800.thomas.observers.*;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class InputManagerTest {
    private InputManager inputManager;

    @Before
    public void setUp() {
        inputManager = new InputManager();
    }

    @Test
    public void testKeyDownListener() {
        KeyDownObserver keyDownObserver = mock(KeyDownObserver.class);

        inputManager.addKeyDownListener(keyDownObserver);
        inputManager.removeKeyDownListener(keyDownObserver);
    }

    @Test
    public void testKeyUpListener() {
        KeyUpObserver keyUpObserver = mock(KeyUpObserver.class);

        inputManager.addKeyUpListener(keyUpObserver);
        inputManager.removeKeyUpListener(keyUpObserver);
    }

    @Test
    public void testTouchDownListener() {
        TouchDownObserver touchDownObserver = mock(TouchDownObserver.class);

        inputManager.addTouchDownListener(touchDownObserver);
        inputManager.removeTouchDownListener(touchDownObserver);
    }

    @Test
    public void testTouchUpListener() {
        TouchUpObserver touchUpObserver = mock(TouchUpObserver.class);

        inputManager.addTouchUpListener(touchUpObserver);
        inputManager.removeTouchUpListener(touchUpObserver);
    }

    @Test
    public void testTouchDraggedListener() {
        TouchDraggedObserver touchDraggedObserver = mock(TouchDraggedObserver.class);

        inputManager.addTouchDraggedListener(touchDraggedObserver);
        inputManager.removeTouchDraggedListener(touchDraggedObserver);
    }

    @Test
    public void testMouseMovedListener() {
        MouseMovedObserver mouseMovedObserver = mock(MouseMovedObserver.class);

        inputManager.addMouseMovedListener(mouseMovedObserver);
        inputManager.removeMouseMovedListener(mouseMovedObserver);
    }

    @Test
    public void testScrollListener() {
        ScrollObserver scrollObserver = mock(ScrollObserver.class);

        inputManager.addScrollListener(scrollObserver);
        inputManager.removeScrollListener(scrollObserver);
    }

    @Test
    public void testKeyDownNotification() {
        KeyDownObserver keyDownObserver = mock(KeyDownObserver.class);
        doNothing().when(keyDownObserver).notifyKeyDown(anyInt());

        inputManager.addKeyDownListener(keyDownObserver);
        inputManager.keyDown(0);

        verify(keyDownObserver).notifyKeyDown(anyInt());
    }

    @Test
    public void testKeyUpNotification() {
        KeyUpObserver keyUpObserver = mock(KeyUpObserver.class);
        doNothing().when(keyUpObserver).notifyKeyUp(anyInt());

        inputManager.addKeyUpListener(keyUpObserver);
        inputManager.keyUp(0);

        verify(keyUpObserver).notifyKeyUp(anyInt());
    }

    @Test
    public void testTouchDownNotification() {
        TouchDownObserver touchDownObserver = mock(TouchDownObserver.class);
        doNothing().when(touchDownObserver).notifyTouchDown(anyInt(), anyInt(), anyInt(), anyInt());

        inputManager.addTouchDownListener(touchDownObserver);
        inputManager.touchDown(0, 0, 0, 0);

        verify(touchDownObserver).notifyTouchDown(anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    public void testTouchUpNotification() {
        TouchUpObserver touchUpObserver = mock(TouchUpObserver.class);
        doNothing().when(touchUpObserver).notifyTouchUp(anyInt(), anyInt(), anyInt(), anyInt());

        inputManager.addTouchUpListener(touchUpObserver);
        inputManager.touchUp(0, 0, 0, 0);

        verify(touchUpObserver).notifyTouchUp(anyInt(), anyInt(), anyInt(), anyInt());
    }

    @Test
    public void testTouchDraggedNotification() {
        TouchDraggedObserver touchDraggedObserver = mock(TouchDraggedObserver.class);
        doNothing().when(touchDraggedObserver).notifyTouchDragged(anyInt(), anyInt(), anyInt());

        inputManager.addTouchDraggedListener(touchDraggedObserver);
        inputManager.touchDragged(0, 0, 0);

        verify(touchDraggedObserver).notifyTouchDragged(anyInt(), anyInt(), anyInt());
    }

    @Test
    public void testMouseMovedNotification() {
        MouseMovedObserver mouseMovedObserver = mock(MouseMovedObserver.class);
        doNothing().when(mouseMovedObserver).notifyMouseMoved(anyInt(), anyInt());

        inputManager.addMouseMovedListener(mouseMovedObserver);
        inputManager.mouseMoved(0, 0);

        verify(mouseMovedObserver).notifyMouseMoved(anyInt(), anyInt());
    }

    @Test
    public void testMouseScrolledNotification() {
        ScrollObserver scrollObserver = mock(ScrollObserver.class);
        doNothing().when(scrollObserver).notifyScrolled(anyInt());

        inputManager.addScrollListener(scrollObserver);
        inputManager.scrolled(0);

        verify(scrollObserver).notifyScrolled(anyInt());
    }
}
