package commands.minecraft;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class SetServer extends MinecraftCommand {

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
    public List<String> getParameterNames() {
        return toList("IP");
    }

    @Override
    public List<Class> getParameterTypes() {
        return toList(String.class);
    }

    @Override
    protected String run(List<Object> parameters) {
        String ip = (String) parameters.get(0);
        if (ip.contains(":") && completePattern.matcher(ip).matches()) {
            String[] split = ip.split(":");
            setServerPinger(new McServerPinger(split[0], Integer.valueOf(split[1])));
            return "Server set to: " + ip;
        } else if (addressPattern.matcher(ip).matches()) {
            setServerPinger(new McServerPinger(ip, DEFAULT_MC_PORT));
            return "Server set to: " + ip;
        } else {
            return "Error: bad formatting. Either *IP:PORT* or only *IP*";
        }
    }
}
