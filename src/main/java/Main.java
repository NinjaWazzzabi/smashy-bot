import commands.*;
import database.Database;
import database.MockDatabase;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import reactor.util.Loggers;
import utils.PersistentData;
import utils.Responses;
import utils.Tuple;

import java.util.*;

public class Main {

    private static final String MC_CH_ID = "617727734210363403";
    private final User self;
    private final DiscordClient client;
    private final CommandDatabase commandDatabase;
    private final Database database;
    private final CommandRunner commandRunner;

//    private final Map<String, Map<String, ? extends Command>> tagMap = new HashMap<>();

    public Main(DiscordClient client) {
        this.client = client;
        self = client.getSelf().block();
        commandDatabase = new CommandDatabase();
        database = new MockDatabase();
        commandRunner = new CommandRunner(database, new CommandBuilder());

        // Pair every message with it's corresponding tag (First word)
//        Flux<Tuple<String, Message>> tagPairs = client.getEventDispatcher().on(MessageCreateEvent.class)
//                .map(MessageCreateEvent::getMessage)
//                .filter(message -> !self.equals(message.getAuthor().block()))
//                .map(message -> new Tuple<>(message.getContent().orElse("").trim().split("[ ]++")[0], message));
//
//        // Run all known command tags (ex: !dnd or !adm)
//        tagPairs.filter(tuple -> tagMap.containsKey(tuple.fst()))
//                .map(tuple -> new Tuple<>(matchCommand(tuple.snd(), tagMap.get(tuple.fst())), tuple.snd()))
//                .flatMap(tuple -> tuple.snd().getChannel().flatMap(
//                        messageChannel -> messageChannel.createMessage(tuple.fst())
//                )).subscribe();
//
//        // See if there's something to respond with to non-commands
//        tagPairs.filter(tuple -> !tagMap.containsKey(tuple.fst()))
//                .map(tuple -> new Tuple<>(randomInput(tuple.snd()), tuple.snd()))
//                .filter(tuple -> tuple.fst().length() > 0)
//                .flatMap(tuple -> tuple.snd().getChannel().flatMap(
//                        messageChannel -> messageChannel.createMessage(tuple.fst())
//                )).subscribe();

        // TODO: 17/09/2019 No anti swearing and stuff rn :(

        client.getEventDispatcher()
                .on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .map(it -> new Tuple<>(findCommand(it), createContext(it)))
                .filter(it -> it.fst().isPresent() && it.snd().isPresent())
                .map(it -> new Tuple<>(it.fst().get(), it.snd().get()))
                .toIterable()
//                .forEach(t -> System.out.println(t.fst().getName()));
                .forEach(t -> commandRunner.runCommand(t.fst(), t.snd()));
    }

    private Optional<CommandPrototype> findCommand(Message message) {
        String queryContent = message.getContent().orElse("").trim();
        String[] parts = queryContent.split("[ ,]++");

        if (parts.length >= 2) {
            String commandGroup = parts[0];
            String commandName = parts[1];

            return commandDatabase.getUnbuiltCommand(commandGroup, commandName);
        } else {
            return Optional.empty();
        }
    }

    private Optional<Context> createContext(Message message) {
        User author = message.getAuthor().block();

        if (author == null) {
            return Optional.empty();
        }
        Optional<Context> context = Optional.of(new Context(
                message,
                author,
                database.getBotUser(author),
                message.getChannel().block(),
                message.getGuild().block()
        ));
        return context;
    }

//    private Optional<CommandPrototype> matchCommand(Message query, Set<CommandPrototype> commandSet) {
//        Command.setCurrentQuery(query);
//        String queryContent = query.getContent().orElse("").trim();
//        queryContent = queryContent.substring(queryContent.split(" ")[0].length()).trim();
//
//        if (queryContent.length() > 0) {
//            String[] split = queryContent.split("[ ,]++");
//            System.out.println(split[0]);
//
//            if (commandSet.containsKey(split[0])) {
//                return commandSet.get(split[0])
//                        .runCommand(Arrays.stream(split).filter(s -> !split[0].equals(s)).toArray(String[]::new));
//            } else {
//                return Responses.unknownCommand();
//            }
//        } else {
//            return Responses.identification();
//        }
//    }

    private String randomInput(Message query) {
        String input = query.getContent().orElse("").toLowerCase();

        if (isInsulted(input)) {
            return Responses.insult();
        }

        if (Arrays.stream(input.split("[ ]++")).anyMatch(PersistentData.blacklistedWords::contains)) {
            User user = query.getAuthor().block();

            if (user != null) {
                if (!PersistentData.userViolations.containsKey(user.getUsername())) {
                    PersistentData.userViolations.put(user.getUsername(), 0);
                }

                int violations = PersistentData.userViolations.get(user.getUsername()) + 1;
                PersistentData.userViolations.remove(user.getUsername());
                PersistentData.userViolations.put(user.getUsername(), violations);

                if (violations >= PersistentData.MAX_USER_VIOLATIONS) {
                    Guild guild = query.getGuild().block();

                    if (guild != null) {
                        guild.kick(user.getId()).subscribe();
                        return user.getUsername() + " has been kicked!";
                    }
                } else if (violations == PersistentData.MAX_USER_VIOLATIONS - 1) {
                    return user.getUsername() + "! Next time there will be consequences!!";
                }
            }

            String name = user != null ? user.getMention() : "";
            return name + "! " + Responses.noBadLanguage();
        }

        return "";
    }

    private boolean isInsulted(String content) {
        return PersistentData.blacklistedWords.stream()
                .anyMatch(s -> content.contains(s.toLowerCase()) && content.contains("you"));
    }

    public static void main(String[] args) {
        String TOKEN = "NTEyOTc2Njg4MjgwMjQwMTQw.D0CIDg.Cm4OvLIYp-QE9Tg6quy2-WKZyiw";

        Loggers.useCustomLoggers(s -> new ShitLogger());
        DiscordClient client = new DiscordClientBuilder(TOKEN).build();

        client.getEventDispatcher().on(ReadyEvent.class).subscribe(readyEvent -> {
            System.out.println("Logged in as: " + readyEvent.getSelf().getUsername());
            new Main(client);
        });

        client.login().block();
    }
}
