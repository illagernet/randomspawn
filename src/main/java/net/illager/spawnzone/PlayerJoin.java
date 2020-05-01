package net.illager.spawnzone;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
	private SpawnPointGenerator spawner;

	public PlayerJoin(SpawnPointGenerator spawner) {
		this.spawner = spawner;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		// If the player has never played before
		if(!player.hasPlayedBefore()) {

			// Teleport the player to a random spawnpoint in the world spawn zone
			Location spawnpoint = spawner.generate();
			player.teleport(spawnpoint);
		}
	}
}