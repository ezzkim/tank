package com.ericsson.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import com.ericsson.tank.Tank.Direction;

public class Missle {

	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	private int x;
	private int y;
	private Tank.Direction dir;
	private boolean good;
	private boolean live = true;
	private TankClient tc;

	public boolean isLive() {
		return live;
	}

	public Missle(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missle(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x,y,dir);
		this.good = good;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			tc.missles.remove(this);
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		move();
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean hitTank(Tank t) {
		if(this.live && getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()) {
			t.setLive(false);
			Explode e = new Explode(x,y,tc);
			tc.explodes.add(e);
			live = false;
			return true;
		}
		
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks) {
		for(Tank t : tanks) {
			if(hitTank(t)) {
				return true;
			}
		}
		
		return false;
	}
	
	///////////private//////////

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
		}
	
		if(x < 0 || y <0 || x>TankClient.L || y>TankClient.H) {
			live = false;
			tc.missles.remove(this);
		}
	}
	
}
