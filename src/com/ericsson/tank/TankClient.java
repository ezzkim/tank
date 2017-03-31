package com.ericsson.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

public class TankClient extends Frame {

	public static final int L = 800;
	public static final int H = 600;
	
	private Tank myTank = new Tank(50, 50, this);
	//public Missle m = null;
	public List<Missle> missles = new LinkedList<Missle>();
	private Image offScreenImage = null;
	
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.lunchFrame();
	}
	
	public void addMissle(Missle m) {
		missles.add(m);
	}
	
	public List<Missle> getMissles() {
		return missles;
	}
	
	public void lunchFrame() {
		this.setLocation(200, 100);
		this.setSize(L, H);
		this.setTitle("TankWar...");
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.addKeyListener(new KeyMonitor());
		this.setVisible(true);
		
		new Thread(new PaintThread()).start();
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawString("missle count : " + missles.size(), 5, 40);
		myTank.draw(g);
		for(Missle m : missles) {
		/*	if(m.isLive() == false) {
				missles.remove(m);
			}*/
			m.draw(g);
		}
	}

	@Override
	public void update(Graphics g) {  //by double buffer method 
		if(offScreenImage == null) {
			offScreenImage = this.createImage(L, H);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		
		// redraw the background
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GREEN);
		gOffScreen.fillRect(0, 0, L, H);
		gOffScreen.setColor(c);

		paint(gOffScreen);//user image's graphics to draw on image
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	private class PaintThread implements Runnable {
		@Override
		public void run() {
			while(true) {
				repaint(); // will call update and then call paint
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	private class KeyMonitor extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
	}
	
}
