package commands.admin;

import commands.Command;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.User;

import java.util.ArrayList;
import java.util.List;

public class KickMe extends AdminCommand {

    @Override
    public List<Class> getParameterTypes() {
        return new ArrayList<>();
    }

    @Override
    public List<String> getParameterNames() {
        return new ArrayList<>();
    }

    @Override
    protected String runAdmMode(List<Object> parameters) {
        Guild guild = Command.getCurrentQuery().getGuild().block();
        User user = Command.getCurrentQuery().getAuthor().block();

        if (user != null && guild != null) {
            guild.kick(user.getId()).subscribe();
        } else {
            return "Guild or user was null";
        }

        return "yeet";
    }

}
