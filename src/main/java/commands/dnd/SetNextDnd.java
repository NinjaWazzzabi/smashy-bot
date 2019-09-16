package commands.dnd;

import user.Powerlevel;

import java.time.DateTimeException;
import java.time.MonthDay;
import java.util.*;

public class SetNextDnd extends DndCommand {

    private final Integer day;
    private final Integer month;

    public SetNextDnd(Integer day, Integer month) {
        this.day = day;
        this.month = month;
    }

    @Override
    public Powerlevel requiredLevel() {
        return Powerlevel.MODERATOR;
    }

    @Override
    public void run() {
        try {
            MonthDay time = MonthDay.of(month, day);
            getWorkspace().setNextDndHang(time);
            getWorkspace().setHypedPeople(new HashSet<>());
            sendBack("Next D&D Häng is set to: " + getWorkspace().getNextDndHang().toString());
        } catch (DateTimeException dte) {
            sendBack("Yo daug, that's outside the realm of time");
        }
//        if (getCurrentQuery().getAuthor().map(
//                user -> Arrays.asList(ALLOWED_PEOPLE).contains(user.getUsername())).blockOptional().orElse(false)
//        ) {
//        } else {
//            return "Whoa buddy, you don't have permission to do this!";
//        }
    }
}
