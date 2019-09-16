package commands;

import commands.admin.AdminCommand;
import commands.dnd.DndCommand;
import commands.minecraft.MinecraftCommand;
import org.reflections.Reflections;

import java.util.*;
import java.util.stream.Collectors;

public class CommandDatabase {

    private Map<String, Map<String, CommandPrototype>> commandMap = new HashMap<>();


    public CommandDatabase() {
        commandMap.put("!adm", generateNameCommandMap(AdminCommand.class));
        commandMap.put("!mc", generateNameCommandMap(MinecraftCommand.class));
        commandMap.put("!dnd", generateNameCommandMap(DndCommand.class));
    }

    public Set<String> getCommandGroups() {
        return commandMap.keySet();
    }

    public Optional<Set<CommandPrototype>> getGroupCommands(String groupName) {
        Map<String, CommandPrototype> nameMap = commandMap.get(groupName);
        if (nameMap != null) {
            return Optional.of(new HashSet<>(nameMap.values()));
        }

        return Optional.empty();
    }

    public Optional<CommandPrototype> getUnbuiltCommand(String groupName, String commandName) {
        Map<String, CommandPrototype> nameMap = commandMap.get(groupName);

        if (nameMap != null) {
            return Optional.ofNullable(nameMap.get(commandName));
        } else {
            return Optional.empty();
        }
    }

    private Map<String, CommandPrototype> generateNameCommandMap(Class commandParent) {
        HashMap<String, CommandPrototype> nameCommandMap = new HashMap<>();

        Reflections reflections = new Reflections(Command.class.getPackage().getName());
        Set<Class> subTypesOf = reflections.getSubTypesOf(commandParent);

        for (Class aClass : subTypesOf) {
            List<Class> parameters = new ArrayList<>(Arrays.asList(aClass.getDeclaredConstructors()[0].getParameterTypes()));
            CommandPrototype commandPrototype = new CommandPrototype<>(aClass, parameters);
            nameCommandMap.put(commandPrototype.getName(), commandPrototype);
        }

        return nameCommandMap;
    }
}
