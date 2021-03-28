package main;
import java.awt.*;
import java.awt.event.*;

public class Listener2 implements MouseListener {

	Robot robot = new Robot();
	
    public Listener2() throws AWTException {
        
    }
    
    public void mousePressed(MouseEvent e) {
    	if (e.getButton() != MouseEvent.BUTTON1)
    		return;
    	System.out.println("Mouse pressed");
    	int x = e.getXOnScreen();
    	int y = e.getYOnScreen();
    	robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    	robot.mouseMove(40, 160); // Replace these values with the right ones
    	robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        //robot.delay(200);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseMove(x, y);
    }
    
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse released");
    }
    
    public void mouseEntered(MouseEvent e) {
    	System.out.println("Mouse entered");
    }
    
    public void mouseExited(MouseEvent e) {
    	System.out.println("Mouse exited");
    }
    
    public void mouseClicked(MouseEvent e) {
    	System.out.println("Mouse clicked");
    }
    
}
