import commands.Command;
import commands.admin.AdminCommand;
import commands.dnd.DndCommand;
import commands.minecraft.MinecraftCommand;
import discord4j.core.DiscordClient;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.User;
import discord4j.core.object.util.Snowflake;
import reactor.core.publisher.Flux;
import reactor.util.Loggers;
import utils.PersistentData;
import utils.Responses;
import utils.Tuple;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    private static final String MC_CH_ID = "617727734210363403";

    private final Map<String, Map<String, ? extends Command>> tagMap = new HashMap<>();

    public Main(DiscordClient client) {
        User self = client.getSelf().block();
        tagMap.put("!dnd", DndCommand.getCommandMap());
        tagMap.put("!adm", AdminCommand.getCommandMap());
        tagMap.put("!mc", MinecraftCommand.getCommandMap());

//        MinecraftCommand.setMcChannelCallback(Optional.of(s -> {
//            ((MessageChannel) client.getChannelById(Snowflake.of(MC_CH_ID)).block()).createMessage(s);
//        }));

        DndCommand.getWorkspace().loadFromFile();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000 * 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                DndCommand.getWorkspace().saveToFile();
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ": Saved state");
            }
        }).start();


        // Pair every message with it's corresponding tag (First word)
        Flux<Tuple<String, Message>> tagPairs = client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> !self.equals(message.getAuthor().block()))
                .map(message -> new Tuple<>(message.getContent().orElse("").trim().split("[ ]++")[0], message));

        // Run all known command tags (ex: !dnd or !adm)
        tagPairs.filter(tuple -> tagMap.containsKey(tuple.fst()))
                .map(tuple -> new Tuple<>(matchCommand(tuple.snd(), tagMap.get(tuple.fst())), tuple.snd()))
                .flatMap(tuple -> tuple.snd().getChannel().flatMap(
                        messageChannel -> messageChannel.createMessage(tuple.fst())
                )).subscribe();

        // See if there's something to respond with to non-commands
        tagPairs.filter(tuple -> !tagMap.containsKey(tuple.fst()))
                .map(tuple -> new Tuple<>(randomInput(tuple.snd()), tuple.snd()))
                .filter(tuple -> tuple.fst().length() > 0)
                .flatMap(tuple -> tuple.snd().getChannel().flatMap(
                        messageChannel -> messageChannel.createMessage(tuple.fst())
                )).subscribe();
    }

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

    private String matchCommand(Message query, Map<String, ? extends Command> commandMap) {
        Command.setCurrentQuery(query);
        String queryContent = query.getContent().orElse("").trim();
        queryContent = queryContent.substring(queryContent.split(" ")[0].length()).trim();

        if (queryContent.length() > 0) {
            String[] split = queryContent.split("[ ,]++");
            System.out.println(split[0]);

            if (commandMap.containsKey(split[0])) {
                return commandMap.get(split[0])
                        .runCommand(Arrays.stream(split).filter(s -> !split[0].equals(s)).toArray(String[]::new));
            } else {
                return Responses.unknownCommand();
            }
        } else {
            return Responses.identification();
        }
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
