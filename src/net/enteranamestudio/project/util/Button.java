package net.enteranamestudio.project.util;

import net.enteranamestudio.project.Resources;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

public class Button {
	
	private int x;
	private int y;
	private int id;
	private Rectangle hitbox;
	
	public Button(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.hitbox = new Rectangle(x, y, 150, 50);
	}
	
	public boolean isClicked(Input input) {
		if (hitbox.contains(input.getMouseX(), input.getMouseY()) && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) 
			return true;
		
		else
			return false;
	}
	
	public void render(Graphics g) {
		Resources.buttons.getSprite(0, id).draw(x, y);;
	}

	public int getID() {
		return id;
	}
	
}
