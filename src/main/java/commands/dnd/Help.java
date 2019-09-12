package commands.dnd;

import commands.Command;
import utils.Tuple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Help extends DndCommand {
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
        StringBuilder sb = new StringBuilder();
        sb.append("Available commands:\n");
        getWorkspace().getCommandList().stream().sorted(Comparator.comparing(Command::getCommandName)).forEach(command -> {
            final String commandName = command.getCommandName();
            final StringBuilder parameterString = new StringBuilder();
            List<Tuple<String, Class>> zip = zip(command.getParameterNames(), command.getParameterTypes());
            zip.forEach(tuple -> parameterString.append(tuple.fst())
                    .append(":")
                    .append(tuple.snd().getSimpleName())
                    .append(" "));
            sb.append("\t").append(commandName).append("\t\t").append(parameterString.toString()).append("\n");
        });

        return sb.toString();
    }
}
