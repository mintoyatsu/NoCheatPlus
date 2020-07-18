package cc.co.evenprime.bukkit.nocheat.config;

import java.util.HashMap;
import java.util.Map;
import cc.co.evenprime.bukkit.nocheat.config.util.OptionNode;

/**
 * Textual explainations of options, will be displayed at the end of the
 * config.txt file
 * 
 */
public class Explainations {

    private static final Map<OptionNode, String> explainations = new HashMap<OptionNode, String>();

    static {

        set(Configuration.LOGGING_ACTIVE, "If true, enable NoCheat logging.\nSome messages may still appear in the console, e.g. plugin errors.");

        set(Configuration.LOGGING_PREFIX, "The short text that appears before NoCheat messages. Color codes are &0-&9 and &A-&F");
        set(Configuration.LOGGING_FILENAME, "Where logs that go to the logfile are stored. You can have different files for different worlds.");
        set(Configuration.LOGGING_FILELEVEL, "What log-level need messages to have to get stored in the logfile. Values are:\n low: all messages\n med: med and high messages only\n high: high messages only\n off: no messages at all.");
        set(Configuration.LOGGING_CONSOLELEVEL, "What log-level need messages to have to get displayed in your server console. Values are:\n low: all messages\n med: med and high messages only\n high: high messages only\n off: no messages at all.");
        set(Configuration.LOGGING_CHATLEVEL, "What log-level need messages to have to get displayed in the in-game chat. Values are:\n low: all messages\n med: med and high messages only\n high: high messages only\n off: no messages at all.");

        set(Configuration.DEBUG_SHOWACTIVECHECKS, "Show a list of active checks in the console when NoCheat is loaded.");
        set(Configuration.DEBUG_COMPATIBILITY, "Prevent other poorly written plugins from cancelling PlayerMove events.\nIt is recommended to keep this on.");
        set(Configuration.DEBUG_MESSAGES, "Show debug messages in the console, e.g. lag warnings.");

        set(Configuration.INVENTORY_CHECK, "If true, enable checks on Inventory events.");

        set(Configuration.INVENTORY_DROP_CHECK, "If true, check if a player drops too many items within a set time frame.");
        set(Configuration.INVENTORY_DROP_TIMEFRAME, "How long (in seconds) dropped items should count towards the drop limit.");
        set(Configuration.INVENTORY_DROP_LIMIT, "How many items are allowed to be dropped by a player per time frame.");
        set(Configuration.INVENTORY_DROP_ACTIONS, "What should be done if a player is trying to cause lag by dropping too many items at once.\nUnit is item drops above the limit.");

        set(Configuration.MOVING_CHECK, "If true, enable checks on PlayerMove events.");

        set(Configuration.MOVING_RUNFLY_CHECK, "If true, check if a player is flying or walking/swimming/sneaking too fast.\nThis also checks if a player is moving while sleeping, or is moving above Y 127 in the Nether.");
        set(Configuration.MOVING_RUNFLY_WALKINGSPEEDLIMIT, "Set the speed limit for moving horizontal while on land.\nUnit is 1/100 of a block, default is 22.");
        set(Configuration.MOVING_RUNFLY_JUMPHEIGHT, "Set how high a player is allowed to jump.\nUnit is 1/100 of a block, default is 135.");
        set(Configuration.MOVING_RUNFLY_CHECKSWIMMING, "Use a separate speed limit for swimming players.");
        set(Configuration.MOVING_RUNFLY_SWIMMINGSPEEDLIMIT, "Set the speed limit for moving horizontal while in water.\nUnit is 1/100 of a block, default is 18.");
        set(Configuration.MOVING_RUNFLY_CHECKSNEAKING, "Use a separate speed limit for sneaking players.");
        set(Configuration.MOVING_RUNFLY_SNEAKINGSPEEDLIMIT, "Set the speed limit for moving horizontal while sneaking.\nUnit is 1/100 of a block, default is 14.");
        set(Configuration.MOVING_RUNFLY_CHECKVEHICLE, "Limit speed of players in vehicles.");
        set(Configuration.MOVING_RUNFLY_VEHICLESPEEDLIMIT, "Set the speed limit for moving horizontal while in a vehicle.\nUnit is 1/100 of a block, default is 44.");
        set(Configuration.MOVING_RUNFLY_ACTIONS, "What should be done if a player moves faster than the speed limit(s) or jumps higher than allowed.\nUnits are in 1/100 of a block above the limit.");

        set(Configuration.MOVING_RUNFLY_CHECKNOFALL, "If true, check if a player is avoiding fall damage on a hacked client.");
        set(Configuration.MOVING_RUNFLY_NOFALLAGGRESSIVE, "This will catch additional types of hacks and deal fall damage to players directly.");
        set(Configuration.MOVING_RUNFLY_NOFALLMULTIPLIER, "The percentage of fall damage that should be dealt to the player.\nNoCheat will almost always underestimate fall damage so using a value greater than 100 percent is advised.\nUnit is percent of the estimated original fall damage, default is 200 percent.");
        set(Configuration.MOVING_RUNFLY_NOFALLACTIONS, "What should be done if a player is avoiding fall damage.\nUnit is number of blocks the player fell.");

        set(Configuration.MOVING_RUNFLY_ALLOWLIMITEDFLYING, "If true, instead of doing the above checks for walking/swimming/sneaking, allow flying and only limit the flying speed.");
        set(Configuration.MOVING_RUNFLY_FLYINGSPEEDLIMITVERTICAL, "Set the speed limit for moving vertical while flying.\nUnit is 1/100 of a block, default is 100.");
        set(Configuration.MOVING_RUNFLY_FLYINGSPEEDLIMITHORIZONTAL, "Set the speed limit for moving horizontal while flying.\nUnit is 1/100 of a block, default is 60.");
        set(Configuration.MOVING_RUNFLY_FLYINGHEIGHTLIMIT, "Set the Y-coordinate height limit that a player may ascend to when flying.\nUnit is number of blocks, default is 500.");
        set(Configuration.MOVING_RUNFLY_FLYINGACTIONS, "What should be done if a player flies faster than the speed limit(s).\nUnits are in 1/100 of a block above the speed limit.");

        set(Configuration.MOVING_MOREPACKETS_CHECK, "If true, check if a player is sending too many packets per second.\nUnder normal circumstances, a client shouldn't send more than 22 packets per second.");
        set(Configuration.MOVING_MOREPACKETS_ACTIONS, "What should be done if a player sends more packets than normal.\nUnits are packets per second above the limit.");

        set(Configuration.MOVING_MOREPACKETSVEHICLE_CHECK, "If true, check if a player is sending too many packets per second while inside a vehicle.\nUnder normal circumstances, a client shouldn't send more than 22 packets per second.");
        set(Configuration.MOVING_MOREPACKETSVEHICLE_ACTIONS, "What should be done if a player sends more packets than normal while inside a vehicle.\nUnits are packets per second above the limit.");

        set(Configuration.BLOCKBREAK_CHECK, "If true, enable checks on BlockBreak events.");

        set(Configuration.BLOCKBREAK_REACH_CHECK, "If true, check if a player is breaking blocks too far away.");
        set(Configuration.BLOCKBREAK_REACH_ACTIONS, "What should be done if a player is breaking blocks too far away.\nUnit is number of break attempts beyond the limit.");

        set(Configuration.BLOCKBREAK_DIRECTION_CHECK, "If true, check if a player is not looking at the block they're breaking.");
        set(Configuration.BLOCKBREAK_DIRECTION_CHECKINSTABREAKBLOCKS, "If true, also compare line of sight for blocks that break instantly, e.g. tall grass.\nThis is very likely to lead to false positives.");
        set(Configuration.BLOCKBREAK_DIRECTION_PRECISION, "How strict to be when comparing the line of sight with the block location.\nThe value represents the amount of 1/100 blocks that the player is allowed to look beyond.\n0.5 blocks (value 50) is a good default value.");
        set(Configuration.BLOCKBREAK_DIRECTION_PENALTYTIME, "How long until the player can break blocks again.\nUnit is milliseconds, default is 0.3 seconds (value 300).");
        set(Configuration.BLOCKBREAK_DIRECTION_ACTIONS, "What should be done if a player is breaking blocks outside their line of sight.\nUnit is the sum of the distance (in blocks) between the location of the block and where the player looked.");

        set(Configuration.BLOCKBREAK_NOSWING_CHECK, "If true, check if a player did not swing their arms before breaking a block.");
        set(Configuration.BLOCKBREAK_NOSWING_ACTIONS, "What should be done if a player didn't swing their arm.\nUnit is number of attacks without arm swinging.");

        set(Configuration.BLOCKPLACE_CHECK, "If true, enable checks on BlockPlace events.");

        set(Configuration.BLOCKPLACE_REACH_CHECK, "If true, check if a player is placing blocks too far away.");
        set(Configuration.BLOCKPLACE_REACH_ACTIONS, "What should be done if a player is placing blocks too far away.\nUnit is the number of place attempts beyond the limit.");

        set(Configuration.BLOCKPLACE_DIRECTION_CHECK, "If true, check if a player is looking at the block that they're placing.");
        set(Configuration.BLOCKPLACE_DIRECTION_PRECISION, "How strict to be when comparing the line of sight with the block location.\nThe value represents the amount of 1/100 blocks that the player is allowed to look beyond.\n0.75 blocks (value 75) is a good default value.");
        set(Configuration.BLOCKPLACE_DIRECTION_PENALTYTIME, "How long until the player can place blocks again.\nUnit is milliseconds, default is 0.1 seconds (value 100).");
        set(Configuration.BLOCKPLACE_DIRECTION_ACTIONS, "What should be done if a player is placing blocks outside their line of sight.\nUnit is the sum of the distance (in blocks) between the location of the block and where the player looked.");

        set(Configuration.CHAT_CHECK, "If true, enable checks on PlayerChat events.");

        set(Configuration.CHAT_COLOR_CHECK, "If true, check if a player is trying to send a chat message with embedded color codes.");
        set(Configuration.CHAT_COLOR_ACTIONS, "What should be done if a player is trying to send a chat message with embedded color codes.\nThis DOES affect other plugins that colorize player chat messages and will increase player VL.\n\"cancel\" means the color codes get removed from the message.\nUnit is number of colored chat messages sent by the player.");

        set(Configuration.CHAT_SPAM_CHECK, "If true, check if a player is sending too many messages within a set time frame.");
        set(Configuration.CHAT_SPAM_WHITELIST, "A comma-delimited list of characters or phrases to be ignored by the spam check.\nIf a message or command begins with one of these, it will always be sent.");
        set(Configuration.CHAT_SPAM_TIMEFRAME, "How long (in seconds) chat messages or commands should count towards their respective limits.\nIf the limit is reached before the time in seconds have passed, any further messages will not be sent.\nAfter the time in seconds have passed, messages may be sent again.");
        set(Configuration.CHAT_SPAM_LIMIT, "How many chat messages the player is allowed to send per time frame.");
        set(Configuration.CHAT_SPAM_COMMANDLIMIT, "How many commands beginning with '/' the player is allowed to send per time frame.");
        set(Configuration.CHAT_SPAM_ACTIONS, "What should be done if a player is trying to spam the chat.\nUnit is number of chat messages or commands above the limit.");

        set(Configuration.CHAT_EMPTY_CHECK, "If true, check if a player is trying to send an empty chat message.");
        set(Configuration.CHAT_EMPTY_ACTIONS, "What should be done if a player is trying to send an empty chat message.\nUnit is the number of empty chat messages sent by the player.");

        set(Configuration.FIGHT_CHECK, "If true, enable checks on Fighting events.");

        set(Configuration.FIGHT_DIRECTION_CHECK, "If true, check if a player attacked an entity without looking at it.");
        set(Configuration.FIGHT_DIRECTION_PRECISION, "How strict to be when comparing the line of sight with the position of the target.\nDefault is 75 which means a player may look 0.75 blocks past the actual target.\nBecause the target is moving, this value should be relatively high.");
        set(Configuration.FIGHT_DIRECTION_PENALTYTIME, "How long until the player can attack again.\nUnit is milliseconds, default is 0.5 seconds (value 500).");
        set(Configuration.FIGHT_DIRECTION_ACTIONS, "What should be done if a player attacks entities that are not in their field of view.\nUnit is the square root of the distance in blocks between the target and where the player looked.");

        set(Configuration.FIGHT_NOSWING_CHECK, "If true, check if a player did not swing their arms before attacking.");
        set(Configuration.FIGHT_NOSWING_ACTIONS, "What should be done if a player didn't swing their arm.\nUnit is number of attacks without arm swinging.");

        set(Configuration.FIGHT_REACH_CHECK, "If true, check if a player attacked beyond a certain distance.");
        set(Configuration.FIGHT_REACH_LIMIT, "The maximum distance that attacks should be allowed.\nDefault is 4 blocks (value 400).");
        set(Configuration.FIGHT_REACH_PENALTYTIME, "How long until the player can attack again.\nUnit is milliseconds, default is 0.5 seconds (value 500).");
        set(Configuration.FIGHT_REACH_ACTIONS, "What should be done if a player attacks entities that are out of range.\nUnit is the square root of the distance in blocks between the target and the player.");

        set(Configuration.FIGHT_SPEED_CHECK, "If true, check if a player attacked extremely fast within a short period of time.");
        set(Configuration.FIGHT_SPEED_ATTACKLIMIT, "How many attacks may a player start within 1 second.\nConsider setting this to a value that's close to how fast you believe players can click their mouse.");
        set(Configuration.FIGHT_SPEED_ACTIONS, "What should be done if a player attacks too quickly.\nUnit is attacks per second.");
    }

    private static void set(OptionNode id, String text) {
        explainations.put(id, text);
    }

    public static String get(OptionNode id) {
        String result = explainations.get(id);

        if(result == null) {
            System.out.println("Missing description for " + id.getName());
            result = "No description available";
        }

        return result;
    }
}
