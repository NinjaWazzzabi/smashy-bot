package commands.admin;

import utils.PersistentData;

import java.util.ArrayList;
import java.util.List;

public class BlacklistedWords extends AdminCommand {
    @Override
    public List<String> getParameterNames() {
        return new ArrayList<>();
    }

    @Override
    public List<Class> getParameterTypes() {
        return new ArrayList<>();
    }

    @Override
    protected String runAdmMode(List<Object> parameters) {
        StringBuilder sb = new StringBuilder();
        PersistentData.blacklistedWords.forEach(s -> sb.append(s).append("\n"));
        return sb.toString();
    }
}
