package commands.admin;

import commands.dnd.DndCommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveState extends AdminCommand {


    @Override
    public List<String> getParameterNames() {
        return new ArrayList<>();
    }

    @Override
    public List<Class> getParameterTypes() {
        return new ArrayList<>();
    }

    @Override
    protected String runAdmMode(List<Object> parameters) {
        DndCommand.getWorkspace().saveToFile();
//        try {
//            DndCommand.getWorkspace().saveToFile();
//            return "State saved!";
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "ERROR: couldn't save file, check stacktrace for more information!";
//        }
        return "saved";
    }
}
