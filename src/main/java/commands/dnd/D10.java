package commands.dnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class D10 extends DndCommand {

    @Override
    public void run() {
        Random random = new Random();
        sendBack("Rolled: " + (random.nextInt(10) + 1));
    }
}
