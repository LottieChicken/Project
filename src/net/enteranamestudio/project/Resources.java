package net.enteranamestudio.project;

import java.awt.Font;
import java.io.InputStream;

import net.enteranamestudio.project.network.packets.PacketDestroyTile;
import net.enteranamestudio.project.network.packets.PacketGame;
import net.enteranamestudio.project.network.packets.PacketJoin;
import net.enteranamestudio.project.network.packets.PacketMessage;
import net.enteranamestudio.project.network.packets.PacketNewTile;
import net.enteranamestudio.project.network.packets.PacketInitPlayer;
import net.enteranamestudio.project.network.packets.PacketPositionPlayer;
import net.enteranamestudio.project.tiles.Sand;
import net.enteranamestudio.project.tiles.Tile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.ResourceLoader;

import com.esotericsoftware.kryo.Kryo;

public class Resources {
	
	public static Image background;
	public static Image background_grass;
	public static Image background_grass2;
	
	public static SpriteSheet buttons;
	public static SpriteSheet tiles;
	
	public static TrueTypeFont font;
	public static TrueTypeFont fontMediumSize;
	
	public static void init() throws SlickException {
		background = new Image("res/background.png");
		background_grass = new Image("res/background_1.png");
		background_grass2 = new Image("res/background_2.png");
		
		buttons = new SpriteSheet("res/buttons.png", 150, 50);
		tiles = new SpriteSheet("res/tiles.png", 16, 16);
		
	    try {
	        InputStream inputStream = ResourceLoader.getResourceAsStream("res/small_pixel.ttf");

	        Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
	        awtFont = awtFont.deriveFont(10f); // set font size
	        font = new TrueTypeFont(awtFont, false);
	        
	        InputStream inputStream2 = ResourceLoader.getResourceAsStream("res/small_pixel2.ttf");

	        Font awtFontMediumSize = Font.createFont(Font.TRUETYPE_FONT, inputStream2);
	        awtFontMediumSize = awtFontMediumSize.deriveFont(16f); // set font size
	        fontMediumSize = new TrueTypeFont(awtFontMediumSize, false);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }   
	}
	
	public static void register(Kryo kryo) {
		kryo.register(PacketGame.class);
		kryo.register(PacketJoin.class);
		kryo.register(PacketMessage.class);
		kryo.register(PacketInitPlayer.class);
		kryo.register(PacketPositionPlayer.class);
		kryo.register(PacketNewTile.class);
		kryo.register(PacketDestroyTile.class);
		kryo.register(Map.class);
		kryo.register(Tile.class);
		kryo.register(Tile[].class);
		kryo.register(Tile[][].class);
		kryo.register(Sand.class);
		kryo.register(Image.class);
		kryo.register(Rectangle.class);
		kryo.register(float[].class);
	}

}
