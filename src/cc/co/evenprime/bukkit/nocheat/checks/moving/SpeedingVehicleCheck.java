package cc.co.evenprime.bukkit.nocheat.checks.moving;

import java.util.Locale;

import org.bukkit.entity.Minecart;

import cc.co.evenprime.bukkit.nocheat.NoCheat;
import cc.co.evenprime.bukkit.nocheat.NoCheatPlayer;
import cc.co.evenprime.bukkit.nocheat.actions.ParameterName;
import cc.co.evenprime.bukkit.nocheat.checks.CheckUtil;
import cc.co.evenprime.bukkit.nocheat.config.Permissions;
import cc.co.evenprime.bukkit.nocheat.data.PreciseLocation;

public class SpeedingVehicleCheck extends MovingCheck {

    private final static double maxBonus     = 1D;

    public SpeedingVehicleCheck(NoCheat plugin) {

        super(plugin, "moving.vehicle", Permissions.MOVING_VEHICLE);
    }

    public PreciseLocation check(NoCheatPlayer player, MovingData data, CCMoving cc) {
        if(cc.allowFlying || player.hasPermission(Permissions.MOVING_RUNFLY) || player.hasPermission(Permissions.MOVING_FLYING) || player.getPlayer().getVehicle() instanceof Minecart) {
            // If the player doesn't get checked for movement
            // reset his critical data
            data.clearRunFlyVehicleData();
            return null;
        }

        // Some shortcuts:
        final PreciseLocation setBack = data.runflyVehicleSetBackPoint;
        final PreciseLocation to = data.toVehicle;
        final PreciseLocation from = data.fromVehicle;

        // Calculate some distances
        final double xDistance = data.toVehicle.x - from.x;
        final double zDistance = to.z - from.z;
        final double horizontalDistance = Math.sqrt((xDistance * xDistance + zDistance * zDistance));

        if(!setBack.isSet()) {
            setBack.set(from);
        }

        // To know if a player "is on ground" is useful
        final int toType = CheckUtil.evaluateLocation(player.getPlayer().getWorld(), to);

        final boolean toOnGround = CheckUtil.isOnGround(toType);
        final boolean toInGround = CheckUtil.isInGround(toType);

        PreciseLocation newToLocation = null;

        final double resultHoriz = Math.max(0.0D, checkHorizontal(data, horizontalDistance, cc));
        final double resultVert = Math.max(0.0D, checkVertical(data));

        final double result = (resultHoriz + resultVert) * 100;

        // Slowly reduce the level with each event
        data.runflyVL *= 0.95;

        if(result > 0) {

            // Increment violation counter
            data.runflyVL += result;
            data.runflyVehicleTotalVL += result;
            data.runflyVehicleFailed++;

            boolean cancel = executeActions(player, cc.actions.getActions(data.runflyVL));

            // Was one of the actions a cancel? Then do it
            if(cancel) {
                newToLocation = setBack;
            } else if(toOnGround || toInGround) {
                // In case it only gets logged, not stopped by NoCheat
                // Update the setback location at least a bit
                setBack.set(to);

            }
        } else {
            if((toInGround && from.y >= to.y) || CheckUtil.isLiquid(toType)) {
                setBack.set(to);
                setBack.y = Math.ceil(setBack.y);
            } else if(toOnGround && (from.y >= to.y || setBack.y <= Math.floor(to.y))) {
                setBack.set(to);
                setBack.y = Math.floor(setBack.y);
            }
        }

        return newToLocation;
    }

    /**
     * Calculate how much the player failed this check
     *
     */
    private double checkHorizontal(final MovingData data, final double totalDistance, final CCMoving cc) {

        // How much further did the player move than expected??
        double distanceAboveLimit = totalDistance - cc.vehicleSpeedLimit - data.horizFreedom;

        if(distanceAboveLimit > 0) {
            // Try to consume the "buffer"
            distanceAboveLimit -= data.horizontalBufferVehicle;
            data.horizontalBufferVehicle = 0;

            // Put back the "overconsumed" buffer
            if(distanceAboveLimit < 0) {
                data.horizontalBufferVehicle = -distanceAboveLimit;
            }
        }
        // He was within limits, give the difference as buffer
        else {
            data.horizontalBufferVehicle = Math.min(maxBonus, data.horizontalBufferVehicle - distanceAboveLimit);
        }

        if(distanceAboveLimit > 0) {
            data.checknamesuffix = "horizontal";
        }
        return distanceAboveLimit;
    }

    /**
     * Calculate if and how much the player "failed" this check.
     *
     */
    private double checkVertical(final MovingData data) {

        // How much higher did the player move than expected??
        double distanceAboveLimit = 0.0D;

        double limit = data.vertFreedom + 300.0D;

        distanceAboveLimit = data.to.y - data.runflyVehicleSetBackPoint.y - limit;

        if(distanceAboveLimit > 0) {
            data.checknamesuffix = "vertical";
        }
        return distanceAboveLimit;

    }

    @Override
    public boolean isEnabled(CCMoving moving) {
        return moving.runflyCheck && moving.vehicleCheck && !moving.allowFlying;
    }

    public String getParameter(ParameterName wildcard, NoCheatPlayer player) {

        if(wildcard == ParameterName.CHECK)
            // Workaround for something until I find a better way to do it
            return getName() + "." + getData(player.getDataStore()).checknamesuffix;
        else if(wildcard == ParameterName.VIOLATIONS)
            return String.format(Locale.US, "%d", (int) getData(player.getDataStore()).runflyVL);
        else
            return super.getParameter(wildcard, player);
    }
}
