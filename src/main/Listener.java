package main;
import java.awt.*;
import java.awt.event.*;

public class Listener implements KeyListener {
    
    public Listener() {
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    	System.out.println("Key Pressed");
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key Released");
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    	System.out.println("Key Typed");
    }
    
}
