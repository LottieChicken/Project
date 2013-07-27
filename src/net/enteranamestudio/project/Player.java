package net.enteranamestudio.project;

import net.enteranamestudio.project.network.packets.PacketPositionPlayer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import com.esotericsoftware.kryonet.Client;

public class Player {
	
	private int id;
	private String name;
	
	private int x;
	private int y;
	private Rectangle hitbox;
	private Vector2f speed;
	
	private boolean alive;
	private boolean run;
	private boolean attack;
	private boolean init;
	
	public Player(int id, String name, int x, int y) {
		this.id = id;
		this.name = name;
		
		this.x = x;
		this.y = y;
		this.hitbox = new Rectangle(x, y, 32, 32);
		this.speed = new Vector2f(0, 0);
		
		this.alive = true;
		this.run = false;
		this.attack = false;
		this.init = false;
	}
	
	public void tick(Input input, Client client, int delta) {
		this.input(input, client, delta);
		this.tickHitbox();
	}
	
	public void render(Graphics g) {
		if (init)
			g.fill(hitbox);
	}
	
	public void input(Input input, Client client, int delta) {
		if (input.isKeyDown(Input.KEY_Z)) {
			this.y -= delta * 0.1f;
			
			PacketPositionPlayer pos = new PacketPositionPlayer();
			pos.x = this.x;
			pos.y = this.y;
			Project.client.sendTCP(pos);
		}
		
		if (input.isKeyDown(Input.KEY_Q)) {
			this.x -= delta * 0.1f;
			
			PacketPositionPlayer pos = new PacketPositionPlayer();
			pos.x = this.x;
			pos.y = this.y;
			Project.client.sendTCP(pos);
		}
		
		if (input.isKeyDown(Input.KEY_S)) {
			this.y += delta * 0.2f;
			
			PacketPositionPlayer pos = new PacketPositionPlayer();
			pos.x = this.x;
			pos.y = this.y;
			Project.client.sendTCP(pos);
		}
		
		if (input.isKeyDown(Input.KEY_D)) {
			this.x += delta * 0.2f;
			
			PacketPositionPlayer pos = new PacketPositionPlayer();
			pos.x = this.x;
			pos.y = this.y;
			Project.client.sendTCP(pos);
		}
	}
	
	public void tickHitbox() {
		this.hitbox.setX(x);
		this.hitbox.setY(y);
	}
	
	public void init(int id, String name, int x, int y) {
		this.id = id;
		this.name = name;
		
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

	public int getId() {
		return id;
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

	public Vector2f getSpeed() {
		return speed;
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

}
