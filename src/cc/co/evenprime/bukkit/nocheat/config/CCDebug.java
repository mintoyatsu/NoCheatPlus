package cc.co.evenprime.bukkit.nocheat.config;

public class CCDebug {

    public final boolean showchecks;
    public final boolean overrideIdiocy;
    public final boolean debugmessages;

    public CCDebug(Configuration data) {

        showchecks = data.getBoolean(Configuration.DEBUG_SHOWACTIVECHECKS);
        overrideIdiocy = data.getBoolean(Configuration.DEBUG_COMPATIBILITY);
        debugmessages = data.getBoolean(Configuration.DEBUG_MESSAGES);
    }
}
