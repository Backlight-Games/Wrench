package dev.encode42.wrench.task;

import dev.encode42.copper.config.Config;
import dev.encode42.wrench.Wrench;
import net.dv8tion.jda.api.JDA;

import java.util.List;
import java.util.SplittableRandom;
import java.util.TimerTask;

public class NicknameTask extends TimerTask {
    public void run() {
        Config config = Wrench.getConfig();
        JDA jda = Wrench.getJDA();
        SplittableRandom random = Wrench.getRandom();

        List<?> fileTypes = config.getList("file-types");
        String fileType = fileTypes.get(random.nextInt(fileTypes.size())).toString();

        jda.getGuildById("776516620226396170").getMemberById("392334030596603904").modifyNickname("GVN" + fileType).queue();
    }
}
