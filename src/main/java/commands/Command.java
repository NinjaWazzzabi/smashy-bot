package commands;

import discord4j.core.object.entity.Message;
import lombok.Getter;
import lombok.Setter;
import org.reflections.Reflections;
import utils.Tuple;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class Command {

    @Setter
    @Getter
    private static Message currentQuery;

    public final String getCommandName() {
        String name = this.getClass().getSimpleName();
        return name.substring(0,1).toLowerCase().concat(name.substring(1));
    }

    public abstract List<String> getParameterNames();
    public abstract List<Class> getParameterTypes();

    public final String runCommand(String[] data) {
        Optional<List<Object>> parameters = extractParameters(data);

        if (parameters.isPresent()) {
            return run(parameters.get());
        } else {
            List<Tuple<String, Class>> parameterData = zip(getParameterNames(), getParameterTypes());
            Stream<String> parameterString = parameterData.stream()
                    .map(para -> para.fst() + ":" + para.snd().getSimpleName());

            StringBuilder sb = new StringBuilder();

            sb.append("Bad parameters for: **").append(getCommandName()).append("**\n");
            if (parameterData.size() < 1) {
                sb.append("No parameters required");
            } else if (parameterData.size() == 1) {
                sb.append("Required parameter is: ");
            } else {
                sb.append("Required parameters are: ");
            }
            parameterString.forEach(s -> sb.append(s).append(" "));
            return sb.toString();
        }
    }

    protected abstract String run(List<Object> parameters);

    private Optional<List<Object>> extractParameters(String[] data) {
        if (getParameterTypes().size() != data.length) {
            return Optional.empty();
        }

        ArrayList<String> strings = new ArrayList<>(Arrays.asList(data));
        List<Tuple<Class, String>> zip = zip(getParameterTypes(), strings);
        final boolean[] success = {true};

        List<Object> convertedParameters = zip.stream().map(cst -> {
            try {
                return convertType(cst.fst(), cst.snd());
            } catch (NumberFormatException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
                success[0] = false;
                return null;
            }
        }).collect(Collectors.toList());

        if (success[0]) {
            return Optional.of(convertedParameters);
        } else {
            return Optional.empty();
        }
    }

    private Object convertType(Class to, String from) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method valueOf = null;
        try {
            valueOf = to.getDeclaredMethod("valueOf", String.class);
        } catch (NoSuchMethodException e) {
            valueOf = to.getDeclaredMethod("valueOf", Object.class);
        }
        return valueOf.invoke(to, from);
    }

    protected static <T, S> List<Tuple<T, S>> zip(List<T> t, List<S> s) {
        return IntStream.range(0, Math.min(t.size(), s.size()))
                .mapToObj(value -> new Tuple<>(t.get(value), s.get(value)))
                .collect(Collectors.toList());
    }

    protected static <T> List<T> toList(T ... ts) {
        return new ArrayList<>(Arrays.asList(ts));
    }
}
