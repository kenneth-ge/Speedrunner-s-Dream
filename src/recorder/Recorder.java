package recorder;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.TrayIcon.MessageType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.github.jinahya.bit.io.BitInput;
import com.github.jinahya.bit.io.BitOutput;
import com.github.jinahya.bit.io.DefaultBitInput;
import com.github.jinahya.bit.io.DefaultBitOutput;
import com.github.jinahya.bit.io.StreamByteInput;
import com.github.jinahya.bit.io.StreamByteOutput;

import main.Main;
import main.MouseCorrectRobot;

public class Recorder {

	public static List<Action> actions = new ArrayList<>();
	public static long startTime;
	public static MouseCorrectRobot robot;
	public static boolean started = false;
	
	public static void toggle() {
		if(started) {
			stop();
			Main.icon.displayMessage("Stopping Recording", "Stopping Recording", MessageType.INFO);
		} else {
			Main.icon.displayMessage("Starting Recording", "Starting Recording", MessageType.INFO);
			start();
		}
	}
	
	public static void start() {
		actions.clear();
		startTime = System.currentTimeMillis();
		started = true;
		
		Point p = MouseInfo.getPointerInfo().getLocation();
		
		var mma = new MouseMoveAction();
		mma.x = (int) (p.x * MouseCorrectRobot.scaleFactor);
		mma.y = (int) (p.y * MouseCorrectRobot.scaleFactor);
		mma.timeStamp = 0;
		
		actions.add(mma);
	}
	
	public static void stop() {
		started = false;
	}
	
	public static void add(Action a) {
		if(started) {
			a.timeStamp = Math.max(0, System.currentTimeMillis() - startTime - 4);
			actions.add(a);
		}
	}
	
	public static void save(File f) throws Exception {
		//format:
		//32 bits - timestamp
		//3 bits - the type of action
		//remaining bits vary
		
		var fos = new FileOutputStream(f);
		var streamOutput = new StreamByteOutput(fos);
		BitOutput output = new DefaultBitOutput(streamOutput);
		
		output.writeInt32(actions.size());
		
		//compress these down as much as possible; use binary bits to save every last bit possible
		for(Action a: actions) {
			output.writeLong(false, 32, a.timeStamp);
			
			if (a instanceof KeyAction) {
				output.writeInt(true, 3, 0);
				
				KeyAction k = (KeyAction) a;
				
				output.writeInt(false, 18, k.keyCode);
				output.writeBoolean(k.press);
			} else if(a instanceof MouseAction){
				output.writeInt(true, 3, 1);
				
				MouseAction m = (MouseAction) a;
				
				output.writeInt(true, 3, m.button);
				output.writeBoolean(m.press);
			} else if(a instanceof MouseMoveAction) {
				output.writeInt(true, 3, 2);
				
				MouseMoveAction m = (MouseMoveAction) a;
				
				output.writeInt(false, 11, Math.max(0, m.x));
				output.writeInt(false, 11, Math.max(0, m.y));
			} else if(a instanceof ScrollAction) {
				output.writeInt(true, 3, 3);
				
				ScrollAction s = (ScrollAction) a;
				output.writeInt(false, 5, s.ticks);
			}
			
		}
		
		output.writeInt32(0);
		
		Main.icon.displayMessage("Saved recording to test.txt", "Share it with a friend to enjoy!", MessageType.INFO);
		
		fos.close();
	}
	
	public static void read(File f) throws Exception {
		var fis = new FileInputStream(f);
		BitInput input = new DefaultBitInput(new StreamByteInput(fis));
		
		int num = input.readInt32();
		
		System.out.println(num);
		
		for(int i = 0; i < num; i++) {
			long timestamp = input.readLong(false, 32);
			
			int type = input.readInt(true, 3);
			
			Action a = null;
			
			switch(type) {
			case 0:
				int code = input.readInt(false, 18);
				boolean press = input.readBoolean();
				
				var ka = new KeyAction();
				ka.keyCode = code;
				ka.press = press;
				
				a = ka;
				break;
			case 1:
				int button = input.readInt(true, 3);
				press = input.readBoolean();
				
				MouseAction ma = new MouseAction();
				ma.button = button;
				ma.press = press;
				
				a = ma;
				
				break;
			case 2:
				MouseMoveAction m = new MouseMoveAction();
				
				m.x = input.readInt(false, 11);
				m.y = input.readInt(false, 11);
				
				a = m;
				
				break;
			case 3:
				
				ScrollAction sa = new ScrollAction();
				
				sa.ticks = input.readInt(false, 5);
				
				break;
			}
			
			a.timeStamp = timestamp;
			actions.add(a);
		}
		
		Main.icon.displayMessage("Finished loading recording", "Press F10 to play it back", MessageType.INFO);
	}
	
	public static void playback() throws Exception {		
		if (actions.size() == 0)
			return;
		
		robot = new MouseCorrectRobot();
		
		startTime = System.currentTimeMillis();
		
		Thread.sleep(actions.get(0).timeStamp);
		actions.get(0).execute(robot);
		
		for (int i = 1, l = actions.size(); i < l; i++) {
			Thread.sleep(Math.max(0, actions.get(i).timeStamp + startTime - System.currentTimeMillis()));
			actions.get(i).execute(robot);
		}
		
		Main.icon.displayMessage("Playback completed", "Playback completed", MessageType.INFO);
	}
	
	public static void main(String[] args) throws Exception {
		Recorder.start();

		byte[] bytes = "Hello!".getBytes();
		for (byte b : bytes) {
			int code = b;
			// keycode only handles [A-Z] (which is ASCII decimal [65-90])
			if (code > 96 && code < 123)
				code = code - 32;
			
			System.out.println(code);

			KeyAction ka = new KeyAction();
			ka.keyCode = code;
			ka.press = true;
			Recorder.add(ka);

			Thread.sleep(10);

			KeyAction ka2 = new KeyAction(); ka2.keyCode = code; ka2.press = false;
			Recorder.add(ka2);
			
			Thread.sleep(10);
		}
		
		Recorder.save(new File("test.txt"));
		
		System.out.println("---File contents---");
		var fis = new FileInputStream("test.txt");
		BitInput input = new DefaultBitInput(new StreamByteInput(fis));
		
		while(fis.available() > 0) {
			boolean b = input.readBoolean();
			System.out.print(b ? 1 : 0);
		}
		
		System.out.println("--Read---");
		Recorder.read(new File("test.txt"));
		
		for(Action a: Recorder.actions) {
			System.out.println(a);
			KeyAction ka = (KeyAction) a;
			
			System.out.println((char)ka.keyCode);
		}
	}
	
}
