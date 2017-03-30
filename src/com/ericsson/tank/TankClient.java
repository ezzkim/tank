package com.ericsson.tank;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame {

	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lunchFrame();
	}
	
	public void lunchFrame() {
		this.setLocation(200, 100);
		this.setSize(800, 600);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		
		this.setVisible(true);
	}

}
