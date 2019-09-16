package commands;

import user.Powerlevel;

public abstract class AbstractCommand implements ICommand {

    @Override
    public Powerlevel requiredLevel() {
        return Powerlevel.USER;
    }

    protected void sendBack(String msg) {
        context.messageChannel.createMessage(msg).subscribe();
    }

    @Override
    public String getName() {
        String simpleName = this.getClass().getSimpleName();
        return simpleName.substring(0, 1).toLowerCase().concat(simpleName.substring(1));
    }

    protected Context context;

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}
