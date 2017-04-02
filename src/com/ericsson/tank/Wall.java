package com.ericsson.tank;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall {

	private int x;
	private int y;
	private int w;
	private int h;
	private TankClient t;
	
	public Wall(int x, int y, int w, int h, TankClient t) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.t = t;
	}
	
	public void draw(Graphics g) {
		g.fillRect(x, y, w, h);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x,y,w,h);
	}
	
}
