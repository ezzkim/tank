package com.ericsson.tank;

import java.awt.Color;
import java.awt.Graphics;

import com.ericsson.tank.Tank.Direction;

public class Missle {

	private static final int XSPEED = 10;
	private static final int YSPEED = 10;
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	private int x;
	private int y;
	private Tank.Direction dir;
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
	
	public Missle(int x, int y, Direction dir, TankClient tc) {
		this(x,y,dir);
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		move();
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
