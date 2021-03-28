package recorder;

import java.awt.Robot;

import main.Converter;

public class KeyAction extends Action {

	public int keyCode;
	public boolean press;
	
	@Override
	public String toString() {
		return "keyaction: " + timeStamp + " " + keyCode + " " + press;
	}

	@Override
	public void execute(Robot robot) {
		if (press)
			robot.keyPress(Converter.convertToAwt(keyCode));
		else
			robot.keyRelease(Converter.convertToAwt(keyCode));
	}
	
}
