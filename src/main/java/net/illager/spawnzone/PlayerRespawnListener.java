package net.illager.spawnzone;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {
  private final SpawnPointGenerator spawner;

  public PlayerRespawnListener(final SpawnZonePlugin plugin, final SpawnPointGenerator spawner) {
    this.spawner = spawner;
    Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler
  public void onPlayerRespawn(final PlayerRespawnEvent event) {
    if (event.isBedSpawn()) return;
    event.setRespawnLocation(spawner.generate());
  }
}
