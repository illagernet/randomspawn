package net.illager.spawnzone;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.*;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name = "SpawnZone", version = "1.1.0")
@ApiVersion(ApiVersion.Target.v1_18)
@Description("Replace the vanilla world spawn mechanic with a configurable spawn zone")
@Author("Benjamin Herman <benjamin@metanomial.com>")
@Website("https://github.com/illagernet/spawnzone")
public class SpawnZonePlugin extends JavaPlugin {
  
  @Override
  public void onEnable() {
    saveDefaultConfig();
    updateConfig();
    final SpawnPointGenerator spawner = new SpawnPointGenerator(this);
    new PlayerRespawnListener(this, spawner);
    new PlayerJoinListener(this, spawner);
  }

  private void updateConfig() {
    
    // version 1.0.0
    if(getConfig().contains("radius") && !getConfig().contains("spawn-radius")) {
      getConfig().set("spawn-radius", getConfig().getInt("radius"));
    }
    if(getConfig().contains("do-safe-spawn") && !getConfig().contains("enable-safe-spawn")) {
      getConfig().set("enable-safe-spawn", getConfig().getBoolean("do-safe-spawn"));
    }
    if(getConfig().contains("safe-tries") && !getConfig().contains("safe-spawn-attempts")) {
      getConfig().set("safe-spawn-attempts", getConfig().getBoolean("safe-tries"));
    }
    
    getConfig().options().copyDefaults();
    saveConfig();
  }
}
