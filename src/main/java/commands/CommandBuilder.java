package commands;

import utils.Tuple;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// TODO: 16/09/2019 Store command constructor arguments and try and match the inputted ones
public class CommandBuilder {


    <T extends ICommand> Optional<T> buildCommandRaw(CommandPrototype<T> prototype, String[] arguments) {
        Optional<List<Object>> formattedArguments = extractParameters(arguments, prototype.constructorArgs);

        if (formattedArguments.isPresent()) {
            return buildCommand(prototype, formattedArguments.get());
        } else {
            return Optional.empty();
        }
    }

    <T extends ICommand> Optional<T> buildCommand(CommandPrototype<T> prototype, List<Object> arguments) {
        List<Class> requiredArgs = prototype.constructorArgs;

        // Check size
        if (requiredArgs.size() != arguments.size()) {
            return Optional.empty();
        }

        // Check types
        for (int i = 0; i < arguments.size(); i++) {
            if (!requiredArgs.get(i).equals(arguments.get(i).getClass())) {
                return Optional.empty();
            }
        }

        Class<T> commandClass = prototype.commandClass;
        try {
            Constructor<?> constructor = commandClass.getDeclaredConstructors()[0];
            T command;
            if (arguments.size() < 1) {
                command = (T) constructor.newInstance();
            } else {
                command = (T) constructor.newInstance(arguments.toArray());
            }
            return Optional.of(command);
        } catch (IllegalAccessException | IllegalArgumentException |
                InstantiationException | InvocationTargetException | ExceptionInInitializerError e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private Optional<List<Object>> extractParameters(String[] data, List<Class> parameters) {
        if (parameters.size() != data.length) {
            return Optional.empty();
        }

        ArrayList<String> strings = new ArrayList<>(Arrays.asList(data));
        List<Tuple<Class, String>> zip = zip(parameters, strings);
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

    private static <T, S> List<Tuple<T, S>> zip(List<T> t, List<S> s) {
        return IntStream.range(0, Math.min(t.size(), s.size()))
                .mapToObj(value -> new Tuple<>(t.get(value), s.get(value)))
                .collect(Collectors.toList());
    }
}
