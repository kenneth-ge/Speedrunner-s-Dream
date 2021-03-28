package main;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

public class MouseCorrectRobot extends Robot
{
	
	public static double scaleFactor;
	
	{{
		var r = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
    	System.out.println("Resolution: " + r);
    	
    	scaleFactor = ((double) r) / 96.0;
	}}
	
    public MouseCorrectRobot() throws AWTException {
    	
    }

    @Override
    public void mouseMove(int x, int y) {
		int finalX = (int) (x * (1.0 / scaleFactor));//(x - deltaX * modFactor);
		int finalY = (int) (y * (1.0 / scaleFactor));//(y - deltaY * modFactor);

		super.mouseMove(finalX, finalY);
    }

}