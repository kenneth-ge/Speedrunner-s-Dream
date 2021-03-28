package main;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

public class Icon {

	public static void main(String[] args) throws Exception {
		//AWT and Swing
		SystemTray tray = SystemTray.getSystemTray();
		
		Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
		
		TrayIcon icon = new TrayIcon(image, "System Tray Icon");
		icon.setImageAutoSize(true);
		tray.add(icon);
		
		icon.displayMessage("Warning", "Your computer is about to expload", MessageType.INFO);
	}
	
}
