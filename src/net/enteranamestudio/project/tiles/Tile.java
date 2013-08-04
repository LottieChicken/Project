package net.enteranamestudio.project.tiles;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public abstract class Tile {
	
	protected String name;
	
	protected short x;
	protected short y;
	protected Rectangle hitbox;
	
	protected int orientation; 
	
	protected boolean alive;
	protected boolean colidable;
	
	public abstract void tick();
	
	public abstract void render(Graphics g);

	public String getName() {
		return name;
	}

	public short getX() {
		return x;
	}

	public short getY() {
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
