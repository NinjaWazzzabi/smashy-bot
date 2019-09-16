package database;

import discord4j.core.object.entity.User;
import org.jetbrains.annotations.NotNull;
import user.BotUser;
import user.Powerlevel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockDatabase implements Database {

    private List<BotUser> botUsers = new ArrayList<>(Arrays.asList(new BotUser("151693382995935232", Powerlevel.GOD)));

    @NotNull
    @Override
    public BotUser getBotUser(@NotNull User user) {
        return botUsers.stream()
                .filter(botUser -> botUser.getSnowflakeId().equals(user.getId().asString()))
                .findFirst()
                .orElse(new BotUser(user.getId().asString(), Powerlevel.USER));
    }

    @NotNull
    @Override
    public List<BotUser> getBotUsers() {
        return botUsers;
    }

    @Override
    public boolean setBotUser(@NotNull BotUser botUser) {
        return false;
    }

    @Override
    public boolean removeBotUser(@NotNull BotUser botUser) {
        return false;
    }
}
