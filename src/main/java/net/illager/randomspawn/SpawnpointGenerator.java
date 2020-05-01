package net.illager.randomspawn;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Spawnpoint generator
 */
public class SpawnpointGenerator {
	private Random random = new Random();
	private RandomSpawnPlugin plugin;

	public SpawnpointGenerator(RandomSpawnPlugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * Generate a random spawn location within the spawn zone
	 */
	public Location generate() {
		
		// Get relevant options from the plugin configuration file
		boolean doSafeSpawn = this.plugin.getConfig().getBoolean("do-safe-spawn");
		int safeTries = this.plugin.getConfig().getInt("safe-tries");
		
		// If the safe spawn option is enabled
		if(doSafeSpawn) {
			
			// Repeat only as many times as the safe tries count
			for(int i = 0; i < safeTries; ++i) {
				
				// Generate a random spawnpoint, safety unchecked
				Location spawnpoint = this.generateRandom();

				// If the location is safe to spawn on, return it
				Block groundBlock = spawnpoint.getBlock().getRelative(0, -1, 0);
				if(this.isSafeMaterial(groundBlock.getType())) return spawnpoint;
			}

			// Log a warning that a safe spawnpoint could not be found
			String warning = String.format("Unable to find safe spawnpoint after %d tries!", safeTries);
			this.plugin.getLogger().log(Level.WARNING, warning);
		}
		
		// Return a random spawnpoint, safety unchecked
		return this.generateRandom();
	}

	/**
	 * Generate a random spawnpoint within the spawn zone, unchecked for safety
	 */
	private Location generateRandom() {
		
		// Get the spawn world and location
		World spawnWorld = this.plugin.getServer().getWorlds().get(0);
		Location spawnLocation = spawnWorld.getSpawnLocation();
		
		// Get the center coordinates of the spawn zone
		int x = spawnLocation.getBlockX();
		int z = spawnLocation.getBlockZ();
		
		// Get the square-radius from the plugin configuration file
		int radius = this.plugin.getConfig().getInt("radius");

		// Pick block-centered random X and Z coordinates within the bounds of the spawn zone
		double xRand = random.nextInt(radius * 2) + x - radius + 0.5;
		double zRand = random.nextInt(radius * 2) + z - radius + 0.5;
		
		// Generate a random location at y=0 within the spawn zone
		Location spawnpoint = new Location(spawnWorld, xRand, 0.0, zRand);

		// If necessary, load and generate the chunk the spawnpoint is in
		Chunk chunk = spawnpoint.getChunk();
		if(!chunk.isLoaded()) chunk.load(true);

		// Adjust the spawnpoint to the highest empty block in the column and return
		int highestY = spawnWorld.getHighestBlockYAt(spawnpoint);
		spawnpoint.setY(highestY);
		return spawnpoint;
	}

	/**
	 * Check if a material is safe to spawn on
	 * @param material The block material to check
	 */
	private boolean isSafeMaterial(Material material) {
		
		// Get safe materials list from the plugin configuration file
		List<String> safeMaterials = this.plugin.getConfig().getStringList("safe-materials");
		
		// Check that the material name appears in the safe materials list
		String materialName = material.toString();
		return safeMaterials.contains(materialName);
	}
}
