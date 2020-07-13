package cc.co.evenprime.bukkit.nocheat.checks.moving;

import java.util.Locale;

import cc.co.evenprime.bukkit.nocheat.NoCheat;
import cc.co.evenprime.bukkit.nocheat.NoCheatPlayer;
import cc.co.evenprime.bukkit.nocheat.actions.ParameterName;
import cc.co.evenprime.bukkit.nocheat.config.Permissions;
import cc.co.evenprime.bukkit.nocheat.data.PreciseLocation;

public class MorePacketsVehicleCheck extends MovingCheck {

    // 20 would be for perfect internet connections, 22 is good enough
    private final static int packetsPerTimeframe = 22;

    public MorePacketsVehicleCheck(final NoCheat plugin) {
        super(plugin, "moving.morepacketsvehicle", Permissions.MOVING_MOREPACKETSVEHICLE);
    }

    /**
     * 1. Players get assigned a certain amount of "free" packets as a limit initially
     * 2. Every move packet reduces that limit by 1
     * 3. If more than 1 second of time passed, the limit gets increased
     * by 22 * time in seconds, up to 50 and he gets a new "setback" location
     * 4. If the player reaches limit = 0 -> teleport him back to "setback"
     * 5. If there was a long pause (maybe lag), limit may be up to 100
     *
     */
    public PreciseLocation check(NoCheatPlayer player, MovingData data, CCMoving cc) {

        PreciseLocation newToLocation = null;

        if(!data.morePacketsVehicleSetbackPoint.isSet()) {
            data.morePacketsVehicleSetbackPoint.set(data.fromVehicle);
        }

        long time = System.currentTimeMillis();

        // Take a packet from the buffer
        data.morePacketsVehicleBuffer--;

        // Player used up buffer, he fails the check
        if(data.morePacketsVehicleBuffer < 0) {

            data.morePacketsVehicleVL = -data.morePacketsVehicleBuffer;
            data.morePacketsVehicleTotalVL++;
            data.morePacketsVehicleFailed++;

            data.packetsVehicle = -data.morePacketsVehicleBuffer;

            final boolean cancel = executeActions(player, cc.morePacketsVehicleActions.getActions(data.morePacketsVehicleVL));

            if(cancel)
                newToLocation = data.morePacketsVehicleSetbackPoint;
        }

        if(data.morePacketsVehicleLastTime + 1000 < time) {
            // More than 1 second elapsed, but how many?
            double seconds = ((double)(time - data.morePacketsVehicleLastTime)) / 1000D;

            // For each second, fill the buffer
            data.morePacketsVehicleBuffer += packetsPerTimeframe * seconds;

            // If there was a long pause (maybe server lag?)
            // Allow buffer to grow up to 100
            if(seconds > 2) {
                if(data.morePacketsVehicleBuffer > 100) {
                    data.morePacketsVehicleBuffer = 100;
                }
                // Else only allow growth up to 50
            } else {
                if(data.morePacketsVehicleBuffer > 50) {
                    data.morePacketsVehicleBuffer = 50;
                }
            }

            // Set the new "last" time
            data.morePacketsVehicleLastTime = time;

            // Set the new "setback" location
            if(newToLocation == null) {
                data.morePacketsVehicleSetbackPoint.set(data.fromVehicle);
            }
        } else if(data.morePacketsVehicleLastTime > time) {
            // Security check, maybe system time changed
            data.morePacketsVehicleLastTime = time;
        }

        return newToLocation;
    }

    @Override
    public boolean isEnabled(CCMoving moving) {
        return moving.morePacketsVehicleCheck;
    }

    @Override
    public String getParameter(final ParameterName wildcard, final NoCheatPlayer player) {

        if (wildcard == ParameterName.VIOLATIONS)
            return String.format(Locale.US, "%d", (int) getData(player.getDataStore()).morePacketsVehicleVL);
        else if (wildcard == ParameterName.PACKETS)
            return String.format(Locale.US, "%d", getData(player.getDataStore()).packetsVehicle);
        else
            return super.getParameter(wildcard, player);
    }
}
