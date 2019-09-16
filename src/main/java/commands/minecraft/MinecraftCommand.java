package commands.minecraft;

import commands.AbstractCommand;
import commands.ICommand;
import lombok.Getter;

import java.util.Optional;

public abstract class MinecraftCommand extends AbstractCommand implements ICommand {

//    @Setter
//    private static Optional<Consumer<String>> mcChannelCallback = Optional.empty();

    @Getter
    private static Optional<McServerPinger> serverPinger = Optional.of(new McServerPinger("81.231.19.201", 25565));

//    static void setServerPinger(McServerPinger serverPinger) {
//        MinecraftCommand.serverPinger = Optional.of(serverPinger);
//        serverPinger.setOnChange(server -> {
//            mcChannelCallback.ifPresent(response -> {
//                if (server.isOnline()) {
//                    response.accept("The Minecraft server is *offline* :(");
//                } else {
//                    response.accept("The Minecraft server is **online**!");
//                }
//            });
//            return Unit.INSTANCE;
//        });
//    }
}
