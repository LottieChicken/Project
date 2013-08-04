package net.enteranamestudio.project;

import java.awt.Dimension;

import net.enteranamestudio.project.network.ClientListener;
import net.enteranamestudio.project.network.packets.PacketJoin;
import net.enteranamestudio.project.states.Connect;
import net.enteranamestudio.project.states.Game;
import net.enteranamestudio.project.states.TitleScreen;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

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
			client = new Client();
			client.start();
			
			Kryo kryo = client.getKryo();
			Resources.register(kryo);
			
			client.addListener(new ClientListener());
			
			PacketJoin request = new PacketJoin();
			request.username = username;
			
			client.connect(10000, ip, port, port);
			client.sendTCP(request);
			
			Game.client = client;
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			client.close();
			
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
