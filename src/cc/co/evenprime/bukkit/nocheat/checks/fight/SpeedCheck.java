package cc.co.evenprime.bukkit.nocheat.checks.fight;

import java.util.Locale;
import cc.co.evenprime.bukkit.nocheat.NoCheat;
import cc.co.evenprime.bukkit.nocheat.NoCheatPlayer;
import cc.co.evenprime.bukkit.nocheat.actions.ParameterName;
import cc.co.evenprime.bukkit.nocheat.config.Permissions;

public class SpeedCheck extends FightCheck {

    public SpeedCheck(NoCheat plugin) {
        super(plugin, "fight.speed", Permissions.FIGHT_SPEED);
    }

    public boolean check(NoCheatPlayer player, FightData data, CCFight cc) {

        boolean cancel = false;

        final long time = System.currentTimeMillis();

        if(data.speedTime + 1000 <= time) {
            data.speedTime = time;
            data.speedAttackCount = 0;
            data.speedVL = 0;
        }

        data.speedAttackCount++;

        if(data.speedAttackCount > cc.speedAttackLimit) {
            if(!plugin.skipCheck()) {
                data.speedVL += 1;
                data.speedTotalVL += 1;
                data.speedFailed++;
            }

            cancel = executeActions(player, cc.speedActions.getActions(data.speedVL));
        }

        return cancel;
    }

    @Override
    public boolean isEnabled(CCFight cc) {
        return cc.speedCheck;
    }

    public String getParameter(ParameterName wildcard, NoCheatPlayer player) {

        if(wildcard == ParameterName.VIOLATIONS)
            return String.format(Locale.US, "%d", (int) getData(player.getDataStore()).speedVL);
        else if(wildcard == ParameterName.LIMIT)
            return String.format(Locale.US, "%d", getConfig(player.getConfigurationStore()).speedAttackLimit);
        else
            return super.getParameter(wildcard, player);
    }
}
