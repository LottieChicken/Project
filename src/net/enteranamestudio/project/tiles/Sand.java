package net.enteranamestudio.project.tiles;

import net.enteranamestudio.project.Resources;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Sand extends Tile {
	
	public Sand(int x, int y) {
		this.name = "sand";
		
		this.x = x;
		this.y = y;
		this.hitbox = new Rectangle(x, y, 48, 48);
		
		this.orientation = 1;
	
		this.alive = true;
		this.colidable = false;
	}
	
	public void render(Graphics g) {
		Resources.tiles.getSprite(0, 0).draw(x, y, 3);
	}

}
