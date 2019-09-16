package commands.admin;

import commands.dnd.DndCommand;
import user.Powerlevel;

public class SaveState extends AdminCommand {

    @Override
    public Powerlevel requiredLevel() {
        return Powerlevel.GOD;
    }

    @Override
    public void run() {
        DndCommand.getWorkspace().saveToFile();
        sendBack("Saved state");
    }
}
