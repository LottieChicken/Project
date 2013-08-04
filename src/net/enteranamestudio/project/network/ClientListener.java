package net.enteranamestudio.project.network;

import net.enteranamestudio.project.network.packets.PacketDeletePlayer;
import net.enteranamestudio.project.network.packets.PacketDestroyTile;
import net.enteranamestudio.project.network.packets.PacketGame;
import net.enteranamestudio.project.network.packets.PacketInitPlayer;
import net.enteranamestudio.project.network.packets.PacketMessage;
import net.enteranamestudio.project.network.packets.PacketNewTile;
import net.enteranamestudio.project.network.packets.PacketPositionPlayer;
import net.enteranamestudio.project.states.Game;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {
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
				Game.player.init(packetPlayer.id, packetPlayer.name, packetPlayer.player, packetPlayer.x, packetPlayer.y);
			}
			
			else
				Game.other.init(packetPlayer.id, packetPlayer.name, packetPlayer.player, packetPlayer.x, packetPlayer.y);
		}
		
		if (object instanceof PacketPositionPlayer) {
			PacketPositionPlayer pos = (PacketPositionPlayer)object;

			if (pos.id == Game.player.getId())
				Game.player.setLocation(pos.x, pos.y);
			
			else if (pos.id == Game.other.getId())
				Game.other.setLocation(pos.x, pos.y, pos.angle);
		}
		
		if (object instanceof PacketDeletePlayer) {
			PacketDeletePlayer player = (PacketDeletePlayer)object;
			
			if (Game.other.getId() == player.id)
				Game.other = null;
		}
	}
}
