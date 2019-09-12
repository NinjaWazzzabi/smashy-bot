package commands.dnd;

import java.time.DateTimeException;
import java.time.MonthDay;
import java.util.*;

public class SetNextDnd extends DndCommand {

    private static final String[] ALLOWED_PEOPLE = {
            "Tonyyy"
    };

    @Override
    public List<String> getParameterNames() {
        return new ArrayList<>(Arrays.asList("Month", "Day"));
    }

    @Override
    public List<Class> getParameterTypes() {
        return new ArrayList<>(Arrays.asList(Integer.class, Integer.class));
    }

    @Override
    protected String run(List<Object> parameters) {
        if (getCurrentQuery().getAuthor().map(
                user -> Arrays.asList(ALLOWED_PEOPLE).contains(user.getUsername())).blockOptional().orElse(false)
        ) {
            try {
                MonthDay time = MonthDay.of((int) parameters.get(0), (int) parameters.get(1));
                getWorkspace().setNextDndHang(time );
                getWorkspace().setHypedPeople(new HashSet<>());
                return "Next D&D Häng is set to: " + getWorkspace().getNextDndHang().toString();
            } catch (DateTimeException dte) {
                return "Yo daug, that's outside the realm of time";
            }
        } else {
            return "Whoa buddy, you don't have permission to do this!";
        }
    }
}
