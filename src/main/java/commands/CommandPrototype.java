package commands;

import lombok.Getter;

import java.util.List;

public class CommandPrototype<T extends ICommand> {

    final Class<T> commandClass;
    final List<Class> constructorArgs;
    @Getter
    private final String name;

    CommandPrototype(Class<T> commandClass, List<Class> arguments) {
        this.commandClass = commandClass;
        String simpleName = commandClass.getSimpleName();
        name = simpleName.substring(0, 1).toLowerCase().concat(simpleName.substring(1));
        constructorArgs = arguments;
    }
}
