package commands.dnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class D20 extends DndCommand {

    @Override
    public void run() {
        Random random = new Random();
        int roll = random.nextInt(20) + 1;

        if (roll == 1) {
            sendBack("Rolled: **NATURAL ||    1||**");
        } else if (roll == 20) {
            sendBack("Rolled: **NATURAL ||   20||**");
        } else {
            sendBack("Rolled: " + roll);
        }
    }
}
