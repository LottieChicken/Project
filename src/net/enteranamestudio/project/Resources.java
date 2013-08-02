package net.enteranamestudio.project;

import java.awt.Font;
import java.io.InputStream;

import net.enteranamestudio.project.network.packets.PacketDestroyTile;
import net.enteranamestudio.project.network.packets.PacketGame;
import net.enteranamestudio.project.network.packets.PacketInitPlayer;
import net.enteranamestudio.project.network.packets.PacketJoin;
import net.enteranamestudio.project.network.packets.PacketKeyCode;
import net.enteranamestudio.project.network.packets.PacketMessage;
import net.enteranamestudio.project.network.packets.PacketNewTile;
import net.enteranamestudio.project.network.packets.PacketPositionPlayer;
import net.enteranamestudio.project.tiles.Tile;

import org.newdawn.slick.Animation;
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
	public static Image playerInfo;
	public static Image lifeBar;
	public static Image lifeFull;
	
	public static SpriteSheet buttons;
	public static SpriteSheet tiles;
	public static SpriteSheet faces;
	public static SpriteSheet girl;
	
	public static Animation girlRunningAnimation;
	
	public static TrueTypeFont font;
	public static TrueTypeFont fontTextSize;
	public static TrueTypeFont fontMediumSize;
	
	public static void init() throws SlickException {
		background = new Image("res/background.png");
		background_grass = new Image("res/background_1.png");
		background_grass2 = new Image("res/background_2.png");
		playerInfo = new Image("res/components/player_info.png");
		lifeBar = new Image("res/components/life.png");
		lifeFull = new Image("res/components/life_full.png");
		
		buttons = new SpriteSheet("res/buttons.png", 150, 50);
		tiles = new SpriteSheet("res/tiles.png", 16, 16);
		faces = new SpriteSheet("res/players/faces.png", 160, 160);
		girl = new SpriteSheet("res/players/girl.png", 150, 150);
		
		girlRunningAnimation = new Animation();
		girlRunningAnimation.setAutoUpdate(true);
		
		for (int frame = 0; frame < 3; frame++) {
			girlRunningAnimation.addFrame(girl.getSprite(frame, 0), 120);
		}
		
	    try {
	        InputStream inputStream = ResourceLoader.getResourceAsStream("res/small_pixel.ttf");

	        Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
	        awtFont = awtFont.deriveFont(10f); 
	        font = new TrueTypeFont(awtFont, false);
	        
	        InputStream inputStream2 = ResourceLoader.getResourceAsStream("res/name.ttf");

	        Font awtFontMediumSize = Font.createFont(Font.TRUETYPE_FONT, inputStream2);
	        awtFontMediumSize = awtFontMediumSize.deriveFont(24f); 
	        fontMediumSize = new TrueTypeFont(awtFontMediumSize, false);
	        
	        InputStream inputStream3 = ResourceLoader.getResourceAsStream("res/small_pixel2.ttf");

	        Font awtFontTextSize = Font.createFont(Font.TRUETYPE_FONT, inputStream3);
	        awtFontTextSize = awtFontTextSize.deriveFont(16f); 
	        fontTextSize = new TrueTypeFont(awtFontTextSize, false);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }   
	}
	
	public static void register(Kryo kryo) {
		kryo.register(PacketGame.class);
		kryo.register(PacketJoin.class);
		kryo.register(PacketMessage.class);
		kryo.register(PacketInitPlayer.class);
		kryo.register(PacketKeyCode.class);
		kryo.register(PacketPositionPlayer.class);
		kryo.register(PacketNewTile.class);
		kryo.register(PacketDestroyTile.class);
		kryo.register(Map.class);
		kryo.register(Tile.class);
		kryo.register(Tile[].class);
		kryo.register(Tile[][].class);
		kryo.register(Image.class);
		kryo.register(Rectangle.class);
		kryo.register(float[].class);
	}

}
