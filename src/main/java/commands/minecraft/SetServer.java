package commands.minecraft;

import user.Powerlevel;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class SetServer extends MinecraftCommand {

    @Override
    public Powerlevel requiredLevel() {
        return Powerlevel.ADMIN;
    }

    private final String ip;
    public SetServer(String ip) {
        this.ip = ip;
    }

    private static final int DEFAULT_MC_PORT = 25565;

    private Pattern addressPattern = Pattern.compile("^"
                                        + "(((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}" // Domain name
                                        + "|"
                                        + "localhost" // localhost
                                        + "|"
                                        + "(([0-9]{1,3}\\.){3})[0-9]{1,3})"); // Ip

    private Pattern completePattern = Pattern.compile("^"
                                        + "(((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}" // Domain name
                                        + "|"
                                        + "localhost" // localhost
                                        + "|"
                                        + "(([0-9]{1,3}\\.){3})[0-9]{1,3})" // Ip
                                        + ":"
                                        + "[0-9]{1,5}$"); // Port

    @Override
    public void run() {
        if (ip.contains(":") && completePattern.matcher(ip).matches()) {
            String[] split = ip.split(":");
//            setServerPinger(new McServerPinger(split[0], Integer.valueOf(split[1])));
            sendBack("Server set to: " + ip);
        } else if (addressPattern.matcher(ip).matches()) {
//            setServerPinger(new McServerPinger(ip, DEFAULT_MC_PORT));
            sendBack("Server set to: " + ip);
        } else {
            sendBack("Error: bad formatting. Either *IP:PORT* or only *IP*");
        }
    }
}
