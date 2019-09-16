package commands.admin;

public class KickMe extends AdminCommand {

    @Override
    public void run() {
        if (context.user != null && context.guild != null) {
            context.guild.kick(context.user.getId()).subscribe();
        } else {
            sendBack("Guild or user was null");
        }
        sendBack("Yeeted: " + context.user.getUsername());
    }
}
