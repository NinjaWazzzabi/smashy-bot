package commands.dnd;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.time.MonthDay;
import java.util.*;


public class Workspace {

    private static final String FILE_NAME = "dnd-shenanigans.dnd";

    @Setter
    @Getter
    private MonthDay nextDndHang;
    @Getter
    @Setter
    private Set<String> hypedPeople = new HashSet<>();
    @Getter
    private final List<DndCommand> commandList = new ArrayList<>();

    public void loadFromFile() {
        new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
                String sVersion = reader.readLine();
                String sNextDndHang = reader.readLine();
                String sHypedPeople = reader.readLine();

                String[] monthDay = sNextDndHang.split(":")[1].split(",");
                MonthDay tmp = MonthDay.of(Integer.parseInt(monthDay[0]), Integer.parseInt(monthDay[1]));
                nextDndHang = tmp;

                hypedPeople.addAll(Arrays.asList(sHypedPeople.split(":")[1].split(",")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void saveToFile() {
        new Thread(() -> {
            StringBuilder sb = new StringBuilder();

            sb.append("Version: 1.0.0\n");
            sb.append("nextDndHang:")
                    .append(nextDndHang.getMonth().getValue())
                    .append(",")
                    .append(nextDndHang.getDayOfMonth())
                    .append("\n");
            sb.append("hypedPeople:");
            if (hypedPeople.size() > 0) {
                hypedPeople.forEach(name -> sb.append(name).append(","));
                sb.delete(sb.length() - 1, sb.length());
            }
            sb.append("\n");


            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
                writer.write(sb.toString());
                writer.close();
            } catch (IOException e) {
//                return "ERROR: couldn't save file, check stacktrace for more information!";
                e.printStackTrace();
            }
        }).start();
    }

}
