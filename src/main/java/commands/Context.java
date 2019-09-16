package commands;

import discord4j.core.object.entity.*;
import user.BotUser;

public class Context {

    public Context(Message message, User user, BotUser botUser, MessageChannel messageChannel, Guild guild) {
        this.message = message;
        this.user = user;
        this.botUser = botUser;
        this.messageChannel = messageChannel;
        this.guild = guild;
    }

    public final Message message;
    public final User user;
    public final BotUser botUser;
    public final MessageChannel messageChannel;
    public final Guild guild;

}
