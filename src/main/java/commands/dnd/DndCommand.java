package commands.dnd;

import commands.AbstractCommand;
import lombok.Getter;
import java.util.Map;

public abstract class DndCommand extends AbstractCommand {

    @Getter
    private static Workspace workspace = new Workspace();

}
