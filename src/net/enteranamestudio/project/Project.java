package net.enteranamestudio.project;

import java.awt.Dimension;

import net.enteranamestudio.project.network.packets.PacketDeletePlayer;
import net.enteranamestudio.project.network.packets.PacketDestroyTile;
import net.enteranamestudio.project.network.packets.PacketGame;
import net.enteranamestudio.project.network.packets.PacketInitPlayer;
import net.enteranamestudio.project.network.packets.PacketJoin;
import net.enteranamestudio.project.network.packets.PacketMessage;
import net.enteranamestudio.project.network.packets.PacketNewTile;
import net.enteranamestudio.project.network.packets.PacketPositionPlayer;
import net.enteranamestudio.project.states.Connect;
import net.enteranamestudio.project.states.Game;
import net.enteranamestudio.project.states.TitleScreen;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class Project extends StateBasedGame {
	
	public static Dimension dimension = new Dimension(816, 576);
	
	public static Client client;
	
	public Project() {
		super("Project - Client");
	}
	
	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new TitleScreen()); // 0
		addState(new Connect(this)); // 1
		
		addState(new Game(client)); // 10
	}
	
	public boolean connectToServer(String username, String ip, int port) {
		try {
			this.client = new Client();
			this.client.start();
			
			Kryo kryo = client.getKryo();
			Resources.register(kryo);
			
			this.client.addListener(new Listener() {
				public void received(Connection connection , Object object) {
					if (object instanceof PacketGame) {
						
					}
					
					if (object instanceof PacketNewTile) {
						
					}
					
					if (object instanceof PacketDestroyTile) {
						
					}
					
					if (object instanceof PacketMessage) {
						PacketMessage message = (PacketMessage)object;
						Game.addMessage(message.text);
					}
					
					if (object instanceof PacketInitPlayer) {
						PacketInitPlayer packetPlayer = (PacketInitPlayer)object;
						
						if (!Game.player.isInit()) {
							Game.player.init(packetPlayer.id, packetPlayer.name, packetPlayer.x, packetPlayer.y);
						}
						
						else
							Game.other.init(packetPlayer.id, packetPlayer.name, packetPlayer.x, packetPlayer.y);
					}
					
					if (object instanceof PacketPositionPlayer) {
						PacketPositionPlayer pos = (PacketPositionPlayer)object;
						
						Game.other.setLocation(pos.x, pos.y);
					}
					
					if (object instanceof PacketDeletePlayer) {
						PacketDeletePlayer player = (PacketDeletePlayer)object;
						
						if (Game.other.getId() == player.id)
							Game.other = null;
					}
				}
			});
			
			PacketJoin request = new PacketJoin();
			request.username = username;
			
			this.client.connect(10000, ip, port);
			this.client.sendTCP(request);
			
			Game.client = this.client;
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.client.close();
			
			return false;
		}
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Project());
		
		app.setDisplayMode(dimension.width, dimension.height, false);
		app.setTargetFrameRate(60);
		app.setShowFPS(false);
		app.start();
	}
	
}
