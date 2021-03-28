package main;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import recorder.Recorder;
import recorder.ScrollAction;

public class NativeScrollListener implements NativeMouseWheelListener {

	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
		ScrollAction s = new ScrollAction();
		s.ticks = e.getWheelRotation(); // Negative ticks are up, positive are down
		Recorder.add(s);
	}

}
