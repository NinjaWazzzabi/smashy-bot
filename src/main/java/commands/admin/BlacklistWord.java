package commands.admin;

import utils.PersistentData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlacklistWord extends AdminCommand {
    @Override
    public List<String> getParameterNames() {
        return new ArrayList<>(Arrays.asList("Actual word"));
    }

    @Override
    public List<Class> getParameterTypes() {
        return new ArrayList<>(Arrays.asList(String.class));
    }

    @Override
    protected String runAdmMode(List<Object> parameters) {
        String word = (String) parameters.get(0);
        PersistentData.blacklistedWords.add(word.toLowerCase());
        return "The word *" + word + "* has been blacklisted!";
    }
}
