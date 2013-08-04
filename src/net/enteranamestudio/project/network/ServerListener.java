package net.enteranamestudio.project.network;

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

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerListener extends Listener {
	private Server server;
	
	public ServerListener(Server server) {
		this.server = server;
	}
	
	public void received(Connection connection, Object object) {
		// WHEN THE SERVER RECEIVED A NEW CONNECTION
		if (object instanceof PacketJoin) {
			PacketJoin packetJoin = (PacketJoin)object;
			
			// VERIFYING PLAYER USERNAME AND SERVER LENGTH
			if (verify(packetJoin.username)) {
				int currentPlayerID = 1;
				Player player = null;
				
				if (ProjectServer.players.size() == 0)
					player = new Player((byte) connection.getID(), packetJoin.username, 1, ProjectServer.map.getSpawn1X(), ProjectServer.map.getSpawn1Y());
				
				else if (ProjectServer.players.size() == 1) {
					player = new Player((byte) connection.getID(), packetJoin.username, 1, ProjectServer.map.getSpawn2X(), ProjectServer.map.getSpawn2Y());
					currentPlayerID = 2;
				}
				
				// SEND TO ALL CONNECTED PLAYER THE NEW PLAYER
				PacketInitPlayer packetInitPlayer = wrapPlayer(player.getId(), player.getName(), player.getX(), player.getY());
				server.sendToAllTCP(packetInitPlayer);
				
				// IF THERE IS ALREADY A CONNECTED PLAYER SEND A THIS PLAYER TO THE NEW PLAYER
				if (ProjectServer.players.size() == 1) {
					Player other = (Player)ProjectServer.players.get(1);
					PacketInitPlayer packetInitPlayer2 = wrapPlayer(other.getId(), other.getName(), other.getX(), other.getY());
					
					server.sendToTCP(connection.getID(), packetInitPlayer2);
				}
				
				ProjectServer.players.put(currentPlayerID, player);
				
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
			message.text = ((Player)ProjectServer.players.get(connection.getID())).getName() + " : "+ message.text;
			
			server.sendToAllTCP(message);
		}
		
		// WHEN THE SERVER RECEIVED A KEY PRESSED
		if (object instanceof PacketKeyCode) {
			PacketKeyCode keyCode = (PacketKeyCode)object;
			PacketPositionPlayer position = new PacketPositionPlayer();
			Player player = (Player)ProjectServer.players.get(connection.getID());
			
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
		message.text = "[SERVER] " + ((Player)ProjectServer.players.get(connection.getID())).getName() + " has disconnected :-(";
		
		server.sendToAllExceptTCP(connection.getID(), new PacketDeletePlayer().id = connection.getID());
		server.sendToAllExceptTCP(connection.getID(), message);
		
		ProjectServer.players.remove(connection.getID());
	}
	
	public PacketInitPlayer wrapPlayer(byte id, String name, short x, short y) {
		PacketInitPlayer packetInitPlayer = new PacketInitPlayer();
		
		packetInitPlayer.id = id;
		packetInitPlayer.name = name;
		packetInitPlayer.x = x;
		packetInitPlayer.y = y;
		
		return packetInitPlayer;
	}
	
	public PacketPositionPlayer wrapPosition(Player player) {
		PacketPositionPlayer position = new PacketPositionPlayer();
		
		position.id = player.getId();
		position.x = player.getX();
		position.y = player.getY();
		position.angle = player.getAngle();
		
		return position;
	}
	
	public boolean verify(String username) {
		if (ProjectServer.players.size() == 2) {
			Connect.message = "Server is full";
			return false;
		}
		
		if (username.equals(((Player)ProjectServer.players.get(1)).getName())) {
			Connect.message = "Someone has the same username";
			return false;
		}
	
		return true;
	}
}
