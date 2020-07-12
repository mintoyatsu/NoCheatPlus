package cc.co.evenprime.bukkit.nocheat.events;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.player.PlayerMoveEvent;

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
    }

    @Override
    protected void handlePlayerMoveEvent(final PlayerMoveEvent event, final Priority priority) {
        // No typo here. I really only handle cancelled events and ignore others
        if(!event.isCancelled())
            return;

        // Fix a common mistake that other developers make (cancelling move
        // events is crazy, rather set the target location to the from location)
        if(plugin.getPlayer(event.getPlayer()).getConfigurationStore().debug.overrideIdiocy) {
            event.setCancelled(false);
            event.setTo(event.getFrom().clone());
        }
    }
}
