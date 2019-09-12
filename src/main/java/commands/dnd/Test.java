package commands.dnd;

import java.util.ArrayList;
import java.util.List;

public class Test extends DndCommand {
    @Override
    public List<String> getParameterNames() {
        return new ArrayList<>();
    }

    @Override
    public List<Class> getParameterTypes() {
        return new ArrayList<>();
    }

    @Override
    protected String run(List<Object> parameters) {
        return "lmao";
    }
}
