package recorder;

import java.awt.Robot;

public class ScrollAction extends Action {

	public int ticks;
	
	@Override
	public String toString() {
		return "scroll: " + timeStamp + " " + ticks;
	}

	@Override
	public void execute(Robot robot) {
		robot.mouseWheel(ticks);
	}
	
}
