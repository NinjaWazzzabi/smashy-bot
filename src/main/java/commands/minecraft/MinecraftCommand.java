package commands.minecraft;

import commands.Command;
import kotlin.Unit;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class MinecraftCommand extends Command {

    @Setter
    private static Optional<Consumer<String>> mcChannelCallback = Optional.empty();

    @Getter
    private static Optional<McServerPinger> serverPinger = Optional.empty();

    static void setServerPinger(McServerPinger serverPinger) {
        MinecraftCommand.serverPinger = Optional.of(serverPinger);
        serverPinger.setOnChange(server -> {
            mcChannelCallback.ifPresent(response -> {
                if (server.isOnline()) {
                    response.accept("The Minecraft server is *offline* :(");
                } else {
                    response.accept("The Minecraft server is **online**!");
                }
            });
            return Unit.INSTANCE;
        });
    }

    @Getter
    private static final Map<String, MinecraftCommand> commandMap = initCommandMap();

    private static Map<String, MinecraftCommand> initCommandMap() {
        serverPinger = Optional.of(new McServerPinger("81.231.19.201", 25565));
        return initCommandMap(MinecraftCommand.class);
    }
}
