package net.illager.spawnzone;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion.Target;

@Plugin(name = "SpawnZone", version = "1.0.0")
@Description(value = "Replace the vanilla world spawn mechanic with a more configurable spawn zone")
@ApiVersion(value = Target.v1_13)
public class SpawnZonePlugin extends JavaPlugin {
	private SpawnPointGenerator spawner;

	@Override
	public void onEnable() {

		// Save the default configuration file
		this.saveDefaultConfig();

		// Instantiate a spawnerpoint generator
		spawner = new SpawnPointGenerator(this);
		
		// Register events
		PluginManager pluginManager = this.getServer().getPluginManager();
		pluginManager.registerEvents(new PlayerJoin(spawner), this);
		pluginManager.registerEvents(new PlayerRespawn(spawner), this);
	}

	@Override
	public void onDisable() {}
}
