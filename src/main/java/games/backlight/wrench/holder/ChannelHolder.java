package games.backlight.wrench.holder;

import net.dv8tion.jda.api.entities.TextChannel;

public class ChannelHolder {
    private static TextChannel waitingRoom;
    private static TextChannel pending;
    private static TextChannel log;

    public static TextChannel getLog() {
        return log;
    }

    public static void setLog(TextChannel log) {
        ChannelHolder.log = log;
    }
}
