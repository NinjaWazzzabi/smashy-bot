package commands.dnd;

import commands.Command;
import lombok.Getter;
import java.util.Map;

public abstract class DndCommand extends Command {

    @Getter
    private static Workspace workspace = new Workspace();

    @Getter
    private static Map<String, DndCommand> commandMap = initCommandMap();

    static Map<String, DndCommand> initCommandMap() {
        Map<String, DndCommand> stringCommandMap = initCommandMap(DndCommand.class);
        stringCommandMap.values().forEach(it -> workspace.getCommandList().add(it));
        return stringCommandMap;
    }
}
