package net.illager.spawnzone;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
  private final SpawnPointGenerator spawner;

  public PlayerJoinListener(final SpawnZonePlugin plugin, final SpawnPointGenerator spawner) {
    this.spawner = spawner;
    Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
  }

  @EventHandler
  public void onPlayerJoin(final PlayerJoinEvent event) {
    final Player player = event.getPlayer();
    if (player.hasPlayedBefore()) return;
    player.teleport(spawner.generate());
  }
}