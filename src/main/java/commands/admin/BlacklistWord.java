package commands.admin;

import utils.PersistentData;

public class BlacklistWord extends AdminCommand {

    private final String actualWord;

    public BlacklistWord(String actualWord) {
        this.actualWord = actualWord;
    }

    @Override
    public void run() {
        PersistentData.blacklistedWords.add(actualWord.toLowerCase());
        sendBack("The word *" + actualWord + "* has been blacklisted!");
    }
}
