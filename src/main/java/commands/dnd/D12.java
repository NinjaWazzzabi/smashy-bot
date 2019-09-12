package commands.dnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class D12 extends DndCommand {
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
        Random random = new Random();
        return "Rolled: " + (random.nextInt(12) + 1);
    }
}
