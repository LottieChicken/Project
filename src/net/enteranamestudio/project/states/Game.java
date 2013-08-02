package net.enteranamestudio.project.states;

import java.awt.Dimension;

import net.enteranamestudio.project.Map;
import net.enteranamestudio.project.Project;
import net.enteranamestudio.project.Resources;
import net.enteranamestudio.project.entities.Player;
import net.enteranamestudio.project.network.packets.PacketMessage;
import net.enteranamestudio.project.util.Timer;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.esotericsoftware.kryonet.Client;

public class Game extends BasicGameState {
	
	public static Rectangle screen;
	
	public static Client client;
	
	public static Map map;
	public static int offX;
	public static int offY;
	
	public static Player player;
	public static Player other;
	
	public static String[] chatText = new String[9];
	
	private PacketMessage message = new PacketMessage();
	private TextField chatField;
	private boolean isChatVisible;
	private static boolean isTextVisible;
	private static Timer textVisibleDuration;
	
	private boolean verbose = false;
	
	public Game(Client _client) {
		client = _client;
	}

	public void init(GameContainer container, StateBasedGame arg1) throws SlickException {
		Resources.init();
		
		screen = new Rectangle(offX, offY, 816, 576);
		
		map = new Map();
		map.generate();
		
		offX = 0;
		offY = 0;
		
		player = new Player(1,"no name", 1, 0, 0);
		other = new Player(2, "no name", 1, 0, 0);
		
		for (int i = 0; i < chatText.length; i++) {
			chatText[i] = "";
		}
		
		this.chatField = new TextField(container, Resources.font, 10, 541, 200, 20);
		this.chatField.setBackgroundColor(Color.transparent);
		this.chatField.setBorderColor(Color.transparent);
		this.chatField.setMaxLength(80);
		this.isChatVisible = false;
		isTextVisible = true;
		textVisibleDuration = new Timer(10000);
		
		this.message.text = "";
	}

	public void render(GameContainer container, StateBasedGame arg1, Graphics g) throws SlickException {
		g.setFont(Resources.font);

		// MAP RENDER 
		g.translate(-offX, -offY);
		map.render(g);
		g.resetTransform();
		
		// PLAYERS RENDER
		g.setAntiAlias(true);
		
		other.render(g);
		
		player.render(g);
		
		// CHAT MESSAGES AND LOG RENDER
		if (isTextVisible) {
			for (int i = 0; i < chatText.length; i++) {
				g.drawString(chatText[i], 10, 521 - i * 20);
			}
		}
		
		// CHAT TEXT FIELD RENDER
		if (isChatVisible)
			this.chatField.render(container, g);
		
		// PLAYER INFO MENU RENDER
		Resources.playerInfo.draw();
		Resources.faces.getSprite(player.getPlayer() - 1, 0).draw(0, -5, 0.5f);
		Resources.lifeFull.draw(125, 9);
		Resources.lifeBar.draw(124, 8);
		g.drawString(""+player.getLife()+"/100", 160, 22);
		g.setFont(Resources.fontMediumSize);
		g.drawString(player.getName(), 80, 60);
		
		// VERBOSE
		if (verbose) {
			g.drawString("FPS : "+container.getFPS(), 10, 210);
			g.drawString("x : "+player.getX(), 10, 230);
			g.drawString("y : "+player.getY(), 10, 250);
			g.drawString("sx : "+offX, 10, 270);
			g.drawString("sy : "+offY, 10, 290);
			g.drawString("other : "+other.getName(), 10, 310);
			g.drawString("other x|y : "+other.getX()+" | "+other.getY(), 10, 330);
		}
	}

	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		
		// CHECK IF THE CURRENT STATE IS "GAME STATE"
		if (game.getCurrentStateID() == this.getID()) {
			// MANAGE SCROLLING 
			offX = player.getX() - 408;
			offY = player.getY() - 288;
			screen.setLocation(offX, offY);
			
			// MANAGE PLAYER
			player.tick(input, client, isChatVisible, delta);
			
			// MANAGE INPUTS
			this.inputHandler(input, container, game);
			
			// CHAT TEXT FIELD
			if (isChatVisible)
				this.chatField.setFocus(true);
			
			else
				this.chatField.setFocus(false);
			
			// THE TEXT VISIBLE DURATION
			textVisibleDuration.tick(delta);
			
			if (isTextVisible && textVisibleDuration.isComplete())
				isTextVisible = false;
		}
	}
	
	public void inputHandler(Input input, GameContainer container, StateBasedGame game) throws SlickException {
		if (input.isKeyPressed(Input.KEY_F)) {
			if (!isChatVisible) {
				if (game.getCurrentStateID() == this.getID()) {
					if (!container.isFullscreen() ) {
						Project.dimension = new Dimension(container.getScreenWidth(), container.getScreenHeight());
						AppGameContainer app = (AppGameContainer)container;
						app.setDisplayMode(Project.dimension.width, Project.dimension.height, true);
					}
					
					else {
						Project.dimension = new Dimension(816, 576);
						AppGameContainer app = (AppGameContainer)container;
						app.setDisplayMode(Project.dimension.width, Project.dimension.height, false);
					}
				}
			}
		}
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			client.close();
			game.enterState(0);
		}
		
		if (input.isKeyPressed(Input.KEY_RETURN)) {
			isTextVisible = true;
			textVisibleDuration.reset();
			textVisibleDuration.setDuration(10000);
			
			if (isChatVisible) {
				if (chatField.getText() != "" && chatField != null) {
					this.sendMessage(chatField.getText());
					this.chatField.setText("");		
					this.isChatVisible = false;
				}
				
				if (chatField.getText() == "" || chatField == null) {
					this.isChatVisible = false;
				}
			}
			
			else
				this.isChatVisible = true;
		}
		
		if (input.isKeyPressed(Input.KEY_V)) {
			if (!verbose)
				this.verbose = true;
			
			else
				this.verbose = false;
		}
	}
	
	public void sendMessage(String string) {
		message.text = string;
		client.sendTCP(message);
	}
	
	public static void addMessage(String m) {
		isTextVisible = true;
		textVisibleDuration.reset();
		textVisibleDuration.setDuration(10000);
		
		for (int i = chatText.length-1; i > 0; i--) {
			chatText[i] = chatText[i-1];
		}
		chatText[0] = m;
	}

	public int getID() {
		return 10;
	}

}
