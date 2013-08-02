package net.enteranamestudio.project.entities;

import net.enteranamestudio.project.Resources;
import net.enteranamestudio.project.network.packets.PacketKeyCode;
import net.enteranamestudio.project.states.Game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.esotericsoftware.kryonet.Client;

public class Player {
	
	private int id;
	private String name;
	private int player;
	
	private int x;
	private int y;
	private float angle;
	private Vector2f speed;
	private Rectangle hitbox;
	
	private int life;
	
	private boolean alive;
	private boolean run;
	private boolean attack;
	private boolean init;
	
	public Player(int id, String name, int player, int x, int y) {
		this.id = id;
		this.name = name;
		this.player = player;
		
		this.x = x;
		this.y = y;
		this.angle = 0;
		this.speed = new Vector2f(0, 0);
		this.hitbox = new Rectangle(x, y, 32, 32);
		
		this.life = 100;
		
		this.alive = true;
		this.run = false;
		this.attack = false;
		this.init = false;
	}
	
	public void tick(Input input, Client client, boolean isChatVisible, int delta) {
		// CALCULATE THE ANGLE BETWEEN PLAYER'S POSITION AND MOUSE POSITION
		Vector2f mouse = new Vector2f(input.getMouseX() + Game.offX, input.getMouseY() + Game.offY);
		float radian = (float) Math.atan2(this.x - mouse.x, this.y - mouse.y);
		this.angle = (float) Math.toDegrees(radian);
		
		// HANDLE INPUT
		if (!isChatVisible)
			this.input(input, client, delta);
		
		// UPDATE HITBOX LOCATION
		this.tickHitbox();
	}
	
	public void render(Graphics g) {
		if (init) {
		//	g.fill(hitbox);
			
			if (player == 1) {
				g.rotate(x - Game.offX + 29, y - Game.offY + 20, -angle);
				
				if (!run)
					Resources.girl.getSprite(0, 0).draw(x - Game.offX, y - Game.offY, 0.4f);   
				
				else 
					Resources.girlRunningAnimation.draw(x - Game.offX, y - Game.offY, 150 * 0.4f, 150 * 0.4f);
				
				g.resetTransform();
			}
		}
	}
	
	public void input(Input input, Client client, int delta) {
		if (input.isKeyDown(Input.KEY_Z)) {
			this.run = true;
			
			PacketKeyCode keyCode = new PacketKeyCode();
			keyCode.keyCode = Input.KEY_Z;
			keyCode.angle = this.angle;
			keyCode.delta = (byte) delta;
			client.sendUDP(keyCode);
		}
		
		else if (input.isKeyDown(Input.KEY_Q)) {
			this.run = true;
			
			PacketKeyCode keyCode = new PacketKeyCode();
			keyCode.keyCode = Input.KEY_Q;
			keyCode.angle = this.angle;
			keyCode.delta = (byte) delta;
			client.sendUDP(keyCode);
		}
		
		else if (input.isKeyDown(Input.KEY_S)) {
			this.run = true;
			
			PacketKeyCode keyCode = new PacketKeyCode();
			keyCode.keyCode = Input.KEY_S;
			keyCode.angle = this.angle;
			keyCode.delta = (byte) delta;
			client.sendUDP(keyCode);
		}
		
		else if (input.isKeyDown(Input.KEY_D)) {
			this.run = true;
			
			PacketKeyCode keyCode = new PacketKeyCode();
			keyCode.keyCode = Input.KEY_D;
			keyCode.angle = this.angle;
			keyCode.delta = (byte) delta;
			client.sendUDP(keyCode);
		}
		
		else
			this.run = false;
	}
	
	public void move(int delta, float angle, int direction) {
		if (direction == 0) {
			this.speed.y = 0.2f;
			
			this.x -= (Math.sin(Math.toRadians(angle)) * speed.y) * delta;
			this.y -= (Math.cos(Math.toRadians(angle)) * speed.y) * delta;
		}
		
		if (direction == 1) 
			this.x -= (Math.sin(Math.toRadians(angle)) * speed.y) * delta;
		
		if (direction == 2) {
			this.speed.y = 0.2f;
			
			this.x += (Math.sin(Math.toRadians(angle)) * speed.y) * delta;
			this.y += (Math.cos(Math.toRadians(angle)) * speed.y) * delta;
		}
		
		if (direction == 3) 
			this.x += (Math.sin(Math.toRadians(angle)) * speed.y) * delta;
		
		if (x < 0)
			this.x = 0;
		
		if (x + 42 > 2400)
			this.x = 2400 - 42;
		
		if (y < 0)
			this.y = 0;
		
		if (y + 42 > 2400)
			this.y = 2400 - 42;
	}
	
	public void tickHitbox() {
		this.hitbox.setX(x);
		this.hitbox.setY(y);
	}
	
	public void init(int id, String name, int player, int x, int y) {
		this.id = id;
		this.name = name;
		this.player = player;
		
		this.x = x;
		this.y = y;
		this.hitbox.setLocation(x, y);
		
		this.init = true;
	}
	
	public void die() {
		this.alive = false;
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		this.hitbox.setLocation(x, y);
	}
	
	public void setLocation(int x, int y, float angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.hitbox.setLocation(x, y);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPlayer() {
		return player;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Vector2f getSpeed() {
		return speed;
	}

	public int getLife() {
		return life;
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean isRun() {
		return run;
	}

	public boolean isAttack() {
		return attack;
	}

	public boolean isInit() {
		return init;
	}

	public float getAngle() {
		return angle;
	}

}
