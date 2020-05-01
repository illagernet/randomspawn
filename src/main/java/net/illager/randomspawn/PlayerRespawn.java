package net.illager.randomspawn;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {
	SpawnpointGenerator spawner;

	public PlayerRespawn(SpawnpointGenerator spawner) {
		this.spawner = spawner;
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {

		// If the player has no fixed spawnpoint
		if(!event.isBedSpawn()) {
			
			// Respawn the player at a random spawnpoint
			Location spawnpoint = spawner.generate();
			event.setRespawnLocation(spawnpoint);
		}
	}
}
