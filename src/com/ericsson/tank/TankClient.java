package com.ericsson.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TankClient extends Frame {

	public static final int L = 800;
	public static final int H = 600;
	
	Wall w1 = new Wall(300,200,20,150,this);
	Wall w2 = new Wall(300,100,300,20,this);
	
	private Tank myTank = new Tank(100, 50, true, Tank.Direction.STOP, this);
	//private Tank enemyTank = new Tank(100, 100, false, this);
	
	public List<Tank> tanks = new ArrayList<Tank>();
	public List<Missle> missles = new LinkedList<Missle>();
	public List<Explode> explodes = new ArrayList<Explode>();
	private Image offScreenImage = null;
	
	Explode e = new Explode(70, 70, this);
	
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
		//add 10 enemy tank
		for(int i=0; i<10; i++) {
			tanks.add(new Tank(50 + 40*(i+1), 50, false, Tank.Direction.D, this));
		}
		
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
		g.drawString("explodes count : " + explodes.size(), 150, 40);
		g.drawString("tank count : " + tanks.size(), 295, 40);
		
		for(Missle m : missles) {
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.draw(g);
		}
		
		for(Explode e : explodes) {
			e.draw(g);
		}
		
		for(Tank t : tanks) {
			t.hitWall(w1);
			t.hitWall(w2);
			t.draw(g);
		}
		
		myTank.draw(g);
		//myTank.hitWall(w1);
		//myTank.hitWall(w2);
		
		w1.draw(g);
		w2.draw(g);
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
