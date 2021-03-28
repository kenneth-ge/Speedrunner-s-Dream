package main;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import recorder.KeyAction;
import recorder.Recorder;

public class NativeKeyboardListener implements NativeKeyListener {

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		if(e.getKeyCode() == NativeKeyEvent.VC_F12) {
			System.out.println("Toggle recording");
			Recorder.toggle();
			return;
		}
		
		if(e.getKeyCode() == NativeKeyEvent.VC_F10) {
			System.out.println("Playback");
			try {
				Recorder.playback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return;
		}
		
		KeyAction k = new KeyAction();
		k.keyCode = e.getKeyCode();
		k.press = true;
		Recorder.add(k);
		/*System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		System.out.println(e.getKeyCode());*/
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		KeyAction k = new KeyAction();
		k.keyCode = e.getKeyCode();
		k.press = false;
		Recorder.add(k);
		//System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		// Do nothing.
	}

}
