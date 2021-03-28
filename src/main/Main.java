package main;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import recorder.Recorder;

public class Main {
	
	static NativeMouseListener mouseListener;
	static NativeKeyboardListener keyboardListener;
	static NativeScrollListener scrollListener;
	
	public static TrayIcon icon;

	public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Speedrunner's Dream");
        mouseListener = new NativeMouseListener();
        keyboardListener = new NativeKeyboardListener();
        scrollListener = new NativeScrollListener();

        // Set frame site
        frame.setMinimumSize(new Dimension(600, 400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		// AWT and Swing
		SystemTray tray = SystemTray.getSystemTray();

		Image image = Toolkit.getDefaultToolkit().createImage("icon.png");

		icon = new TrayIcon(image, "Speedrunner's Dream");
		icon.setImageAutoSize(true);
		tray.add(icon);
        
        JButton game = new JButton("Start/Stop Game");
        JButton recording = new JButton("Playback Recording");
        JButton save = new JButton("Save Recording");
        JButton load = new JButton("Load Recording");
        
        simplify(game, recording, save, load);
        
        game.addActionListener(a -> {
        	Recorder.toggle();
        });
        
        recording.addActionListener(a -> {
        	try {
				Recorder.playback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        });
        
        save.addActionListener(a -> {
        	try {
				Recorder.save(new File("test.txt"));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        });
        
        load.addActionListener(a -> {
        	try {
				Recorder.read(new File("test.txt"));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        });
        
        //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        
        JPanel panel = new JPanel();
        //panel.setPreferredSize(new Dimension(400, 400));
        //panel.setBorder(BorderFactory.createTitledBorder("bordertitle"));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JPanel horiz = new JPanel();
        horiz.setLayout(new BoxLayout(horiz, BoxLayout.X_AXIS));
        
        JPanel btns = new JPanel();
        btns.setLayout(new BoxLayout(btns, BoxLayout.Y_AXIS));
        
        //horiz.setMinimumSize(new Dimension(500, 200));
        
        btns.add(game, BorderLayout.CENTER);
        btns.add(recording, BorderLayout.CENTER);
        btns.add(save, BorderLayout.CENTER);
        btns.add(load, BorderLayout.CENTER);
        
        horiz.add(Box.createHorizontalGlue());
        horiz.add(btns);
        horiz.add(Box.createHorizontalGlue());
        
        panel.add(horiz);
        
        game.setAlignmentX(Component.CENTER_ALIGNMENT);
        recording.setAlignmentX(Component.CENTER_ALIGNMENT);
        game.setAlignmentY(Component.CENTER_ALIGNMENT);
        recording.setAlignmentY(Component.CENTER_ALIGNMENT);
        save.setAlignmentX(Component.CENTER_ALIGNMENT);
        load.setAlignmentX(Component.CENTER_ALIGNMENT);
        save.setAlignmentY(Component.CENTER_ALIGNMENT);
        load.setAlignmentY(Component.CENTER_ALIGNMENT);
        
        JLabel instructions = new JLabel("Press F12 to toggle recording and use F10 to play back the recording.", SwingConstants.CENTER);
        panel.add(instructions, BorderLayout.CENTER);
        
        setup();
        
        frame.setContentPane(panel);
        
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                tray.remove(icon);
            }
        });
        
        // Display it
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	
	public static void simplify(JButton... buttons) {
		/*for(JButton b: buttons) {
			b.setBorderPainted(false);
			b.setFocusPainted(false);
			b.setContentAreaFilled(false);
		}*/
	}
	
	public static void setup() {
		// Clear spam
		// Clear previous logging configurations.
		LogManager.getLogManager().reset();

		// Get the logger for "org.jnativehook" and set the level to off.
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}
		GlobalScreen.addNativeMouseListener(mouseListener);
		GlobalScreen.addNativeMouseMotionListener(mouseListener);
		GlobalScreen.addNativeKeyListener(keyboardListener);
		GlobalScreen.addNativeMouseWheelListener(scrollListener);
	}
	
}
