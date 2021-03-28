package recorder;

import java.awt.Robot;

public abstract class Action {

	public long timeStamp;
	
	public abstract void execute(Robot robot);
	
}
