package cc.co.evenprime.bukkit.nocheat;

import org.bukkit.entity.Player;

import cc.co.evenprime.bukkit.nocheat.config.ConfigurationCacheStore;
import cc.co.evenprime.bukkit.nocheat.data.DataStore;

public interface NoCheatPlayer {

    public boolean hasPermission(String permission);

    public String getName();

    public Player getPlayer();

    public DataStore getDataStore();

    public boolean isDead();

    public ConfigurationCacheStore getConfigurationStore();
}
