package com.ericsson.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Tank {

	private static final int XSPEED = 5;
	private static final int YSPEED = 5;
	
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	private TankClient tc = null;
	
	private int x;
	private int y;
	
	private boolean good;
	private boolean live = true;
	
	

	private boolean bL = false;
	private boolean bU = false;
	private boolean bR = false;
	private boolean bD = false;
	
	enum Direction {
		L,LU,LD,R,
		RU,RD,U,D,
		STOP
	};
	
	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;
	
	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
	}
	
	public Tank(int x, int y, boolean good, TankClient tc) {
		this(x, y, good);
		this.tc = tc;
	}
	
	public void setLive(boolean live) {
		this.live = live;
	}
	
	public boolean isLive() {
		return live;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			if(!good) {
				tc.tanks.remove(this);
			} 
			return;
		}
		
		Color c = g.getColor();
		if(good) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.BLUE);
		}
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		switch(ptDir) {
		case L:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT/2);
			break;
		case LU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y);
			break;
		case LD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT/2);
			break;
		case R:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y + Tank.HEIGHT/2);
			break;
		case RU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y);
			break;
		case RD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y + Tank.HEIGHT);
			break;
		case U:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y);
			break;
		case D:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y + Tank.HEIGHT);
			break;
		}
		
		move();
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		
		locateDirection();
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_CONTROL:
			//Missle m = fire();
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		
		locateDirection();
	}
	
	public void locateDirection() {
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		else if(bL && bU && !bR && !bD) dir = Direction.LU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
	}
	
	public Missle fire() {
		int lx = x + Tank.WIDTH/2 - Missle.WIDTH/2;
		int ly = y + Tank.HEIGHT/2 - Missle.HEIGHT/2;
		//Missle m = new Missle(lx, ly, ptDir);
		Missle m = new Missle(lx, ly, ptDir, tc);
		tc.addMissle(m);
		System.out.println("tank fired");
		return m;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	//////////////private//////////////
	
	private void move() {
		switch(dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case STOP:
			break;
		}
		
		if(this.dir != Direction.STOP) {
			this.ptDir = this.dir;
		}
		
		if(x<2) x = 2;
		if(y<24) y = 24;
		if(x + Tank.WIDTH + 2> TankClient.L) x = TankClient.L - Tank.WIDTH - 2;
		if(y + Tank.HEIGHT + 2> TankClient.H) y = TankClient.H - Tank.HEIGHT - 2;
	}

}
