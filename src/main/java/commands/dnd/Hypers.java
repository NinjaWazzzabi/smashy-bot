package commands.dnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class Hypers extends DndCommand {

    @Override
    public void run() {
        Set<String> hypedPeople = getWorkspace().getHypedPeople();

        if (hypedPeople.size() < 1) {
            sendBack("Currently there are no hyped people, ofantligt sad times");
        } else if (hypedPeople.size() == 1){
            AtomicReference<String> s = new AtomicReference<>();
            hypedPeople.iterator().forEachRemaining(name -> s.set("The lonely hyper is: " + name));
            sendBack(s.get());
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Current hypers are:\n");
            hypedPeople.forEach(name -> sb.append("\t").append(name).append("\n"));
            sendBack(sb.toString());
        }
    }
}
