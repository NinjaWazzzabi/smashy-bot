package commands.admin;

import commands.AbstractCommand;

public class DispSnowflake extends AdminCommand {

    @Override
    public void run() {
        sendBack(context.user.getId().asString());
    }
}
