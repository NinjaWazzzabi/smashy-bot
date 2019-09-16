package commands.admin;

import commands.AbstractCommand;
import commands.ICommand;
import user.Powerlevel;


public abstract class AdminCommand extends AbstractCommand implements ICommand {

    @Override
    public Powerlevel requiredLevel() {
        return Powerlevel.ADMIN;
    }
}
