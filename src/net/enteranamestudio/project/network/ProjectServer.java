package net.enteranamestudio.project.network;

import java.io.IOException;
import java.util.HashMap;

import net.enteranamestudio.project.Map;
import net.enteranamestudio.project.Resources;
import net.enteranamestudio.project.entities.Player;
import net.enteranamestudio.project.network.packets.PacketDeletePlayer;
import net.enteranamestudio.project.network.packets.PacketDestroyTile;
import net.enteranamestudio.project.network.packets.PacketInitPlayer;
import net.enteranamestudio.project.network.packets.PacketJoin;
import net.enteranamestudio.project.network.packets.PacketKeyCode;
import net.enteranamestudio.project.network.packets.PacketMessage;
import net.enteranamestudio.project.network.packets.PacketNewTile;
import net.enteranamestudio.project.network.packets.PacketPositionPlayer;
import net.enteranamestudio.project.states.Connect;

import org.newdawn.slick.Input;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class ProjectServer {

	private Server server;
	
	private Map map;
	
	private HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	
	private static boolean running = true;
	
	public ProjectServer() throws IOException {
		this.map = new Map();
		this.map.generate();
		
		this.server = new Server();
		
		Kryo kryo = server.getKryo();
		Resources.register(kryo);
		
		this.server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				// WHEN THE SERVER RECEIVED A NEW CONNECTION
				if (object instanceof PacketJoin) {
					PacketJoin packetJoin = (PacketJoin)object;
					
					if (verify(packetJoin.username, connection)) {
						Player player = null;
						
						if (players.size() == 0)
							player = new Player((byte) connection.getID(), packetJoin.username, 1, map.getSpawn1X(), map.getSpawn1Y());
						
						else if (players.size() == 1)
							player = new Player((byte) connection.getID(), packetJoin.username, 1, map.getSpawn2X(), map.getSpawn2Y());
						
						PacketInitPlayer packetPlayer = new PacketInitPlayer();
						packetPlayer.id = player.getId();
						packetPlayer.name = player.getName();
						packetPlayer.player = player.getPlayer();
						packetPlayer.x = player.getX();
						packetPlayer.y = player.getY();
						
						server.sendToAllTCP(packetPlayer);
						
						int index = 0;
						
						if (players.size() == 1) {
							Player other = null;
							
							if (giveNotNull() != 0)
								index = giveNotNull();
							
							other = (Player)players.get(index);
							
							PacketInitPlayer otherPlayer = new PacketInitPlayer();
							otherPlayer.id = other.getId();
							otherPlayer.name = other.getName();
							otherPlayer.x = other.getX();
							otherPlayer.y = other.getY();
							
							server.sendToTCP(connection.getID(), otherPlayer);
						}
						
						players.put(connection.getID(), player);
						
						PacketMessage message = new PacketMessage();
						PacketMessage messageToOther = new PacketMessage();
						message.text = "[SERVER] You have connected!";
						messageToOther.text = "[SERVER] "+packetJoin.username + " " + "has connected";
						
						connection.sendTCP(message);
						server.sendToAllExceptTCP(connection.getID(), messageToOther);
					} else {
						connection.close();
					}
				}
				
				// WHEN THE SERVER REVEIVED A CHANGE IN THE MAP
				if (object instanceof PacketNewTile) {
					
				}
				
				// WHEN THE SERVER REVEIVED A CHANGE IN THE MAP
				if (object instanceof PacketDestroyTile) {
					
				}
				
				// WHEN THE SERVER RECEIVED A MESSAGE
				if (object instanceof PacketMessage) {
					PacketMessage message = (PacketMessage)object;
					message.text = ((Player)players.get(connection.getID())).getName() + " : "+ message.text;
					
					server.sendToAllTCP(message);
				}
				
				// WHEN THE SERVER RECEIVED A KEY PRESSED
				if (object instanceof PacketKeyCode) {
					PacketKeyCode keyCode = (PacketKeyCode)object;
					PacketPositionPlayer position = new PacketPositionPlayer();
					Player player = (Player)players.get(connection.getID());
					
					switch (keyCode.keyCode) {
						case Input.KEY_Z:
							player.move(keyCode.delta, keyCode.angle, 0);
							position = wrapPosition(player);
							server.sendToAllUDP(position);
							break;
						case Input.KEY_Q:
							player.move(keyCode.delta, keyCode.angle, 1);
							position = wrapPosition(player);
							server.sendToAllUDP(position);
							break;
						case Input.KEY_S:
							player.move(keyCode.delta, keyCode.angle, 2);
							position = wrapPosition(player);
							server.sendToAllUDP(position);
							break;
						case Input.KEY_D:
							player.move(keyCode.delta, keyCode.angle, 3);
							position = wrapPosition(player);
							server.sendToAllUDP(position);
							break;
					}
				}
			}
			
			// WHEN SOMEONE DISCONECT
			public void disconnected(Connection connection) {
				PacketMessage message = new PacketMessage();
				message.text = "[SERVER] " + ((Player)players.get(connection.getID())).getName() + " has disconnected :-(";
				
				server.sendToAllExceptTCP(connection.getID(), new PacketDeletePlayer().id = connection.getID());
				server.sendToAllExceptTCP(connection.getID(), message);
				
				players.remove(connection.getID());
			}
		});
		
		this.server.bind(20815, 20815);
		this.server.start();
	}
	
	public static void tick() {
		
	}
	
	public PacketPositionPlayer wrapPosition(Player player) {
		PacketPositionPlayer position = new PacketPositionPlayer();
		
		position.id = player.getId();
		position.x = player.getX();
		position.y = player.getY();
		position.angle = player.getAngle();
		
		return position;
	}
	
	public boolean verify(String username, Connection connection) {
		int index = 0;
		
		if (players.size() == 2) {
			Connect.message = "Server is full";
			return false;
		}
		
		if (players.size() == 1) {
			if (giveNotNull() != 0)
				index = giveNotNull();
			
			if (username.equals(((Player)players.get(index)).getName())) {
				Connect.message = "Someone has the same username";
				return false;
			}
		}
		
		return true;
	}
	
	public int giveNotNull() {
		for (int i = 0; i < 10; i++) {
			if (players.get(i) != null)
				return i;
		}
		
		return 0;
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
