package cc.co.evenprime.bukkit.nocheat.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import cc.co.evenprime.bukkit.nocheat.NoCheat;

/**
 * Only place that listens to Player-teleport related events and dispatches them
 * to relevant checks
 * 
 */
public class WorkaroundsEventManager extends EventManagerImpl {

    public WorkaroundsEventManager(NoCheat plugin) {

        super(plugin);

        registerListener(Event.Type.PLAYER_MOVE, Priority.Highest, false, null);
        registerListener(Event.Type.PLAYER_TELEPORT, Priority.Monitor, true, null);
        registerListener(Event.Type.PLAYER_PORTAL, Priority.Monitor, true, null);
        registerListener(Event.Type.PLAYER_RESPAWN, Priority.Monitor, true, null);
    }

    @Override
    protected void handlePlayerTeleportEvent(final PlayerTeleportEvent event, final Priority priority) {
        handleTeleportation(event.getPlayer(), event.getTo());
    }

    @Override
    protected void handlePlayerPortalEvent(final PlayerPortalEvent event, final Priority priority) {
        handleTeleportation(event.getPlayer(), event.getTo());
    }

    @Override
    protected void handlePlayerRespawnEvent(final PlayerRespawnEvent event, final Priority priority) {
        handleTeleportation(event.getPlayer(), event.getRespawnLocation());
    }

    @Override
    protected void handlePlayerMoveEvent(final PlayerMoveEvent event, final Priority priority) {
        // No typo here. I really only handle cancelled events and ignore others
        if(!event.isCancelled())
            return;

        handleTeleportation(event.getPlayer(), event.getTo());

        // Fix a common mistake that other developers make (cancelling move
        // events is crazy, rather set the target location to the from location)
        if(plugin.getPlayer(event.getPlayer()).getConfigurationStore().debug.overrideIdiocy) {
            event.setCancelled(false);
            event.setTo(event.getFrom().clone());
        }
    }

    private void handleTeleportation(final Player player, final Location to) {
        plugin.clearCriticalData(player.getName());
    }
}
