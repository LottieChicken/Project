package net.enteranamestudio.project.network;

import java.io.IOException;
import java.util.HashMap;

import net.enteranamestudio.project.Map;
import net.enteranamestudio.project.Resources;
import net.enteranamestudio.project.entities.Player;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class ProjectServer {

	private Server server;
	
	public static Map map;
	
	public static HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	
	private static boolean running = true;
	
	public ProjectServer() throws IOException {
		map = new Map();
		map.generate();
		
		this.server = new Server();
		
		Kryo kryo = server.getKryo();
		Resources.register(kryo);
		
		this.server.addListener(new ServerListener(server));
		
		this.server.bind(20815, 20815);
		this.server.start();
	}
	
	public static void tick() {
		
	}
	
	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new ProjectServer();
		
		while (running) {
			tick();
			
			try {
				Thread.sleep(30);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
