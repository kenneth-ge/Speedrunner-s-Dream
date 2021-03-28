package recorder;

import java.awt.Robot;

import main.MouseCorrectRobot;

public class MouseMoveAction extends Action {

	public int x, y;
	
	@Override
	public String toString() {
		return "movemouse: " + timeStamp + " " + x + " " + y;
	}

	@Override
	public void execute(Robot robot) {
		robot.mouseMove(x, y);
	}
	
}
