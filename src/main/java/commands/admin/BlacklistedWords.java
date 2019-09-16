package commands.admin;

import utils.PersistentData;

import java.util.ArrayList;
import java.util.List;

public class BlacklistedWords extends AdminCommand {

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder();
        PersistentData.blacklistedWords.forEach(s -> sb.append(s).append("\n"));
        context.messageChannel.createMessage(sb.toString()).subscribe();
    }
}
