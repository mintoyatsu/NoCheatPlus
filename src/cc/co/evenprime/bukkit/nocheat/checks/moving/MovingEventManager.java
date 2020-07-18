package cc.co.evenprime.bukkit.nocheat.checks.moving;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import cc.co.evenprime.bukkit.nocheat.NoCheat;
import cc.co.evenprime.bukkit.nocheat.NoCheatPlayer;
import cc.co.evenprime.bukkit.nocheat.checks.CheckUtil;
import cc.co.evenprime.bukkit.nocheat.config.ConfigurationCacheStore;
import cc.co.evenprime.bukkit.nocheat.config.Permissions;
import cc.co.evenprime.bukkit.nocheat.data.PreciseLocation;
import cc.co.evenprime.bukkit.nocheat.data.SimpleLocation;
import cc.co.evenprime.bukkit.nocheat.debug.PerformanceManager.EventType;
import cc.co.evenprime.bukkit.nocheat.events.EventManagerImpl;

/**
 * The only place that listens to and modifies player_move events if necessary
 * 
 * Get the event, decide which checks should work on it and in what order,
 * evaluate the check results and decide what to
 * 
 */
public class MovingEventManager extends EventManagerImpl {

    private final List<MovingCheck> checks;

    private final List<MovingCheck> checksVehicle;

    public MovingEventManager(NoCheat plugin) {

        super(plugin);

        this.checks = new ArrayList<MovingCheck>(2);

        checks.add(new RunflyCheck(plugin));
        checks.add(new MorePacketsCheck(plugin));

        this.checksVehicle = new ArrayList<MovingCheck>(2);

        checksVehicle.add(new SpeedingVehicleCheck(plugin));
        checksVehicle.add(new MorePacketsVehicleCheck(plugin));

        registerListener(Event.Type.PLAYER_MOVE, Priority.Lowest, true, plugin.getPerformance(EventType.MOVING));
        registerListener(Event.Type.VEHICLE_MOVE, Priority.Lowest, true, plugin.getPerformance(EventType.MOVING));
        registerListener(Event.Type.PLAYER_VELOCITY, Priority.Monitor, true, plugin.getPerformance(EventType.VELOCITY));
        registerListener(Event.Type.BLOCK_PLACE, Priority.Monitor, true, plugin.getPerformance(EventType.BLOCKPLACE));
        registerListener(Event.Type.PLAYER_INTERACT, Priority.Lowest, true, null);
        registerListener(Event.Type.PLAYER_TELEPORT, Priority.Highest, false, null);
        registerListener(Event.Type.PLAYER_PORTAL, Priority.Monitor, false, null);
        registerListener(Event.Type.PLAYER_RESPAWN, Priority.Monitor, false, null);
        registerListener(Event.Type.PLAYER_QUIT, Priority.Monitor, false, null);
    }

    @Override
    protected void handleBlockPlaceEvent(final BlockPlaceEvent event, final Priority priority) {

        final NoCheatPlayer player = plugin.getPlayer(event.getPlayer());
        // Get the player-specific stored data that applies here
        final MovingData data = MovingCheck.getData(player.getDataStore());

        final Block blockPlaced = event.getBlockPlaced();

        if(blockPlaced == null || !data.runflySetBackPoint.isSet()) {
            return;
        }

        final SimpleLocation lblock = new SimpleLocation();
        lblock.set(blockPlaced);
        final SimpleLocation lplayer = new SimpleLocation();
        lplayer.setLocation(player.getPlayer().getLocation());

        if(Math.abs(lplayer.x - lblock.x) <= 1 && Math.abs(lplayer.z - lblock.z) <= 1 && lplayer.y - lblock.y >= 0 && lplayer.y - lblock.y <= 2) {

            final int type = CheckUtil.getType(blockPlaced.getTypeId());
            if(CheckUtil.isSolid(type) || CheckUtil.isLiquid(type)) {
                if(lblock.y + 1 >= data.runflySetBackPoint.y) {
                    data.runflySetBackPoint.y = (lblock.y + 1);
                    data.jumpPhase = 0;
                }
            }
        }
    }

    @Override
    public void handlePlayerTeleportEvent(final PlayerTeleportEvent event, final Priority priority) {

        NoCheatPlayer player = plugin.getPlayer(event.getPlayer());
        final MovingData data = MovingCheck.getData(player.getDataStore());

        // If it was a teleport initialized by NoCheat, do it anyway
        if(data.teleportTo.isSet() && data.teleportTo.equals(event.getTo())) {
            event.setCancelled(false);
        } else {
            // Only if it wasn't NoCheat, drop data from morepackets check
            data.clearMorePacketsData();
            data.clearMorePacketsVehicleData();
        }

        // Always forget runfly specific data
        data.teleportTo.reset();
        data.clearRunFlyData();
    }

    @Override
    public void handlePlayerPortalEvent(PlayerPortalEvent event, Event.Priority priority) {
        final MovingData data = MovingCheck.getData(plugin.getPlayer(event.getPlayer()).getDataStore());
        data.clearMorePacketsData();
        data.clearMorePacketsVehicleData();
        data.clearRunFlyData();
    }

    @Override
    public void handlePlayerRespawnEvent(PlayerRespawnEvent event, Event.Priority priority) {
        final MovingData data = MovingCheck.getData(plugin.getPlayer(event.getPlayer()).getDataStore());
        data.clearMorePacketsData();
        data.clearMorePacketsVehicleData();
        data.clearRunFlyData();
    }

    @Override
    public void handlePlayerMoveEvent(final PlayerMoveEvent event, final Priority priority) {

        // Don't care for movements that are very high distance or to another
        // world (such that it is very likely the event data was modified by
        // another plugin before we got it)
        if(!event.getFrom().getWorld().equals(event.getTo().getWorld()) || event.getFrom().distanceSquared(event.getTo()) > 400) {
            return;
        }

        // Get the world-specific configuration that applies here
        final NoCheatPlayer player = plugin.getPlayer(event.getPlayer());

        // Not interested at all in players in vehicles or dead
        if(event.getPlayer().isInsideVehicle() || player.isDead()) {
            return;
        }

        final CCMoving cc = MovingCheck.getConfig(player.getConfigurationStore());

        final MovingData data = MovingCheck.getData(player.getDataStore());

        // Various calculations related to velocity estimates
        updateVelocities(data);

        if(!cc.check || player.hasPermission(Permissions.MOVING)) {
            // Just because he is allowed now, doesn't mean he will always
            // be. So forget data about the player related to moving
            data.clearRunFlyData();
            data.clearMorePacketsData();
            return;
        }

        // Get some data that's needed from this event, to avoid passing the
        // event itself on to the checks (and risk to
        // accidentally modifying the event there)

        final Location to = event.getTo();

        data.from.set(event.getFrom());
        data.to.set(to);

        // This variable will have the modified data of the event (new
        // "to"-location)
        PreciseLocation newTo = null;

        for(MovingCheck check : checks) {
            if(newTo == null && check.isEnabled(cc) && !player.hasPermission(check.getPermission())) {
                newTo = check.check(player, data, cc);
            }
        }

        // Did the check(s) decide we need a new "to"-location?
        if(newTo != null) {
            // Compose a new location based on coordinates of "newTo" and
            // viewing direction of "event.getTo()"
            event.setTo(new Location(player.getPlayer().getWorld(), newTo.x, newTo.y, newTo.z, to.getYaw(), to.getPitch()));

            data.teleportTo.set(newTo);
        }
    }

    private void updateVelocities(MovingData data) {

        /******** DO GENERAL DATA MODIFICATIONS ONCE FOR EACH EVENT *****/
        if(data.horizVelocityCounter > 0) {
            data.horizVelocityCounter--;
        } else if(data.horizFreedom > 0.001) {
            data.horizFreedom *= 0.90;
        }

        if(data.vertVelocity <= 0.1) {
            data.vertVelocityCounter--;
        }
        if(data.vertVelocityCounter > 0) {
            data.vertFreedom += data.vertVelocity;
            data.vertVelocity *= 0.90;
        } else if(data.vertFreedom > 0.001) {
            // Counter has run out, now reduce the vert freedom over time
            data.vertFreedom *= 0.93;
        }
    }

    @Override
    public void handlePlayerVelocityEvent(final PlayerVelocityEvent event, final Priority priority) {

        final MovingData data = MovingCheck.getData(plugin.getPlayer(event.getPlayer()).getDataStore());

        final Vector v = event.getVelocity();

        double newVal = v.getY();
        if(newVal >= 0.0D) {
            data.vertVelocity += newVal;
            data.vertFreedom += data.vertVelocity;
        }

        data.vertVelocityCounter = 50;

        newVal = Math.sqrt(Math.pow(v.getX(), 2) + Math.pow(v.getZ(), 2));
        if(newVal > 0.0D) {
            data.horizFreedom += newVal;
            data.horizVelocityCounter = 30;
        }
    }

    /**
     * When an vehicle moves, it will be checked for various
     * suspicious behaviour.
     *
     * @param event
     *            The VehicleMoveEvent
     */
    @Override
    public void handleVehicleMoveEvent(final VehicleMoveEvent event, final Priority priority) {

        // Don't care for movements that are very high distance or to another
        // world (such that it is very likely the event data was modified by
        // another plugin before we got it)
        if(!event.getFrom().getWorld().equals(event.getTo().getWorld()) || event.getFrom().distanceSquared(event.getTo()) > 400) {
            return;
        }

        // Don't care about vehicles without a passenger
        if(event.getVehicle().getPassenger() == null || !(event.getVehicle().getPassenger() instanceof Player)) {
            return;
        }

        // Get the world-specific configuration that applies here
        final NoCheatPlayer player = plugin.getPlayer((Player) event.getVehicle().getPassenger());

        final CCMoving cc = MovingCheck.getConfig(player.getConfigurationStore());

        final MovingData data = MovingCheck.getData(player.getDataStore());

        // Various calculations related to velocity estimates
        updateVelocities(data);

        if(!cc.check || player.hasPermission(Permissions.MOVING)) {
            // Just because it is allowed now, doesn't mean it will always
            // be. So forget data about the vehicle related to moving
            data.clearRunFlyVehicleData();
            data.clearMorePacketsVehicleData();
            return;
        }

        // Get some data that's needed from this event, to avoid passing the
        // event itself on to the checks (and risk to
        // accidentally modifying the event there)

        final Location to = event.getTo();

        data.fromVehicle.set(event.getFrom());
        data.toVehicle.set(to);

        // This variable will have the modified data of the event (new
        // "to"-location)
        PreciseLocation newTo = null;

        for(MovingCheck check : checksVehicle) {
            if(newTo == null && check.isEnabled(cc) && !player.hasPermission(check.getPermission())) {
                newTo = check.check(player, data, cc);
            }
        }

        // Did the check(s) decide we need a new "to"-location?
        if(newTo != null) {
            // Drop the usual items
            if(event.getVehicle() instanceof Boat) {
                event.getVehicle().getWorld().dropItemNaturally(event.getVehicle().getLocation(), new ItemStack(Material.WOOD, 3));
                event.getVehicle().getWorld().dropItemNaturally(event.getVehicle().getLocation(), new ItemStack(Material.STICK, 2));
            } else if(event.getVehicle() instanceof Minecart) {
                event.getVehicle().getWorld().dropItemNaturally(event.getVehicle().getLocation(), new ItemStack(Material.MINECART, 1));
            }
            // Remove the passenger
            if (event.getVehicle().getPassenger() != null) {
                event.getVehicle().setPassenger(null);
            }
            // Destroy the vehicle
            if (! (event.getVehicle() instanceof Pig)) {
                event.getVehicle().remove();
            }

            data.teleportTo.set(newTo);
        }
    }

    /**
     * If a player tries to place a boat on the ground, the event
     * will be cancelled.
     *
     * @param event
     *            The PlayerInteractEvent
     */
    @Override
    public void handlePlayerInteractEvent(final PlayerInteractEvent event, final Priority priority) {
        if(!event.getPlayer().hasPermission(Permissions.MOVING_BOATONGROUND)
                && event.getAction() == Action.RIGHT_CLICK_BLOCK
                && event.getPlayer().getItemInHand().getType() == Material.BOAT
                && event.getClickedBlock().getType() != Material.WATER
                && event.getClickedBlock().getType() != Material.STATIONARY_WATER
                // This also makes sure there are no water blocks next to the clicked block
                && event.getClickedBlock().getRelative(event.getBlockFace()).getType() != Material.WATER
                && event.getClickedBlock().getRelative(event.getBlockFace()).getType() != Material.STATIONARY_WATER) {
            event.setCancelled(true);
        }
    }

    /**
     * This events listener fixes the exploitation of the safe
     * respawn location (usually exploited with gravel or sand).
     *
     * @param event
     */
    @Override
    public void handlePlayerQuitEvent(final PlayerQuitEvent event, final Priority priority) {
        if(!event.getPlayer().hasPermission(Permissions.MOVING_RESPAWNTRICK)
                && (event.getPlayer().getLocation().getBlock().getType() == Material.GRAVEL || event.getPlayer().getLocation().getBlock().getType() == Material.SAND)) {
            event.getPlayer().getLocation().getBlock().setType(Material.AIR);
            event.getPlayer().getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
        }
    }

    public List<String> getActiveChecks(ConfigurationCacheStore cc) {
        LinkedList<String> s = new LinkedList<String>();

        CCMoving m = MovingCheck.getConfig(cc);

        if(m.check) {
            if(m.runflyCheck) {

                if(!m.allowFlying) {
                    s.add("moving.runfly");
                    if(m.swimmingCheck)
                        s.add("moving.swimming");
                    if(m.sneakingCheck)
                        s.add("moving.sneaking");
                    if(m.vehicleCheck)
                        s.add("moving.vehicle");
                    if(m.nofallCheck)
                        s.add("moving.nofall");
                } else
                    s.add("moving.flying");

            }
            if(m.morePacketsCheck)
                s.add("moving.morepackets");
            if(m.morePacketsVehicleCheck)
                s.add("moving.morepacketsvehicle");
        }

        return s;
    }
}
