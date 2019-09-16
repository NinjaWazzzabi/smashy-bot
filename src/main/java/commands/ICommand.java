package commands;

import user.Powerlevel;

public interface ICommand extends Runnable {

    String getName();
    Powerlevel requiredLevel();
    void setContext(Context context);

}
