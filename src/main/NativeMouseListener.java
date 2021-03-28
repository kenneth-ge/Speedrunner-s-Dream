package main;
import java.awt.Robot;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import recorder.MouseAction;
import recorder.MouseMoveAction;
import recorder.Recorder;

public class NativeMouseListener implements NativeMouseInputListener {
	
	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {
		// Do nothing, since this is already handled by nativeMousePressed and nativeMouseReleased.
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		MouseAction m = new MouseAction();
		m.button = e.getButton();
		m.press = true;
		Recorder.add(m);
		//System.out.println("Mouse Pressed: " + e.getButton());
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		MouseAction m = new MouseAction();
		m.button = e.getButton();
		m.press = false;
		Recorder.add(m);
		//System.out.println("Mouse Released: " + e.getButton());
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {
		MouseMoveAction m = new MouseMoveAction();
		m.x = e.getX();
		m.y = e.getY();
		
		//robot.mouseMove(m.x, m.y);
		
		Recorder.add(m);
		//System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {
		MouseMoveAction m = new MouseMoveAction();
		m.x = e.getX();
		m.y = e.getY();
		Recorder.add(m);
		//System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
	}
	
}
