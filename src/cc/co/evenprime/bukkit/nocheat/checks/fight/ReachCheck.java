package cc.co.evenprime.bukkit.nocheat.checks.fight;

import java.util.Locale;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityGiantZombie;
import cc.co.evenprime.bukkit.nocheat.NoCheat;
import cc.co.evenprime.bukkit.nocheat.NoCheatPlayer;
import cc.co.evenprime.bukkit.nocheat.actions.ParameterName;
import cc.co.evenprime.bukkit.nocheat.checks.CheckUtil;
import cc.co.evenprime.bukkit.nocheat.config.Permissions;

public class ReachCheck extends FightCheck {

    public ReachCheck(NoCheat plugin) {
        super(plugin, "fight.reach", Permissions.FIGHT_REACH);
    }

    public boolean check(NoCheatPlayer player, FightData data, CCFight cc) {

        boolean cancel = false;

        final long time = System.currentTimeMillis();

        // Get the width of the damagee
        Entity entity = data.damagee;

        // Safeguard, if entity is complex, this check will fail
        // due to giant and hard to define hitboxes
        if(entity instanceof EntityGiantZombie) {
            return false;
        }

        // entity.height is broken and will always be 0, therefore calculate height instead
        final double height = entity.boundingBox.e - entity.boundingBox.b;

        // Because entityLocations are always set to center bottom of the
        // hitbox, divide the height by 2 to get the "center" of the hitbox
        final double off = CheckUtil.reachCheck(player, entity.locX, entity.locY + (height / 2D), entity.locZ, cc.reachLimit);

        if(off < 0.1D) {
            // Player did probably nothing wrong
            // reduce violation counter
            data.reachVL *= 0.80D;
        } else {
            // Player failed the check
            // Increment violation counter
            // This is influenced by lag, so don't do it if there was server lag
            if(!plugin.skipCheck()) {
                double sqrt = Math.sqrt(off);
                data.reachVL += sqrt;
                data.reachTotalVL += sqrt;
                data.reachFailed++;
            }

            cancel = executeActions(player, cc.reachActions.getActions(data.reachVL));

            if(cancel) {
                // Needed to calculate penalty times
                data.reachLastViolationTime = time;
            }
        }

        // If the player is still in penalty time, cancel the event anyway
        if(data.reachLastViolationTime + cc.reachPenaltyTime > time) {
            if(data.reachLastViolationTime > time) {
                System.out.println("Nocheat noted that your time ran backwards for " + (data.reachLastViolationTime - time) + " ms");
                // Security check for server time changed situations
                data.reachLastViolationTime = 0;
            }
            return true;
        }

        return cancel;
    }

    @Override
    public boolean isEnabled(CCFight cc) {
        return cc.reachCheck;
    }

    public String getParameter(ParameterName wildcard, NoCheatPlayer player) {

        if(wildcard == ParameterName.VIOLATIONS)
            return String.format(Locale.US, "%d", (int) getData(player.getDataStore()).reachVL);
        else
            return super.getParameter(wildcard, player);
    }
}
