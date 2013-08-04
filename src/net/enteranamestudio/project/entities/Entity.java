package net.enteranamestudio.project.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity {
	
	protected String name;
	
	protected short x;
	protected short y;
	protected Vector2f speed;
	protected short scale;
	
	protected Shape hitbox;
	
	protected Image image;
	protected Animation animation;
	
	protected boolean alive;
	protected boolean colidable;
	
	public abstract void tick(int delta);
	
	public void render(Graphics g) {
		if (image == null)
			this.animation.draw(x, y, animation.getWidth() * scale, animation.getHeight() * scale);
		
		if (animation == null)
			this.image.draw(x, y, scale);
	}
	
	public void physicManager(int delta) {
		
	}
	
	public void die() {
		this.alive = false;
	}

	public String getName() {
		return name;
	}

	public short getX() {
		return x;
	}

	public short getY() {
		return y;
	}

	public Vector2f getSpeed() {
		return speed;
	}

	public short getScale() {
		return scale;
	}

	public Shape getHitbox() {
		return hitbox;
	}

	public Image getImage() {
		return image;
	}

	public Animation getAnimation() {
		return animation;
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean isColidable() {
		return colidable;
	}
	
}
