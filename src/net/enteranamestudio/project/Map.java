package net.enteranamestudio.project;

import java.util.Random;

import net.enteranamestudio.project.states.Game;
import net.enteranamestudio.project.tiles.Flag;
import net.enteranamestudio.project.tiles.Tile;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class Map {
	
	private final int MAP_WIDTH = 50;
	private final int MAP_HEIGHT = 50;
	
	private int[][] background;
	private Tile[][] tiles = new Tile[MAP_WIDTH][MAP_HEIGHT];
	
	private int spawn1X;
	private int spawn1Y;
	
	private int spawn2X;
	private int spawn2Y;
	
	private boolean loaded = false;
	
	public Map() {
		this.background = new int[2400][2400];
	}
	
	public void generate() {
		spawn1X = 8 * 48;
		spawn1Y = 8* 48;
		spawn2X = 42 * 48;
		spawn2Y = 42 * 48;
		
		for (int x = 0; x < 2400; x += 400) {
			for (int y = 0; y < 2400; y++) {
				int random = this.getRandomBetween(2, 1);
				
				this.background[x][y] = random;
			}
		}
		
		this.tiles[8][8] = new Flag(spawn1X, spawn1Y);
		this.tiles[42][42] = new Flag(spawn2X, spawn2Y);
		
		this.loaded = true;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		for (int x = 0; x < 2400; x += 400) {
			for (int y = 0; y < 2400; y += 400) {
				if (background[x][y] == 1 && Game.screen.intersects(new Rectangle(x, y, 400, 400))) 
					Resources.background_grass.draw(x, y);
				
				else if (background[x][y] == 2)
					Resources.background_grass2.draw(x, y);
			}
		}
		
		for (int x = 0; x < MAP_WIDTH; x++) {
			for (int y = 0; y < MAP_HEIGHT; y++) {
				if (tiles[x][y] != null && Game.screen.intersects(tiles[x][y].getHitbox())) 
					this.tiles[x][y].render(g);
			}
		}
	}
	
	public void clearMap() {
		for (int x = 0; x < MAP_WIDTH; x++) {
			for (int y = 0; y < MAP_HEIGHT; y++) {
				this.tiles[x][y] = null;
			}
		}
	}
	
	public int getRandomBetween(int max, int min) {
		return min + new Random().nextInt((max + 1) - min);
	}
	
	public void setTiles(Tile[][] newTiles) {
		this.tiles = newTiles;
	}
	
	public void setSpawn1X(int spawn1x) {
		spawn1X = spawn1x;
	}

	public void setSpawn1Y(int spawn1y) {
		spawn1Y = spawn1y;
	}

	public void setSpawn2X(int spawn2x) {
		spawn2X = spawn2x;
	}

	public void setSpawn2Y(int spawn2y) {
		spawn2Y = spawn2y;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public int getSpawn1X() {
		return spawn1X;
	}

	public int getSpawn1Y() {
		return spawn1Y;
	}

	public int getSpawn2X() {
		return spawn2X;
	}

	public int getSpawn2Y() {
		return spawn2Y;
	}
	
	public boolean isLoaded() {
		return loaded;
	}

}
