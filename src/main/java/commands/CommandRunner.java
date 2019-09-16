package commands;

import database.Database;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;

import java.util.Arrays;
import java.util.Optional;

public class CommandRunner {

    private final Database database;
    private final CommandBuilder commandBuilder;

    public CommandRunner(Database database, CommandBuilder commandBuilder) {
        this.database = database;
        this.commandBuilder = commandBuilder;
    }

    public void runCommand(CommandPrototype prototype, Context context) {
        // TODO: 16/09/2019
        String[] arguments = extractArguments(context.message);
        @SuppressWarnings("unchecked assignment")
        Optional<ICommand> maybeCommand = commandBuilder.buildCommandRaw(prototype, arguments);

        maybeCommand.ifPresent(command -> {
            command.setContext(context);
            if (userHasThePower(command, context.user)) {
                command.run();
            } else {
                context.messageChannel.createMessage("Your powerlevel isn't enough to run that command!").subscribe();
            }
        });
    }

    private boolean userHasThePower(ICommand command, User user) {
        return database.getBotUser(user).getLevel().ordinal() <= command.requiredLevel().ordinal();
    }

    private String[] extractArguments(Message message) {
        String messageContents = message.getContent().orElse("").trim();
        String commandAndArgs = messageContents.substring(messageContents.split(" ")[0].length()).trim();

        if (commandAndArgs.length() > 0) {
            String[] split = commandAndArgs.split("[ ,]++");
            String commandName = split[0];
            return Arrays.stream(split).filter(s -> !s.equals(commandName)).toArray(String[]::new);
        }

        return new String[0];
    }
}
