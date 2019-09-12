package commands.admin;

import commands.Command;
import discord4j.core.object.entity.User;
import lombok.Getter;
import java.util.*;

public abstract class AdminCommand extends Command {

    @Getter
    private static Set<String> allowedUsers = new HashSet<>(Arrays.asList("Tonyyy", "Don No"));
    @Getter
    private static Map<String, AdminCommand> commandMap = initCommandMap();

    protected abstract String runAdmMode(List<Object> parameters);

    @Override
    protected String run(List<Object> parameters) {
        User user = Command.getCurrentQuery().getAuthor().block();

        if (user != null && allowedUsers.contains(user.getUsername())) {
            return runAdmMode(parameters);
        }

        return "You do not have permission to do this!";
    }

    private static Map<String, AdminCommand> initCommandMap() {
        return initCommandMap(AdminCommand.class);
    }
}
