package cc.co.evenprime.bukkit.nocheat.checks.fight;

import cc.co.evenprime.bukkit.nocheat.ConfigItem;
import cc.co.evenprime.bukkit.nocheat.config.Configuration;
import cc.co.evenprime.bukkit.nocheat.config.util.ActionList;

public class CCFight implements ConfigItem {

    public final boolean    check;
    public final boolean    directionCheck;
    public final double     directionPrecision;
    public final ActionList directionActions;
    public final long       directionPenaltyTime;
    public final boolean    noswingCheck;
    public final ActionList noswingActions;
    public final boolean    reachCheck;
    public final double     reachLimit;
    public final long       reachPenaltyTime;
    public final ActionList reachActions;
    public final int        speedAttackLimit;
    public final ActionList speedActions;
    public final boolean    speedCheck;

    public CCFight(Configuration data) {

        check = data.getBoolean(Configuration.FIGHT_CHECK);
        directionCheck = data.getBoolean(Configuration.FIGHT_DIRECTION_CHECK);
        directionPrecision = ((double) (data.getInteger(Configuration.FIGHT_DIRECTION_PRECISION))) / 100D;
        directionPenaltyTime = data.getInteger(Configuration.FIGHT_DIRECTION_PENALTYTIME);
        directionActions = data.getActionList(Configuration.FIGHT_DIRECTION_ACTIONS);
        noswingCheck = data.getBoolean(Configuration.FIGHT_NOSWING_CHECK);
        noswingActions = data.getActionList(Configuration.FIGHT_NOSWING_ACTIONS);
        reachCheck = data.getBoolean(Configuration.FIGHT_REACH_CHECK);
        reachLimit = ((double) (data.getInteger(Configuration.FIGHT_REACH_LIMIT))) / 100D;
        reachPenaltyTime = data.getInteger(Configuration.FIGHT_REACH_PENALTYTIME);
        reachActions = data.getActionList(Configuration.FIGHT_REACH_ACTIONS);
        speedCheck = data.getBoolean(Configuration.FIGHT_SPEED_CHECK);
        speedActions = data.getActionList(Configuration.FIGHT_SPEED_ACTIONS);
        speedAttackLimit = data.getInteger(Configuration.FIGHT_SPEED_ATTACKLIMIT);
    }
}
