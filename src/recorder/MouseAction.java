package recorder;

import java.awt.Robot;
import java.awt.event.InputEvent;

public class MouseAction extends Action {

	public int button;
	public boolean press;
	
	@Override
	public String toString() {
		return "mouseaction: " + timeStamp + " " + button + " " + press;
	}

	@Override
	public void execute(Robot robot) {
		if(press)
			robot.mousePress(InputEvent.getMaskForButton(button));
		else
			robot.mouseRelease(InputEvent.getMaskForButton(button));
	}
	
}
