package net.illager.randomspawn;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion.Target;

@Plugin(name = "RandomSpawn", version = "0.1.0")
@Description(value = "Randomize the world spawn point")
@ApiVersion(value = Target.v1_13)
public class RandomSpawnPlugin extends JavaPlugin {
	private SpawnpointGenerator spawner;

	@Override
	public void onEnable() {

		// Save the default configuration file
		this.saveDefaultConfig();

		// Instantiate a spawnerpoint generator
		spawner = new SpawnpointGenerator(this);
		
		// Register events
		PluginManager pluginManager = this.getServer().getPluginManager();
		pluginManager.registerEvents(new PlayerJoin(spawner), this);
		pluginManager.registerEvents(new PlayerRespawn(spawner), this);
	}

	@Override
	public void onDisable() {}
}
