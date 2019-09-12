package commands.dnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class Hypers extends DndCommand {


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
        Set<String> hypedPeople = getWorkspace().getHypedPeople();

        if (hypedPeople.size() < 1) {
            return "Currently there are no hyped people, ofantligt sad times";
        } else if (hypedPeople.size() == 1){
            AtomicReference<String> s = new AtomicReference<>();
            hypedPeople.iterator().forEachRemaining(name -> s.set("The lonely hyper is: " + name));
            return s.get();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Current hypers are:\n");
            hypedPeople.forEach(name -> sb.append("\t").append(name).append("\n"));
            return sb.toString();
        }
    }
}
