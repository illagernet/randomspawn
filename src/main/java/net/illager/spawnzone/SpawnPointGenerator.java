package net.illager.spawnzone;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Spawnpoint generator
 */
public class SpawnPointGenerator {
  final List<String> safeMaterials;
  final boolean safeSpawnEnabled;
  final int safeSpawnAttempts;
  final int spawnRadius;
  final World spawnWorld;

  public SpawnPointGenerator(final SpawnZonePlugin plugin) {
    safeSpawnEnabled = plugin.getConfig().getBoolean("enable-safe-spawn", true);
    safeSpawnAttempts = plugin.getConfig().getInt("safe-spawn-attempts", 100);
    safeMaterials = plugin.getConfig().getStringList("safe-materials");
    spawnRadius = plugin.getConfig().getInt("spawn-radius");
    spawnWorld = Bukkit.getServer().getWorlds().get(0);
  }

  /**
   * Generates a spawn location within the spawn zone.
   */
  public Location generate() {

    // Attempt safe spawn if enabled
    if (safeSpawnEnabled) {
      for (int i = 0; i < safeSpawnAttempts; i++) {
        final Location spawnpoint = generateUnchecked();
        if (safeMaterials.contains(spawnpoint.getBlock().getType().toString()))
          return spawnpoint;
      }
      Bukkit.getLogger().warning(String.format("Unable to find safe spawnpoint after %d attempts!", safeSpawnAttempts));
    }

    // Unchecked spawn
    return generateUnchecked();
  }

  /**
   * Generates a potentially unsafe spawnpoint within the spawn zone.
   */
  private Location generateUnchecked() {
    final Location worldSpawn = spawnWorld.getSpawnLocation();
    final Random random = new Random();
    final double xRand = random.nextInt(spawnRadius * 2) + worldSpawn.getBlockX() - spawnRadius + 0.5;
    final double zRand = random.nextInt(spawnRadius * 2) + worldSpawn.getBlockZ() - spawnRadius + 0.5;
    final Location location = new Location(spawnWorld, xRand, 0.0, zRand);
    location.getChunk().load(true);
    location.setY(spawnWorld.getHighestBlockYAt(location));
    return location;
  }
}
