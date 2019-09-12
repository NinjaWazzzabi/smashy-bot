package commands.dnd;

import discord4j.core.object.entity.User;
import utils.Responses;

import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

public class Hype extends DndCommand {
    @Override
    public List<String> getParameterNames() {
        return new ArrayList<>();
    }

    @Override
    public List<Class> getParameterTypes() {
        return new ArrayList<>();
    }

    @Override
    protected String run(List<Object> parameters) {
        Workspace workspace = getWorkspace();

        if (workspace.getNextDndHang() == null) {
            return "Can't hype when there's no next D&D häng :(";
        } else if (workspace.getNextDndHang().isBefore(MonthDay.now())) {
            return workspace.getNextDndHang().toString() + " must have been a good D&D session!!";
        } else {
            User user = getCurrentQuery().getAuthor().block();

            if (user != null) {
                workspace.getHypedPeople().add(user.getUsername());
                return user.getMention() + " has hyped for the next session!! " + Responses.bigStuff();
            } else {
                return "WHoa there, that username is impossible for me to read. Well played!";
            }
        }
    }
}
