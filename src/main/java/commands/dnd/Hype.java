package commands.dnd;

import discord4j.core.object.entity.User;
import utils.Responses;

import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

public class Hype extends DndCommand {

    @Override
    public void run() {
        Workspace workspace = getWorkspace();

        if (workspace.getNextDndHang() == null) {
            sendBack("Can't hype when there's no next D&D häng :(");
        } else if (workspace.getNextDndHang().isBefore(MonthDay.now())) {
            sendBack(workspace.getNextDndHang().toString() + " must have been a good D&D session!!");
        } else {
            User user = context.user;

            if (user != null) {
                workspace.getHypedPeople().add(user.getUsername());
                sendBack(user.getMention() + " has hyped for the next session!! " + Responses.bigStuff());
            } else {
                sendBack("WHoa there, that username is impossible for me to read. Well played!");
            }
        }
    }
}
