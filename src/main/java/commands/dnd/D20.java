package commands.dnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class D20 extends DndCommand {

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
        int roll = random.nextInt(20) + 1;

        if (roll == 1) {
            return "Rolled: **NATURAL ||    1||**";
        } else if (roll == 20) {
            return "Rolled: **NATURAL ||   20||**";
        } else {
            return "Rolled: " + roll;
        }
    }
}
