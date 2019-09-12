package commands.dnd;

import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

public class When extends DndCommand {

    // TODO: 09/02/2019 Read fields names and types instead of returning lists? Easier and no type casting!

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

        MonthDay nextDndHang = getWorkspace().getNextDndHang();

        if (nextDndHang == null) {
            return "No D&D häng has been set! **This is outrageous!**";
        } else if (nextDndHang.isBefore(MonthDay.now())) {
            return "Last D&D häng was on " + nextDndHang.toString() + ". I belive it's time for a new one!";
        } else {
            return "Next D&D häng will be on " + nextDndHang.toString();
        }
    }
}
