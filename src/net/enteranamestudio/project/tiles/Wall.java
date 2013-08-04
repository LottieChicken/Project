package net.enteranamestudio.project.tiles;

import net.enteranamestudio.project.Resources;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Wall extends Tile {
	
	//private int type;
	
	public Wall(int x, int y, int type, int orientation) {
		this.name = "wall";
		
		this.x = (short)x;
		this.y = (short)y;
		this.hitbox = new Rectangle((short)x, (short)y, 48, 48);
		
		this.orientation = orientation;
	//	this.type  = type;
	
		this.alive = true;
		this.colidable = false;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		Resources.tiles.getSprite(orientation, 1).draw(x, y, 3);
	}

}
