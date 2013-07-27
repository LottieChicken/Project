package net.enteranamestudio.project.tiles;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Tile {
	
	protected String name;
	
	protected int x;
	protected int y;
	protected Rectangle hitbox;
	
	protected int orientation; 
	
	protected boolean alive;
	protected boolean colidable;
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {

	}

	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean isColidable() {
		return colidable;
	}

}
