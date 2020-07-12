package cc.co.evenprime.bukkit.nocheat.checks.fight;

import java.util.Map;

import net.minecraft.server.Entity;
import cc.co.evenprime.bukkit.nocheat.DataItem;
import cc.co.evenprime.bukkit.nocheat.data.ExecutionHistory;

public class FightData implements DataItem {

    public double                 directionVL                = 0.0D;
    public double                 directionTotalVL           = 0.0D;
    public int                    directionFailed            = 0;
    public double                 noswingVL                  = 0.0D;
    public double                 noswingTotalVL             = 0.0D;
    public int                    noswingFailed              = 0;
    public double                 reachVL                    = 0.0D;
    public double                 reachTotalVL               = 0.0D;
    public int                    reachFailed                = 0;
    public double                 speedVL                    = 0.0D;
    public double                 speedTotalVL               = 0.0D;
    public int                    speedFailed                = 0;

    public long                   directionLastViolationTime = 0;
    public long                   reachLastViolationTime     = 0;
    public final ExecutionHistory history                    = new ExecutionHistory();

    public Entity                 damagee;
    public boolean                armswung                   = true;
    public boolean                skipNext                   = false;

    public long                   speedTime                  = 0;
    public int                    speedAttackCount           = 0;

    @Override
    public void collectData(Map<String, Object> map) {
        map.put("fight.direction.vl", (int) directionTotalVL);
        map.put("fight.noswing.vl", (int) noswingTotalVL);
        map.put("fight.reach.vl", (int) reachTotalVL);
        map.put("fight.speed.vl", (int) speedTotalVL);
        map.put("fight.direction.failed", directionFailed);
        map.put("fight.noswing.failed", noswingFailed);
        map.put("fight.reach.failed", reachFailed);
        map.put("fight.speed.failed", speedFailed);
    }

    @Override
    public void clearCriticalData() {

    }
}
