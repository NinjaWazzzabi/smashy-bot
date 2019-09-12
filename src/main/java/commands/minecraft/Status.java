package commands.minecraft;

import minecraft_server_ping.MinecraftPingReply;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Status extends MinecraftCommand {

    @Override
    public List<String> getParameterNames() {
        return toList();
    }

    @Override
    public List<Class> getParameterTypes() {
        return toList();
    }

    @Override
    protected String run(List<Object> parameters) {
        if (getServerPinger().isPresent()) {
            McServerPinger server = MinecraftCommand.getServerPinger().get();
            if (server.isOnline()) {
                MinecraftPingReply serverInfo = server.getServerInfo();
                if (serverInfo != null) {
                    return formatStatus(serverInfo);
                } else {
                    return "Server is online! Info is not available.";
                }
            } else {
                return "Server of " + server.getIp() + " is offline :(";
            }
        } else {
            return "Server ip not set";
        }
    }

    private String formatStatus(MinecraftPingReply reply) {
        String description = reply.getDescription().getText();
//        String favicon = reply.getFavicon();
        List<MinecraftPingReply.Player> players = reply.getPlayers().getSample();
        String version = reply.getVersion().getName();

        StringBuilder sb = new StringBuilder();
        sb.append(description).append("\n");
        sb.append("Version: ").append(version).append("\n");
        if (players != null && players.size() > 0) {
            sb.append("Players online:\n");
            for (MinecraftPingReply.Player player : players) {
                sb.append("    ").append(player.getName()).append("\n");
            }
        } else {
            sb.append("No players online");
        }

        String[] lines = sb.toString().split("\n");
        int maxLength = Arrays.stream(lines).map(String::length).max(Integer::compareTo).orElse(0);


        /*
         **********************
         **********************
         **                  **
         **  Name: naem      **
         **  Players online: **
         **      oneBoi .... **
         */

        // Maxlen + 6
        // start with **__, end with __**
        String borderCharacter = "$";
        String start = borderCharacter + borderCharacter + "  ";
        String end = "  " + borderCharacter + borderCharacter + "\n";

        String roof = repeat(repeat(borderCharacter, maxLength + 8) + "\n", 2);
        String empty = start + repeat(" ", maxLength) + end;


        final StringBuilder asciiArt = new StringBuilder();
        asciiArt.append("```")
                .append(roof)
                .append(empty);

        Arrays.stream(lines).forEach(line -> {
            asciiArt.append(start);
            asciiArt.append(line);
            IntStream.range(0, maxLength - line.length()).forEach(it -> asciiArt.append(" "));
            asciiArt.append(end);
        });

        asciiArt.append(empty)
                .append(roof)
                .append("```");
        return asciiArt.toString();
    }

    String repeat(String s, int times) {
        StringBuilder sb = new StringBuilder(times);
        for (int i = 0; i < times; i++) {
            sb.append(s);
        }
        return sb.toString();
    }
}




























