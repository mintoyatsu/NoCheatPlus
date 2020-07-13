package cc.co.evenprime.bukkit.nocheat.config;

import java.util.HashMap;
import java.util.Map;
import cc.co.evenprime.bukkit.nocheat.config.util.ActionList;
import cc.co.evenprime.bukkit.nocheat.config.util.OptionNode;
import cc.co.evenprime.bukkit.nocheat.config.util.OptionNode.DataType;
import cc.co.evenprime.bukkit.nocheat.log.LogLevel;

/**
 * This class describes a basic configuration for NoCheat. NoCheats
 * configuration
 * is based on String,String pairs, and this class provides some convenience
 * methods
 * to retrieve and store more complex datatypes based on strings.
 * 
 */
public abstract class Configuration {

    protected final static OptionNode     ROOT                                       = new OptionNode(null, null, DataType.PARENT);

    protected final static OptionNode     INACTIVE                                   = new OptionNode(null, null, DataType.PARENT);

    private final static OptionNode       LOGGING                                    = new OptionNode("logging", ROOT, DataType.PARENT);
    public final static OptionNode        LOGGING_ACTIVE                             = new OptionNode("active", LOGGING, DataType.BOOLEAN);
    public final static OptionNode        LOGGING_PREFIX                             = new OptionNode("prefix", LOGGING, DataType.STRING);
    public final static OptionNode        LOGGING_FILENAME                           = new OptionNode("filename", LOGGING, DataType.STRING);
    public final static OptionNode        LOGGING_FILELEVEL                          = new OptionNode("filelevel", LOGGING, DataType.LOGLEVEL);
    public final static OptionNode        LOGGING_CONSOLELEVEL                       = new OptionNode("consolelevel", LOGGING, DataType.LOGLEVEL);
    public final static OptionNode        LOGGING_CHATLEVEL                          = new OptionNode("chatlevel", LOGGING, DataType.LOGLEVEL);

    private final static OptionNode       DEBUG                                      = new OptionNode("debug", ROOT, DataType.PARENT);
    public final static OptionNode        DEBUG_SHOWACTIVECHECKS                     = new OptionNode("showactivechecks", DEBUG, DataType.BOOLEAN);
    public final static OptionNode        DEBUG_COMPATIBILITY                        = new OptionNode("compatibility", DEBUG, DataType.BOOLEAN);
    public final static OptionNode        DEBUG_MESSAGES                             = new OptionNode("messages", DEBUG, DataType.BOOLEAN);

    private final static OptionNode       INVENTORY                                  = new OptionNode("inventory", ROOT, DataType.PARENT);
    public static final OptionNode        INVENTORY_CHECK                            = new OptionNode("check", INVENTORY, DataType.BOOLEAN);

    private final static OptionNode       INVENTORY_DROP                             = new OptionNode("drop", INVENTORY, DataType.PARENT);
    public final static OptionNode        INVENTORY_DROP_CHECK                       = new OptionNode("check", INVENTORY_DROP, DataType.BOOLEAN);
    public final static OptionNode        INVENTORY_DROP_TIMEFRAME                   = new OptionNode("timeframe", INVENTORY_DROP, DataType.INTEGER);
    public final static OptionNode        INVENTORY_DROP_LIMIT                       = new OptionNode("limit", INVENTORY_DROP, DataType.INTEGER);
    public final static OptionNode        INVENTORY_DROP_ACTIONS                     = new OptionNode("actions", INVENTORY_DROP, DataType.ACTIONLIST);

    private final static OptionNode       MOVING                                     = new OptionNode("moving", ROOT, DataType.PARENT);
    public final static OptionNode        MOVING_CHECK                               = new OptionNode("check", MOVING, DataType.BOOLEAN);

    private final static OptionNode       MOVING_RUNFLY                              = new OptionNode("runfly", MOVING, DataType.PARENT);
    public final static OptionNode        MOVING_RUNFLY_CHECK                        = new OptionNode("check", MOVING_RUNFLY, DataType.BOOLEAN);
    public final static OptionNode        MOVING_RUNFLY_WALKINGSPEEDLIMIT            = new OptionNode("walkingspeedlimit", MOVING_RUNFLY, DataType.INTEGER);
    public final static OptionNode        MOVING_RUNFLY_JUMPHEIGHT                   = new OptionNode("jumpheight", MOVING_RUNFLY, DataType.INTEGER);
    public final static OptionNode        MOVING_RUNFLY_CHECKSNEAKING                = new OptionNode("checksneaking", MOVING_RUNFLY, DataType.BOOLEAN);
    public final static OptionNode        MOVING_RUNFLY_SNEAKINGSPEEDLIMIT           = new OptionNode("sneakingspeedlimit", MOVING_RUNFLY, DataType.INTEGER);
    public final static OptionNode        MOVING_RUNFLY_CHECKSWIMMING                = new OptionNode("checkswimming", MOVING_RUNFLY, DataType.BOOLEAN);
    public final static OptionNode        MOVING_RUNFLY_SWIMMINGSPEEDLIMIT           = new OptionNode("swimmingspeedlimit", MOVING_RUNFLY, DataType.INTEGER);
    public final static OptionNode        MOVING_RUNFLY_ACTIONS                      = new OptionNode("actions", MOVING_RUNFLY, DataType.ACTIONLIST);
    public final static OptionNode        MOVING_RUNFLY_CHECKNOFALL                  = new OptionNode("checknofall", MOVING_RUNFLY, DataType.BOOLEAN);
    public final static OptionNode        MOVING_RUNFLY_NOFALLAGGRESSIVE             = new OptionNode("nofallaggressivemode", MOVING_RUNFLY, DataType.BOOLEAN);
    public final static OptionNode        MOVING_RUNFLY_NOFALLMULTIPLIER             = new OptionNode("nofallmultiplier", MOVING_RUNFLY, DataType.INTEGER);
    public final static OptionNode        MOVING_RUNFLY_NOFALLACTIONS                = new OptionNode("nofallactions", MOVING_RUNFLY, DataType.ACTIONLIST);
    public final static OptionNode        MOVING_RUNFLY_ALLOWLIMITEDFLYING           = new OptionNode("allowlimitedflying", MOVING_RUNFLY, DataType.BOOLEAN);
    public final static OptionNode        MOVING_RUNFLY_FLYINGSPEEDLIMITVERTICAL     = new OptionNode("flyingspeedlimitvertical", MOVING_RUNFLY, DataType.INTEGER);
    public final static OptionNode        MOVING_RUNFLY_FLYINGSPEEDLIMITHORIZONTAL   = new OptionNode("flyingspeedlimithorizontal", MOVING_RUNFLY, DataType.INTEGER);
    public final static OptionNode        MOVING_RUNFLY_FLYINGHEIGHTLIMIT            = new OptionNode("flyingheightlimit", MOVING_RUNFLY, DataType.INTEGER);
    public final static OptionNode        MOVING_RUNFLY_FLYINGACTIONS                = new OptionNode("flyingactions", MOVING_RUNFLY, DataType.ACTIONLIST);

    private final static OptionNode       MOVING_MOREPACKETS                         = new OptionNode("morepackets", MOVING, DataType.PARENT);
    public final static OptionNode        MOVING_MOREPACKETS_CHECK                   = new OptionNode("check", MOVING_MOREPACKETS, DataType.BOOLEAN);
    public final static OptionNode        MOVING_MOREPACKETS_ACTIONS                 = new OptionNode("actions", MOVING_MOREPACKETS, DataType.ACTIONLIST);

    private final static OptionNode       BLOCKBREAK                                 = new OptionNode("blockbreak", ROOT, DataType.PARENT);
    public final static OptionNode        BLOCKBREAK_CHECK                           = new OptionNode("check", BLOCKBREAK, DataType.BOOLEAN);

    private final static OptionNode       BLOCKBREAK_REACH                           = new OptionNode("reach", BLOCKBREAK, DataType.PARENT);
    public final static OptionNode        BLOCKBREAK_REACH_CHECK                     = new OptionNode("check", BLOCKBREAK_REACH, DataType.BOOLEAN);
    public final static OptionNode        BLOCKBREAK_REACH_ACTIONS                   = new OptionNode("actions", BLOCKBREAK_REACH, DataType.ACTIONLIST);

    private final static OptionNode       BLOCKBREAK_DIRECTION                       = new OptionNode("direction", BLOCKBREAK, DataType.PARENT);
    public final static OptionNode        BLOCKBREAK_DIRECTION_CHECK                 = new OptionNode("check", BLOCKBREAK_DIRECTION, DataType.BOOLEAN);
    public final static OptionNode        BLOCKBREAK_DIRECTION_CHECKINSTABREAKBLOCKS = new OptionNode("checkinstabreakblocks", BLOCKBREAK_DIRECTION, DataType.BOOLEAN);
    public final static OptionNode        BLOCKBREAK_DIRECTION_PRECISION             = new OptionNode("precision", BLOCKBREAK_DIRECTION, DataType.INTEGER);
    public final static OptionNode        BLOCKBREAK_DIRECTION_PENALTYTIME           = new OptionNode("penaltytime", BLOCKBREAK_DIRECTION, DataType.INTEGER);
    public final static OptionNode        BLOCKBREAK_DIRECTION_ACTIONS               = new OptionNode("actions", BLOCKBREAK_DIRECTION, DataType.ACTIONLIST);

    private final static OptionNode       BLOCKBREAK_NOSWING                         = new OptionNode("noswing", BLOCKBREAK, DataType.PARENT);
    public static final OptionNode        BLOCKBREAK_NOSWING_CHECK                   = new OptionNode("check", BLOCKBREAK_NOSWING, DataType.BOOLEAN);
    public static final OptionNode        BLOCKBREAK_NOSWING_ACTIONS                 = new OptionNode("actions", BLOCKBREAK_NOSWING, DataType.ACTIONLIST);

    private final static OptionNode       BLOCKPLACE                                 = new OptionNode("blockplace", ROOT, DataType.PARENT);
    public final static OptionNode        BLOCKPLACE_CHECK                           = new OptionNode("check", BLOCKPLACE, DataType.BOOLEAN);

    private final static OptionNode       BLOCKPLACE_REACH                           = new OptionNode("reach", BLOCKPLACE, DataType.PARENT);
    public final static OptionNode        BLOCKPLACE_REACH_CHECK                     = new OptionNode("check", BLOCKPLACE_REACH, DataType.BOOLEAN);
    public final static OptionNode        BLOCKPLACE_REACH_ACTIONS                   = new OptionNode("actions", BLOCKPLACE_REACH, DataType.ACTIONLIST);

    private final static OptionNode       BLOCKPLACE_DIRECTION                       = new OptionNode("direction", BLOCKPLACE, DataType.PARENT);
    public final static OptionNode        BLOCKPLACE_DIRECTION_CHECK                 = new OptionNode("check", BLOCKPLACE_DIRECTION, DataType.BOOLEAN);                 ;
    public final static OptionNode        BLOCKPLACE_DIRECTION_PENALTYTIME           = new OptionNode("penaltytime", BLOCKPLACE_DIRECTION, DataType.INTEGER);
    public final static OptionNode        BLOCKPLACE_DIRECTION_PRECISION             = new OptionNode("precision", BLOCKPLACE_DIRECTION, DataType.INTEGER);
    public final static OptionNode        BLOCKPLACE_DIRECTION_ACTIONS               = new OptionNode("actions", BLOCKPLACE_DIRECTION, DataType.ACTIONLIST);

    private final static OptionNode       CHAT                                       = new OptionNode("chat", ROOT, DataType.PARENT);
    public final static OptionNode        CHAT_CHECK                                 = new OptionNode("check", CHAT, DataType.BOOLEAN);

    private final static OptionNode       CHAT_COLOR                                 = new OptionNode("color", CHAT, DataType.PARENT);
    public final static OptionNode        CHAT_COLOR_CHECK                           = new OptionNode("check", CHAT_COLOR, DataType.BOOLEAN);
    public final static OptionNode        CHAT_COLOR_ACTIONS                         = new OptionNode("actions", CHAT_COLOR, DataType.ACTIONLIST);

    private final static OptionNode       CHAT_SPAM                                  = new OptionNode("spam", CHAT, DataType.PARENT);
    public final static OptionNode        CHAT_SPAM_CHECK                            = new OptionNode("check", CHAT_SPAM, DataType.BOOLEAN);
    public final static OptionNode        CHAT_SPAM_WHITELIST                        = new OptionNode("whitelist", CHAT_SPAM, DataType.STRING);
    public final static OptionNode        CHAT_SPAM_TIMEFRAME                        = new OptionNode("timeframe", CHAT_SPAM, DataType.INTEGER);
    public final static OptionNode        CHAT_SPAM_LIMIT                            = new OptionNode("messagelimit", CHAT_SPAM, DataType.INTEGER);
    public final static OptionNode        CHAT_SPAM_COMMANDLIMIT                     = new OptionNode("commandlimit", CHAT_SPAM, DataType.INTEGER);
    public final static OptionNode        CHAT_SPAM_ACTIONS                          = new OptionNode("actions", CHAT_SPAM, DataType.ACTIONLIST);

    private final static OptionNode       CHAT_EMPTY                                 = new OptionNode("empty", CHAT, DataType.PARENT);
    public final static OptionNode        CHAT_EMPTY_CHECK                           = new OptionNode("check", CHAT_EMPTY, DataType.BOOLEAN);
    public final static OptionNode        CHAT_EMPTY_ACTIONS                         = new OptionNode("actions", CHAT_EMPTY, DataType.ACTIONLIST);

    private final static OptionNode       FIGHT                                      = new OptionNode("fight", ROOT, DataType.PARENT);
    public final static OptionNode        FIGHT_CHECK                                = new OptionNode("check", FIGHT, DataType.BOOLEAN);

    private final static OptionNode       FIGHT_DIRECTION                            = new OptionNode("direction", FIGHT, DataType.PARENT);
    public final static OptionNode        FIGHT_DIRECTION_CHECK                      = new OptionNode("check", FIGHT_DIRECTION, DataType.BOOLEAN);
    public final static OptionNode        FIGHT_DIRECTION_PRECISION                  = new OptionNode("precision", FIGHT_DIRECTION, DataType.INTEGER);
    public final static OptionNode        FIGHT_DIRECTION_PENALTYTIME                = new OptionNode("penaltytime", FIGHT_DIRECTION, DataType.INTEGER);
    public final static OptionNode        FIGHT_DIRECTION_ACTIONS                    = new OptionNode("actions", FIGHT_DIRECTION, DataType.ACTIONLIST);

    private final static OptionNode       FIGHT_NOSWING                              = new OptionNode("noswing", FIGHT, DataType.PARENT);
    public final static OptionNode        FIGHT_NOSWING_CHECK                        = new OptionNode("check", FIGHT_NOSWING, DataType.BOOLEAN);
    public final static OptionNode        FIGHT_NOSWING_ACTIONS                      = new OptionNode("actions", FIGHT_NOSWING, DataType.ACTIONLIST);

    private final static OptionNode       FIGHT_REACH                                = new OptionNode("reach", FIGHT, DataType.PARENT);
    public final static OptionNode        FIGHT_REACH_CHECK                          = new OptionNode("active", FIGHT_REACH, DataType.BOOLEAN);
    public final static OptionNode        FIGHT_REACH_LIMIT                          = new OptionNode("distance", FIGHT_REACH, DataType.INTEGER);
    public final static OptionNode        FIGHT_REACH_PENALTYTIME                    = new OptionNode("penaltytime", FIGHT_REACH, DataType.INTEGER);
    public final static OptionNode        FIGHT_REACH_ACTIONS                        = new OptionNode("actions", FIGHT_REACH, DataType.ACTIONLIST);

    private final static OptionNode       FIGHT_SPEED                                = new OptionNode("speed", FIGHT, DataType.PARENT);
    public final static OptionNode        FIGHT_SPEED_CHECK                          = new OptionNode("active", FIGHT_SPEED, DataType.BOOLEAN);
    public final static OptionNode        FIGHT_SPEED_ATTACKLIMIT                    = new OptionNode("attacklimit", FIGHT_SPEED, DataType.INTEGER);
    public final static OptionNode        FIGHT_SPEED_ACTIONS                        = new OptionNode("actions", FIGHT_SPEED, DataType.ACTIONLIST);

    private final Map<OptionNode, Object> values;
    private final Configuration           defaults;

    public Configuration(Configuration defaults, boolean copyDefaults) {

        this.values = new HashMap<OptionNode, Object>();
        this.defaults = defaults;

        if(defaults != null && copyDefaults) {
            deepCopy(defaults, ROOT);
        }
    }

    private void deepCopy(Configuration defaults, OptionNode root) {

        if(root.isLeaf()) {
            this.set(root, defaults.getRecursive(root));
        } else {
            for(OptionNode child : root.getChildren()) {
                deepCopy(defaults, child);
            }
        }
    }

    public boolean getBoolean(OptionNode id) {
        if(id.getType() != DataType.BOOLEAN) {
            throw new IllegalArgumentException(id + " is no boolean value!");
        }

        return (Boolean) getRecursive(id);
    }

    public String getString(OptionNode id) {
        if(id.getType() != DataType.STRING) {
            throw new IllegalArgumentException(id + " is no string value!");
        }

        return (String) getRecursive(id);
    }

    public int getInteger(OptionNode id) {
        if(id.getType() != DataType.INTEGER) {
            throw new IllegalArgumentException(id + " is no integer value!");
        }

        return (Integer) getRecursive(id);
    }

    public LogLevel getLogLevel(OptionNode id) {
        if(id.getType() != DataType.LOGLEVEL) {
            throw new IllegalArgumentException(id + " is no loglevel value!");
        }

        return (LogLevel) getRecursive(id);
    }

    public ActionList getActionList(OptionNode id) {

        if(id.getType() != DataType.ACTIONLIST) {
            throw new IllegalArgumentException(id + " is no actionlist value!");
        }

        return (ActionList) getRecursive(id);
    }

    public void setValue(OptionNode id, Integer value) {
        if(id.getType() != DataType.INTEGER) {
            throw new IllegalArgumentException(id + " is no integer value!");
        }
        set(id, value);
    }

    public void setValue(OptionNode id, LogLevel value) {
        if(id.getType() != DataType.LOGLEVEL) {
            throw new IllegalArgumentException(id + " is no loglevel value!");
        }
        set(id, value);
    }

    public void setValue(OptionNode id, Boolean value) {
        if(id.getType() != DataType.BOOLEAN) {
            throw new IllegalArgumentException(id + " is no boolean value!");
        }
        set(id, value);
    }

    public void setValue(OptionNode id, String value) {
        if(id.getType() != DataType.STRING) {
            throw new IllegalArgumentException(id + " is no string value!");
        }
        set(id, value);
    }

    public void setValue(OptionNode id, ActionList value) {
        if(id.getType() != DataType.ACTIONLIST) {
            throw new IllegalArgumentException(id + " is no actionlist value!");
        }
        set(id, value);
    }

    protected void set(OptionNode id, Object value) {
        if(value == null) {
            this.values.remove(id);
        } else {
            this.values.put(id, value);
        }
    }

    protected Object getRecursive(OptionNode id) {
        Object o = get(id);

        if(o != null) {
            return o;
        }

        if(defaults != null) {
            return defaults.getRecursive(id);
        }

        return null;
    }

    protected Configuration getDefaults() {
        return defaults;
    }

    protected Object get(OptionNode id) {
        return this.values.get(id);
    }
}
